package services;

import java.awt.BorderLayout;
import java.awt.Component;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import components.DialogComponent;
import entities.FrameValueObject;
import entities.ImageValueObject;
import utils.DealFilePathStr;
import utils.ImageUtil;

public class InputPathHandler {
  public ImageValueObject loadImgsFromDir(File directory) {
    List<File> imageFileList = new ArrayList<>();
    imageFileList = searchImgsInDir(directory, imageFileList);

    DealFilePathStr deal = new DealFilePathStr();
    File[] imageFilesArr = imageFileList.toArray(new File[0]);
    imageFilesArr = deal.orderByName(imageFilesArr);

    String[] strPathArr = deal.filesArr2StrArr(imageFilesArr);
    if (strPathArr.length < 1) {
      System.out.println(this + " 该目录下无图片文件");
      return null;
    }

    int currentIndex = 0;
    String currPath = strPathArr[currentIndex];

    ImageValueObject vo = new ImageValueObject();
    vo.setCurrentOrder(currentIndex);
    vo.setCurrentPath(currPath);
    vo.setCurrentPathsArray(strPathArr);
    return vo;
  }

  public List<File> searchImgsInDir(File directory, List<File> imageFileList) {
    File[] files = directory.listFiles();
    ImageUtil util = new ImageUtil();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          searchImgsInDir(file, imageFileList); // 递归调用，搜索子目录中的图片文件
        } else if (util.isImageFile(file)) {
          imageFileList.add(file);
        }
      }
    }

    return imageFileList;
  }

  public void showErrorDialog(String errInfo, Component parenComponent, String title) {
    DialogComponent component = new DialogComponent();
    String btnString = "Close";
    int width = 200;
    int height = 120;
    component.getCustomDialog(btnString, parenComponent, title, errInfo, width, height);
  }

  public void showOptPane(FrameValueObject frameJO, JButton openButton, JTextField directoryField) {
    JPanel selectDirectoryPanel = new JPanel(new BorderLayout());
    selectDirectoryPanel.add(directoryField, BorderLayout.CENTER);
    selectDirectoryPanel.add(openButton, BorderLayout.EAST);
    String titleStr = "please input directory path";

    JOptionPane.showMessageDialog(frameJO.getFrame(), selectDirectoryPanel,
        titleStr,
        JOptionPane.PLAIN_MESSAGE);
  }
}