package utils;

import java.io.IOException;

import json.JsonFileHandler;

public class JackSonUtil {
  public void insertObj2JsonFile(Object valObj, String directory, String jsonFile) {
    JsonFileHandler handler = new JsonFileHandler();
    if (valObj != null) {
      try {
        handler.writeJsonToFile(valObj, directory, jsonFile);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(this + " insertObj2JsonFile valObj is NULL");
    }
  }
}