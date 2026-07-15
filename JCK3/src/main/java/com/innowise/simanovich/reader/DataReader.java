package com.innowise.simanovich.reader;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class DataReader {
  private static final Logger logger = LogManager.getLogger(DataReader.class);

  public Map<String, Integer> readData(String filePath) {
    Map<String, Integer> config = new HashMap<>();
    try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
      String line;
      while ((line = br.readLine()) != null) {
        String[] parts = line.split("=");
        if (parts.length == 2) {
          config.put(parts[0].trim(), Integer.parseInt(parts[1].trim()));
        }
      }
      logger.info("Configuration successfully loaded from: {}", filePath);
    } catch (IOException e) {
      logger.error("Failed to read configuration file: {}", filePath, e);
    }
    return config;
  }
}