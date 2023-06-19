package services;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import entities.FrameValueObject;
import entities.ImgListPane;
import components.DialogComponent;
import constants.Constants;

public class PicsListHandler {
  public void showImageList(String[] imagesUrlArray, FrameValueObject obj) {
    File[] imageFilesArr = convertToFiles(imagesUrlArray);
    ImgListPane listPane = createListPanel(imageFilesArr, obj);
    // 绑定键盘事件
    bindEnterKeyEvents(listPane.getImageList(), listPane.getListModel(), imagesUrlArray, obj);
    // 绑定鼠标双击事件
    bindMouseDoubleClickEvent(listPane.getImageList(), listPane.getListModel(), imagesUrlArray, obj);
    showingDialog(listPane.getListPanel(), obj);
  }

  public ImgListPane createListPanel(File[] imageFilesArr, FrameValueObject obj) {
    int index = 1;
    JPanel listPanel = new JPanel(new BorderLayout());
    DefaultListModel<String> listModel = new DefaultListModel<>();

    for (File file : imageFilesArr) {
      String imageName = file.getName();
      String item = String.format("%d. %s", index, imageName);
      listModel.addElement(item);
      index++;
    }

    JList<String> imageList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(imageList);
    listPanel.add(scrollPane);
    imageList.setSelectedIndex(obj.getImageValObj().getCurrentOrder()); // 设置元素被选中

    ImgListPane pane = getListPane(imageList, listModel, listPanel);
    return pane;
  }

  public ImgListPane getListPane(JList<String> imageList, DefaultListModel<String> listModel, JPanel listPanel) {
    ImgListPane pane = new ImgListPane();
    pane.setImageList(imageList);
    pane.setListModel(listModel);
    pane.setListPanel(listPanel);
    return pane;
  }

  public void showingDialog(JPanel listPanel, FrameValueObject obj) {
    DialogComponent dialog = new DialogComponent();
    dialog.getShowingDialog(listPanel, obj, Constants.list_dialog_title);
  }

  // 新方法：绑定键盘事件
  public void bindEnterKeyEvents(JList<String> imageList, DefaultListModel<String> listModel, String[] imagesUrlArray,
      FrameValueObject obj) {
    imageList.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          int selectedIndex = imageList.getSelectedIndex();
          if (selectedIndex >= 0) {
            PhotoManifestHandler photo = new PhotoManifestHandler();
            photo.openPhoto(obj, imagesUrlArray[selectedIndex]);
          }
        }
      }
    });
  }

  // 新方法：绑定鼠标双击事件
  public void bindMouseDoubleClickEvent(JList<String> imageList, DefaultListModel<String> listModel,
      String[] imagesUrlArray, FrameValueObject obj) {
    imageList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          int selectedIndex = imageList.getSelectedIndex();
          if (selectedIndex >= 0) {
            PhotoManifestHandler photo = new PhotoManifestHandler();
            photo.openPhoto(obj, imagesUrlArray[selectedIndex]);
          }
        }
      }
    });
  }

  public File[] convertToFiles(String[] pathStrArray) {
    File[] fileArray = new File[pathStrArray.length];

    for (int i = 0; i < pathStrArray.length; i++) {
      fileArray[i] = new File(pathStrArray[i]);
    }
    return fileArray;
  }

}