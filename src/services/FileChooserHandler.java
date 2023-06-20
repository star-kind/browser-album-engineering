package services;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import constants.Constants;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import entities.ImageValueObject;
import utils.DealFilePathStr;
import utils.JackSonUtil;

public class FileChooserHandler {
  public ImageValueObject openFileChooser() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");

    fileChooser.setFileFilter(imageFilter);
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      ImageValueObject imgValObj = getImgValObj(fileChooser);

      JackSonUtil util = new JackSonUtil();
      util.insertObj2JsonFile(imgValObj, Constants.json_folder_path, Constants.img_json_file);

      return imgValObj;
    }
    return null;
  }

  public ImageValueObject getImgValObj(JFileChooser fileChooser) {
    File selectedFile = fileChooser.getSelectedFile();
    DealFilePathStr deal = new DealFilePathStr();
    String[] imagePathArr = deal.getImgPathsArray(selectedFile);

    ImageValueObject imgValObj = getImgValObj(imagePathArr, selectedFile);
    return imgValObj;
  }

  public ImageValueObject getImgValObj(String[] imagePathArr, File selectedFile) {
    ImageValueObject imgValObj = new ImageValueObject();
    List<String> strList = Arrays.asList(imagePathArr);
    String currPath = selectedFile.getAbsolutePath();

    int currentOrder = strList.indexOf(currPath);
    System.out.println(this.getClass() + " getImgValObj currentOrder=" + currentOrder);

    imgValObj.setCurrentOrder(currentOrder);
    imgValObj.setCurrentPath(currPath);
    imgValObj.setCurrentPathsArray(imagePathArr);

    return imgValObj;
  }

}
