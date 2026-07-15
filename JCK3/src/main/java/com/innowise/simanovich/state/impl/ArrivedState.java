package com.innowise.simanovich.state.impl;

import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.state.PatientState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class ArrivedState implements PatientState {
  private static final Logger logger = LogManager.getLogger(ArrivedState.class);

  @Override
  public void handle(Patient patient) throws InterruptedException {
    logger.info("Patient {} is arriving and registering...", patient.getName());
    TimeUnit.MILLISECONDS.sleep(100);
    patient.setState(new WaitingForWardState());
  }

  @Override
  public boolean isRecovered() {
    return false;
  }
}