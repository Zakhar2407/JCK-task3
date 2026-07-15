package com.innowise.simanovich.entity;

import com.innowise.simanovich.concurrent.WardManager;
import com.innowise.simanovich.state.PatientState;
import com.innowise.simanovich.state.impl.ArrivedState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Patient implements Runnable {
  private static final Logger logger = LogManager.getLogger(Patient.class);

  private final String name;
  private final int requiredMedicines;
  private final WardManager wardManager;
  private final Pharmacy pharmacy;

  private PatientState currentState;
  private Ward assignedWard;

  public Patient(String name, int requiredMedicines, WardManager wardManager, Pharmacy pharmacy) {
    this.name = name;
    this.requiredMedicines = requiredMedicines;
    this.wardManager = wardManager;
    this.pharmacy = pharmacy;
    this.currentState = new ArrivedState();
    logger.info("Patient {} created. Required medicines: {}", name, requiredMedicines);
  }

  @Override
  public void run() {
    try {
      logger.info("Patient {} started treatment process (thread: {})", name, Thread.currentThread().getName());

      while (!currentState.isRecovered()) {
        currentState.handle(this);
      }

      logger.info("Patient {} has completed treatment and been discharged", name);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      logger.error("Patient {} was interrupted during treatment", name, e);
    } catch (Exception e) {
      logger.error("Error occurred while treating patient {}", name, e);
    }
  }

  public void setState(PatientState state) {
    this.currentState = state;
  }

  public PatientState getState() {
    return currentState;
  }

  public int getRequiredMedicines() {
    return requiredMedicines;
  }

  public String getName() {
    return name;
  }

  public void setAssignedWard(Ward ward) {
    this.assignedWard = ward;
  }

  public Ward getAssignedWard() {
    return assignedWard;
  }

  public WardManager getWardManager() {
    return wardManager;
  }

  public Pharmacy getPharmacy() {
    return pharmacy;
  }
}