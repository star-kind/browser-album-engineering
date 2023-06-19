package events;

import javax.swing.*;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.PhotoManifestHandler;
import utils.DealFilePathStr;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class DeletePhotoListener {
  public void deletePhoto(FrameValueObject framePOJO) {// TODO 重构优化
    ImageValueObject imgJO = framePOJO.getImageValObj();
    if (imgJO == null) {
      System.out.println(this + " Has not any PICTURE");
      return;
    }

    DealFilePathStr deal = new DealFilePathStr();
    File[] fileArr = deal.strArr2FileArr(imgJO.getCurrentPathsArray());

    imgJO = moveImageToDirectory(fileArr, imgJO.getCurrentOrder(), framePOJO.getImageLabel());
    if (imgJO == null) {
      System.out.println(this + " Has not any picture");
      return;
    }

    framePOJO.setImageValObj(imgJO);
    PhotoManifestHandler handler = new PhotoManifestHandler();
    handler.openPhoto(framePOJO, imgJO.getCurrentPath());
  }

  public ImageValueObject moveImageToDirectory(File[] imageFilesArr, int currentIndex, JLabel imageLabel) {
    if (imageFilesArr != null && imageFilesArr.length > 0) {
      File imageFile = imageFilesArr[currentIndex];
      String os = System.getProperty("os.name").toLowerCase();

      File destinationDirectory;
      if (os.contains("linux")) {
        destinationDirectory = new File(System.getProperty("user.home"));
      } else if (os.contains("windows")) {
        destinationDirectory = new File(System.getProperty("user.home"), "Pictures");
      } else {
        System.out.println("Unsupported operating system.");
        return null;
      }

      File destinationFile = new File(destinationDirectory, imageFile.getName());

      try {
        Files.move(imageFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println(this + " 图片移动成功");

        // 移除已移动的图片文件
        List<File> remainingFiles = new ArrayList<>(Arrays.asList(imageFilesArr));
        remainingFiles.remove(currentIndex);

        // 更新 imageFiles 数组和 currentIndex 指针
        imageFilesArr = remainingFiles.toArray(new File[0]);
        if (currentIndex >= imageFilesArr.length) {
          currentIndex = imageFilesArr.length - 1;
        }

        // 显示下一张图片（或者没有图片时清空显示）
        if (currentIndex >= 0) {
          ImageValueObject obj = new ImageValueObject();
          DealFilePathStr deal = new DealFilePathStr();
          String[] arr = deal.filesArr2StrArr(imageFilesArr);

          obj.setCurrentPathsArray(arr);
          obj.setCurrentOrder(currentIndex);
          obj.setCurrentPath(arr[currentIndex]);
          return obj;
        } else {
          imageLabel.setIcon(null);
        }
      } catch (IOException ex) {
        System.out.println(this + " 图片移动失败: " + ex.getMessage());
      }
    }
    return null;
  }
}