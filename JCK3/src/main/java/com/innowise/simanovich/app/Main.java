package com.innowise.simanovich.app;

import com.innowise.simanovich.concurrent.WardManager;
import com.innowise.simanovich.entity.Patient;
import com.innowise.simanovich.entity.Pharmacy;
import com.innowise.simanovich.reader.DataReader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {
  private static final Logger logger = LogManager.getLogger(Main.class);

  public static void main(String[] args) {
    DataReader reader = new DataReader();
    Map<String, Integer> config = reader.readData("data.txt");

    int wardsCount = config.getOrDefault("wards", 5);
    int medicinesCount = config.getOrDefault("medicines", 20);
    int patientsCount = config.getOrDefault("patients", 10);

    Pharmacy pharmacy = Pharmacy.getInstance(medicinesCount);
    WardManager wardManager = new WardManager(wardsCount);

    ExecutorService executor = Executors.newFixedThreadPool(wardsCount);
    List<Patient> patients = new ArrayList<>();

    for (int i = 1; i <= patientsCount; i++) {
      Patient patient = new Patient("Patient_" + i, 2, wardManager, pharmacy);
      patients.add(patient);
      executor.submit(patient);
    }

    executor.shutdown();
    try {
      if (!executor.awaitTermination(60, TimeUnit.SECONDS)) {
        executor.shutdownNow();
      }
      logger.info("All patients have been treated. Hospital is closing.");
    } catch (InterruptedException e) {
      executor.shutdownNow();
      Thread.currentThread().interrupt();
      logger.error("Main thread was interrupted", e);
    }
  }
}