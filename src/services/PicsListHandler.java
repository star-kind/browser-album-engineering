package services;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import entities.FrameValueObject;
import entities.ImgListPane;
import utils.DealFilePathStr;
import components.DialogComponent;
import components.ListLabelBuilderComponent;
import constants.Constants;

public class PicsListHandler {
  public void showImageList(String[] imagesUrlArray, FrameValueObject obj) {
    DealFilePathStr deal = new DealFilePathStr();
    File[] imageFilesArr = deal.strArr2FileArr(imagesUrlArray);

    ListLabelBuilderComponent comp = new ListLabelBuilderComponent();
    ImgListPane listPane = comp.createListPanel(imageFilesArr, obj);
    // 绑定键盘事件
    bindEnterKeyEvents(listPane.getImageList(), listPane.getListModel(), imagesUrlArray, obj);
    // 绑定鼠标双击事件
    bindMouseDoubleClickEvent(listPane.getImageList(), listPane.getListModel(), imagesUrlArray, obj);
    showingDialog(listPane.getListPanel(), obj);
  }

  public void showingDialog(JPanel listPanel, FrameValueObject obj) {
    DialogComponent dialog = new DialogComponent();
    dialog.getShowInfoDialog(listPanel, Constants.list_dialog_title, obj.getBasePanel(), 400, 400);
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
            OpenPhotoHandler photo = new OpenPhotoHandler();
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
            OpenPhotoHandler photo = new OpenPhotoHandler();
            photo.openPhoto(obj, imagesUrlArray[selectedIndex]);
          }
        }
      }
    });
  }

}