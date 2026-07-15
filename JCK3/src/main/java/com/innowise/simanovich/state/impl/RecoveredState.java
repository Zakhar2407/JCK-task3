package com.innowise.simanovich.state.impl;

import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.state.PatientState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class RecoveredState implements PatientState {
  private static final Logger logger = LogManager.getLogger(RecoveredState.class);

  @Override
  public void handle(Patient patient) throws InterruptedException {
    logger.info("Patient {} has recovered and is being discharged...", patient.getName());
    patient.getWardManager().releaseWard(patient.getAssignedWard().getWardNumber());
    patient.getPharmacy().returnMedicines(patient.getRequiredMedicines());
    TimeUnit.MILLISECONDS.sleep(100);
  }

  @Override
  public boolean isRecovered() {
    return true;
  }
}