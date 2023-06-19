package json;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonFileHandler {
  private static final ObjectMapper objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);

  private void createFolderIfNotExists(String folderPath) {
    File folder = new File(folderPath);
    if (!folder.exists()) {
      folder.mkdirs();
    }
  }

  public void writeJsonToFile(Object val, String folderPath, String fileName) throws IOException {
    createFolderIfNotExists(folderPath);
    String filePath = folderPath + File.separator + fileName;
    String json = objectMapper.writeValueAsString(val);
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
      writer.write(json);
    }
  }

  public String readJsonFromFile(String filePath) throws IOException {
    File file = new File(filePath);

    if (file.isDirectory()) {
      System.out.println("Given path is a directory: " + filePath);
      return null;
    }

    if (file.exists() == true) {
      return new String(Files.readAllBytes(Paths.get(filePath)));
    }

    System.out.println("File does not exist: " + filePath);
    return null;
  }

  public void printJsonFromFile(String filePath) throws IOException {
    String json = readJsonFromFile(filePath);
    if (json != null || !("".equals(json))) {
      System.out.println(json);
    } else {
      System.out.println("invaild json");
    }
  }

  public void clearJsonFile(String filePath) throws IOException {
    File file = new File(filePath);
    if (file.exists()) {
      try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
        writer.write("");
      }
    } else {
      System.out.println("File does not exist: " + filePath);
    }
  }

  public String convertToJsonFromFile(String filePath) throws IOException {
    if (filePath == null || filePath.trim().isEmpty()) {
      System.out.println(this.getClass() + " convertToJsonFromFile filePath is null");
      return null; // 返回null或者抛出异常，具体根据需求而定
    }

    String json = readJsonFromFile(filePath);
    if (json == null || json.trim().isEmpty()) {
      System.out.println(this.getClass() + " convertToJsonFromFile json is null");
      return null; // 返回null或者抛出异常，具体根据需求而定
    }

    Object val = null;
    try {
      val = objectMapper.readValue(json, Object.class);
    } catch (JsonProcessingException e) {
      e.printStackTrace();
    }

    return objectMapper.writeValueAsString(val);
  }

  public Object convertToObjectFromJson(String json, Class<?> clazz) throws IOException {
    return objectMapper.readValue(json, clazz);
  }

  public Object getConvertObject(String path, Class<?> clazz) throws IOException {
    if (path != null || !("".equals(path))) {
      // 将文件内容转化为JSON
      String convertedJson = convertToJsonFromFile(path);
      if (convertedJson != null || !("".equals(convertedJson))) {
        // 将JSON还原为对象
        Object obj = convertToObjectFromJson(convertedJson, clazz);
        return obj;
      }
    }
    return null;
  }

}
