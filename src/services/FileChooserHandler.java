package services;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.io.File;
import java.util.Arrays;
import java.util.List;

import entities.ImageValueObject;
import utils.DealFilePathStr;

public class FileChooserHandler {
  public ImageValueObject getImgsDataModel() {
    JFileChooser fileChooser = new JFileChooser();
    FileNameExtensionFilter imageFilter = new FileNameExtensionFilter("Image files", "jpg", "jpeg", "png", "gif");

    fileChooser.setFileFilter(imageFilter);
    int result = fileChooser.showOpenDialog(null);

    if (result == JFileChooser.APPROVE_OPTION) {
      ImageValueObject imgValObj = getImgVO(fileChooser);
      return imgValObj;
    }
    return null;
  }

  public ImageValueObject getImgVO(JFileChooser fileChooser) {
    File selectedFile = fileChooser.getSelectedFile();
    DealFilePathStr deal = new DealFilePathStr();
    String[] imagePathArr = deal.getImgPathsArray(selectedFile);

    ImageValueObject imgValObj = arrangeImgValObj(imagePathArr, selectedFile);
    return imgValObj;
  }

  /**
   * arrange 整理,编排
   * 
   * @param imagePathArr
   * @param selectedFile
   * @return
   */
  public ImageValueObject arrangeImgValObj(String[] imagePathArr, File selectedFile) {
    List<String> strList = Arrays.asList(imagePathArr);
    String currPath = selectedFile.getAbsolutePath();

    int currentOrder = strList.indexOf(currPath);
    System.out.println(this + " arrangeImgValObj currentOrder=" + currentOrder);

    ImageValueObject imgValObj = new ImageValueObject();
    imgValObj.setCurrentOrder(currentOrder);
    imgValObj.setCurrentPath(currPath);
    imgValObj.setCurrentPathsArray(imagePathArr);

    return imgValObj;
  }

}
