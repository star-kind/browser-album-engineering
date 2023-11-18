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

  public ImageValueObject getImgByFilePath(String fileUrl) {
    File file = new File(fileUrl);

    if (!file.exists()) {
      System.out.println(this + " The file is Absent");
      return null;
    }
    verifyFileType(file);

    DealFilePathStr deal = new DealFilePathStr();
    String[] imagePathArr = deal.getImgPathsArray(file);

    ImageValueObject imgValObj = arrangeImgValObj(imagePathArr, file);
    return imgValObj;
  }

  public void verifyFileType(File file) {
    // 获取文件的扩展名
    String extension = getFileExtension(file);
    // 检查文件是否是有效的图片文件
    boolean isImageFile = false;
    String[] imageExtensions = { "jpg", "png", "gif", "jpeg", "PNG", "JPEG", "JPG", "GIF" };

    for (String ext : imageExtensions) {
      if (extension.equalsIgnoreCase(ext)) {
        isImageFile = true;
        break;
      }
    }

    if (!isImageFile) {
      System.out.println(this + " File is not an image file");
      return;
    }
  }

  // 获取文件的扩展名
  public String getFileExtension(File file) {
    String fileName = file.getName();
    int index = fileName.lastIndexOf(".");
    return fileName.substring(index + 1);
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
    System.out.println(this + " arrangeImgValObj.currentOrder:" + currentOrder);

    ImageValueObject imgValObj = new ImageValueObject();
    imgValObj.setCurrentOrder(currentOrder);
    imgValObj.setCurrentPath(currPath);
    imgValObj.setCurrentPathsArray(imagePathArr);

    return imgValObj;
  }

}
