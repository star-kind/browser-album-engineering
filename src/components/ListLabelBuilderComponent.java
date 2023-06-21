package components;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Font;
import java.io.File;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import entities.FrameValueObject;
import entities.ImgListPane;

public class ListLabelBuilderComponent {
  public ImgListPane createListPanel(File[] imageFilesArr, FrameValueObject obj) {
    DefaultListModel<String> listModel = createListModel(imageFilesArr);
    JList<String> imageList = createImageList(listModel, obj);
    JPanel listPanel = getListPanel(imageList);
    setLabelTextSize(listPanel, 16); // 设置文字尺寸

    ImgListPane pane = getListPane(imageList, listModel, listPanel);
    return pane;
  }

  public DefaultListModel<String> createListModel(File[] imageFilesArr) {
    DefaultListModel<String> listModel = new DefaultListModel<>();

    int index = 1;
    for (File file : imageFilesArr) {
      String imageName = file.getName();
      String item = String.format("%d. %s", index, imageName);
      listModel.addElement(item);
      index++;
    }

    return listModel;
  }

  public JList<String> createImageList(DefaultListModel<String> listModel, FrameValueObject obj) {
    JList<String> imageList = new JList<>(listModel);
    imageList.setSelectedIndex(obj.getImageValObj().getCurrentOrder()); // 设置元素被选中

    // 设置JList的显示属性
    imageList.setLayoutOrientation(JList.VERTICAL);
    imageList.setVisibleRowCount(-1);

    // 设置滚动策略
    JScrollPane scrollPane = new JScrollPane(imageList);
    scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

    return imageList;
  }

  public JPanel getListPanel(JList<String> imageList) {
    JPanel listPanel = new JPanel(new BorderLayout());
    JScrollPane scrollPane = new JScrollPane(imageList);
    listPanel.add(scrollPane);

    return listPanel;
  }

  private void setLabelTextSize(JPanel panel, int textSize) {
    for (Component component : panel.getComponents()) {
      if (component instanceof JLabel) {
        JLabel label = (JLabel) component;
        Font font = label.getFont();
        label.setFont(font.deriveFont(Font.BOLD, textSize));
      }
    }
  }

  public ImgListPane getListPane(JList<String> imageList, DefaultListModel<String> listModel, JPanel listPanel) {
    ImgListPane pane = new ImgListPane();
    pane.setImageList(imageList);
    pane.setListModel(listModel);
    pane.setListPanel(listPanel);
    return pane;
  }
}
