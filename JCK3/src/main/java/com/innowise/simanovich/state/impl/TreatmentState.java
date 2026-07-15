package com.innowise.simanovich.state.impl;

import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.state.PatientState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class TreatmentState implements PatientState {
  private static final Logger logger = LogManager.getLogger(TreatmentState.class);

  @Override
  public void handle(Patient patient) throws InterruptedException {
    logger.info("Patient {} is undergoing treatment in ward {}...", patient.getName(), patient.getAssignedWard().getWardNumber());
    TimeUnit.SECONDS.sleep(2);
    patient.setState(new RecoveredState());
  }

  @Override
  public boolean isRecovered() {
    return false;
  }
}