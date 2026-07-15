package com.innowise.simanovich.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Ward {
  private static final Logger logger = LogManager.getLogger(Ward.class);

  private final int wardNumber;
  private boolean isOccupied;
  private Patient currentPatient;

  public Ward(int wardNumber) {
    this.wardNumber = wardNumber;
    this.isOccupied = false;
    logger.debug("Ward {} object created", wardNumber);
  }

  public int getWardNumber() {
    return wardNumber;
  }

  public boolean isOccupied() {
    return isOccupied;
  }

  public void setOccupied(boolean occupied) {
    isOccupied = occupied;
  }

  public Patient getCurrentPatient() {
    return currentPatient;
  }

  public void setCurrentPatient(Patient currentPatient) {
    this.currentPatient = currentPatient;
  }
}