package services;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import entities.*;
import json.JsonFileHandler;
import constants.*;

public class ScaleFactorHandler {
  public Double processDecision(Double decision) throws IOException {
    JsonFileHandler handler = new JsonFileHandler();
    Path filePath = Constants.getConflatePath(Constants.json_folder_path, Constants.scale_json_file);

    if (!Files.exists(filePath)) {
      // 第一次对图片进行缩放
      return firstTimeScaling(decision, handler);
    } else {
      // 文件已存在，更新缩放因子
      return updateScalingFactor(decision, handler, filePath.toString());
    }
  }

  public Double firstTimeScaling(Double decision, JsonFileHandler jsHandler) {
    ScaleFactorObj obj = new ScaleFactorObj();
    obj.setInScaleFactor(Constants.zoom_in_decision);
    obj.setOutScaleFactor(Constants.zoom_out_decision);
    try {
      jsHandler.writeJsonToFile(obj, Constants.json_folder_path,
          Constants.scale_json_file);
    } catch (IOException e) {
      e.printStackTrace();
    }
    return decision;
  }

  public Double updateScalingFactor(Double decision, JsonFileHandler jsHandler, String filePath) throws IOException {
    ScaleFactorObj scaleFactorObj;
    Double calculate = 1.0;

    try {
      scaleFactorObj = (ScaleFactorObj) jsHandler.getConvertObject(filePath, ScaleFactorObj.class);
    } catch (Exception e) {
      File file = new File(filePath);
      file.delete();
      return firstTimeScaling(decision, jsHandler);
    }

    if (decision.equals(Constants.zoom_in_decision)) {
      scaleFactorObj = calculateInFactor(scaleFactorObj, decision);
      calculate = scaleFactorObj.getInScaleFactor();

    } else if (decision.equals(Constants.zoom_out_decision)) {
      scaleFactorObj = calculateOutFactor(scaleFactorObj, decision);
      calculate = scaleFactorObj.getOutScaleFactor();

    }

    jsHandler.writeJsonToFile(scaleFactorObj, Constants.json_folder_path, Constants.scale_json_file);
    return calculate;
  }

  public ScaleFactorObj calculateInFactor(ScaleFactorObj scaleFactorObj, Double decision) {
    if (scaleFactorObj.getInScaleFactor() > Constants.zoom_upper_limit) {
      scaleFactorObj.setInScaleFactor(Constants.zoom_upper_limit);
    } else {
      decision = scaleFactorObj.getInScaleFactor() * decision;
      scaleFactorObj.setInScaleFactor(decision);
    }
    return scaleFactorObj;
  }

  public ScaleFactorObj calculateOutFactor(ScaleFactorObj scaleFactorObj, Double decision) {
    if (scaleFactorObj.getOutScaleFactor() < Constants.zoom_lower_limit) {
      scaleFactorObj.setOutScaleFactor(Constants.zoom_lower_limit);
    } else {
      decision = scaleFactorObj.getOutScaleFactor() * decision;
      scaleFactorObj.setOutScaleFactor(decision);
    }
    return scaleFactorObj;
  }

}