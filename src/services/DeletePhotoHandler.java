package services;

import javax.swing.*;

import constants.Constants;
import entities.ImageValueObject;
import utils.DealFilePathStr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DeletePhotoHandler {
  public ImageValueObject moveImageToDirectory(File[] imageFilesArr, int currentIndex, JLabel imageLabel) {
    if (imageFilesArr == null || imageFilesArr.length == 0) {
      return null;
    }

    File imageFile = imageFilesArr[currentIndex];
    File destinationDirectory = getDestinationDirectory();
    File destinationFile = new File(destinationDirectory, imageFile.getName());

    try {
      moveImageFile(imageFile, destinationFile);
      System.out.println(this + " 图片移动成功");

      List<File> remainingFiles = removeMovedFile(imageFilesArr, currentIndex);
      currentIndex = updateCurrentIndex(currentIndex, remainingFiles);

      ImageValueObject vo = handleNextImage(remainingFiles, currentIndex, imageLabel);
      return vo;
    } catch (IOException ex) {
      System.out.println(this + " 图片移动失败: " + ex.getMessage());
    }

    return null;
  }

  private File getDestinationDirectory() {
    String os = System.getProperty("os.name").toLowerCase();
    File destinationDirectory;

    if (os.contains("linux")) {
      destinationDirectory = new File(System.getProperty("user.home") + File.separator +
          Constants.recycle_path);
    } else if (os.contains("windows")) {
      destinationDirectory = new File(System.getProperty("user.home"), "Pictures");
    } else {
      System.out.println("Unsupported operating system.");
      return null;
    }

    if (!destinationDirectory.exists()) {
      boolean created = destinationDirectory.mkdirs();
      if (!created) {
        System.out.println("Failed to create destination directory.");
        return null;
      }
    }
    return destinationDirectory;
  }

  public void moveImageFile(File sourceFile, File destinationFile) throws IOException {
    Files.move(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
  }

  public List<File> removeMovedFile(File[] imageFilesArr, int currentIndex) {
    List<File> remainingFiles = new ArrayList<>(Arrays.asList(imageFilesArr));
    remainingFiles.remove(currentIndex);
    return remainingFiles;
  }

  public int updateCurrentIndex(int currentIndex, List<File> remainingFiles) {
    return Math.min(currentIndex, remainingFiles.size() - 1);
  }

  public ImageValueObject handleNextImage(List<File> remainingFiles, int currentIndex, JLabel imageLabel) {
    if (currentIndex >= 0) {
      ImageValueObject obj = createImageValueObject(remainingFiles, currentIndex);
      return obj;
      // setImageValueObject(obj);
    } else {
      imageLabel.setIcon(null);
      return null;
    }
  }

  public ImageValueObject createImageValueObject(List<File> remainingFiles, int currentIndex) {
    // ImageValueObject obj = new ImageValueObject();
    DealFilePathStr deal = new DealFilePathStr();

    File[] fileArr = remainingFiles.toArray(new File[remainingFiles.size()]);
    String[] arr = deal.filesArr2StrArr(fileArr);

    // obj.setCurrentPathsArray(arr);
    // obj.setCurrentOrder(currentIndex);
    // obj.setCurrentPath(arr[currentIndex]);
    ImageValueObject obj = setImageValueObject(arr, currentIndex);
    return obj;
  }

  public ImageValueObject setImageValueObject(String[] arr, int currentIndex) {
    ImageValueObject obj = new ImageValueObject();
    obj.setCurrentPathsArray(arr);
    obj.setCurrentOrder(currentIndex);
    obj.setCurrentPath(arr[currentIndex]);
    return obj;
  }

}