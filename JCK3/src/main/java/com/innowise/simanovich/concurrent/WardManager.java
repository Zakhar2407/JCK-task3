package com.innowise.simanovich.concurrent;

import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.entity.Ward;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class WardManager {
  private static final Logger logger = LogManager.getLogger(WardManager.class);

  private final Ward[] wards;
  private final ReentrantLock lock = new ReentrantLock();
  private final Condition wardFree = lock.newCondition();

  public WardManager(int capacity) {
    this.wards = new Ward[capacity];
    for (int i = 0; i < capacity; i++) {
      wards[i] = new Ward(i + 1);
    }
    logger.info("WardManager initialized with {} wards", capacity);
  }

  public void occupyWard(Patient patient) throws InterruptedException {
    lock.lock();
    try {
      logger.debug("Patient {} is looking for a free ward...", patient.getName());

      while (true) {
        for (Ward ward : wards) {
          if (!ward.isOccupied()) {
            ward.setOccupied(true);
            ward.setCurrentPatient(patient);
            patient.setAssignedWard(ward);
            logger.info("Patient {} assigned to Ward {}", patient.getName(), ward.getWardNumber());
            return;
          }
        }
        logger.info("All wards are occupied. Patient {} is waiting...", patient.getName());
        wardFree.await();
      }
    } finally {
      lock.unlock();
    }
  }

  public void releaseWard(int wardNumber) {
    lock.lock();
    try {
      Ward ward = wards[wardNumber - 1];
      ward.setOccupied(false);
      ward.setCurrentPatient(null);
      logger.info("Ward {} is now free", wardNumber);
      wardFree.signal();
    } finally {
      lock.unlock();
    }
  }

  public Ward[] getWards() {
    return wards;
  }
}