package com.innowise.simanovich.state.impl;

import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.state.PatientState;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.concurrent.TimeUnit;

public class WaitingForMedicineState implements PatientState {
  private static final Logger logger = LogManager.getLogger(WaitingForMedicineState.class);

  @Override
  public void handle(Patient patient) throws InterruptedException {
    logger.info("Patient {} is waiting for medicines...", patient.getName());
    patient.getPharmacy().dispenseMedicines(patient.getRequiredMedicines(), patient.getName());
    logger.info("Patient {} received required medicines", patient.getName());
    TimeUnit.MILLISECONDS.sleep(50);
    patient.setState(new TreatmentState());
  }

  @Override
  public boolean isRecovered() {
    return false;
  }
}