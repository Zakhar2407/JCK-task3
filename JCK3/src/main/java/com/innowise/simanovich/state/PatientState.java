package com.innowise.simanovich.state;

import com.innowise.simanovich.entity.Patient;

public interface PatientState {
  void handle(Patient patient) throws InterruptedException;
  boolean isRecovered();
}