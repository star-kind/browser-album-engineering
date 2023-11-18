package services;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

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
    imageFilesArr = deal.listSort(imageFilesArr);

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
    component.getCustomDialog(btnString, parenComponent, title, errInfo);
  }

  public void showOptPane(FrameValueObject frameJO, JButton openButton, JTextField directoryField) {

    JPanel selectDirectoryPanel = new JPanel(new BorderLayout());
    selectDirectoryPanel.add(directoryField, BorderLayout.CENTER);
    selectDirectoryPanel.add(openButton, BorderLayout.EAST);
    String titleStr = "Please Input A Directory Path";
    String[] options = { "Close" };

    JOptionPane optionPane = new JOptionPane(selectDirectoryPanel, JOptionPane.PLAIN_MESSAGE,
        JOptionPane.DEFAULT_OPTION, null, options, options[0]);
    optionPane.setPreferredSize(new Dimension(580, 100)); // 设置弹窗的尺寸

    // 创建一个新的字体对象，设置输入框中文字的尺寸
    Font textFieldFont = new Font("Arial", Font.PLAIN, 20);
    directoryField.setFont(textFieldFont);

    // 设置弹窗中的文字尺寸
    UIManager.put("OptionPane.messageFont", new Font("Arial", Font.BOLD, 20));
    UIManager.put("OptionPane.buttonFont", new Font("Arial", Font.PLAIN, 20));

    JDialog dialog = optionPane.createDialog(frameJO.getFrame(), titleStr);
    dialog.setVisible(true);
  }

}