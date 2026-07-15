package com.innowise.simanovich.state.impl;

import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.state.PatientState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class WaitingForWardState implements PatientState {
  private static final Logger logger = LogManager.getLogger(WaitingForWardState.class);

  @Override
  public void handle(Patient patient) throws InterruptedException {
    logger.info("Patient {} is waiting for a ward...", patient.getName());
    patient.getWardManager().occupyWard(patient);
    logger.info("Patient {} successfully occupied ward {}", patient.getName(), patient.getAssignedWard().getWardNumber());
    TimeUnit.MILLISECONDS.sleep(50);
    patient.setState(new WaitingForMedicineState());
  }

  @Override
  public boolean isRecovered() {
    return false;
  }
}