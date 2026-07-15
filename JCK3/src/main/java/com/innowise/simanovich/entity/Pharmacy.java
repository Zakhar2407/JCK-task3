package com.innowise.simanovich.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;

public class Pharmacy {
  private static final Logger logger = LogManager.getLogger(Pharmacy.class);

  private static Pharmacy instance;
  private static final ReentrantLock instanceLock = new ReentrantLock();

  private final int totalCapacity;
  private int availableMedicines;
  private final ReentrantLock medicineLock;
  private final Condition medicinesAvailable;

  private Pharmacy(int totalCapacity) {
    this.totalCapacity = totalCapacity;
    this.availableMedicines = totalCapacity;
    this.medicineLock = new ReentrantLock();
    this.medicinesAvailable = medicineLock.newCondition();
    logger.info("Pharmacy created with total capacity: {} medicines", totalCapacity);
  }

  public static Pharmacy getInstance(int totalCapacity) {
    instanceLock.lock();
    try {
      if (instance == null) {
        instance = new Pharmacy(totalCapacity);
        logger.info("Pharmacy singleton instance created");
      }
      return instance;
    } finally {
      instanceLock.unlock();
    }
  }

  public void dispenseMedicines(int quantity, String patientName) throws InterruptedException {
    medicineLock.lock();
    try {
      logger.debug("Patient {} requesting {} medicines. Available: {}", patientName, quantity, availableMedicines);
      while (availableMedicines < quantity) {
        logger.warn("Insufficient medicines for patient {}. Available: {}, Required: {}. Waiting...", patientName, availableMedicines, quantity);
        medicinesAvailable.await();
      }
      availableMedicines -= quantity;
      logger.info("Dispensed {} medicines to patient {}. Remaining: {}", quantity, patientName, availableMedicines);
    } finally {
      medicineLock.unlock();
    }
  }

  public void replenishMedicines(int quantity) {
    medicineLock.lock();
    try {
      int oldStock = availableMedicines;
      availableMedicines = Math.min(availableMedicines + quantity, totalCapacity);
      int actualAdded = availableMedicines - oldStock;
      logger.info("Pharmacy replenished. Added: {}, Total available: {}, Capacity: {}", actualAdded, availableMedicines, totalCapacity);
      medicinesAvailable.signalAll();
    } finally {
      medicineLock.unlock();
    }
  }

  public void returnMedicines(int quantity) {
    medicineLock.lock();
    try {
      availableMedicines = Math.min(availableMedicines + quantity, totalCapacity);
      logger.info("Returned {} medicines to pharmacy. Total available: {}", quantity, availableMedicines);
      medicinesAvailable.signalAll();
    } finally {
      medicineLock.unlock();
    }
  }

  public int getAvailableMedicines() {
    medicineLock.lock();
    try {
      return availableMedicines;
    } finally {
      medicineLock.unlock();
    }
  }

  public int getTotalCapacity() {
    return totalCapacity;
  }
}