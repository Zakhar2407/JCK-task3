package com.innowise.simanovich.entity;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Medicine {
  private static final Logger logger = LogManager.getLogger(Medicine.class);

  private final String name;
  private final String description;
  private final int dosage;

  public Medicine(String name, String description, int dosage) {
    this.name = name;
    this.description = description;
    this.dosage = dosage;
    logger.debug("Medicine created: {} (dosage: {})", name, dosage);
  }

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public int getDosage() {
    return dosage;
  }

  @Override
  public String toString() {
    return "Medicine{name='" + name + "', dosage=" + dosage + "}";
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    Medicine medicine = (Medicine) obj;
    return name.equals(medicine.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}