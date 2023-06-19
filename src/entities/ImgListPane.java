package entities;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JPanel;

public class ImgListPane {
  public JPanel listPanel;
  public DefaultListModel<String> listModel;
  public JList<String> imageList;

  public JPanel getListPanel() {
    return this.listPanel;
  }

  public void setListPanel(JPanel listPanel) {
    this.listPanel = listPanel;
  }

  public DefaultListModel<String> getListModel() {
    return this.listModel;
  }

  public void setListModel(DefaultListModel<String> listModel) {
    this.listModel = listModel;
  }

  public JList<String> getImageList() {
    return this.imageList;
  }

  public void setImageList(JList<String> imageList) {
    this.imageList = imageList;
  }

  @Override
  public String toString() {
    return "{" +
        " listPanel='" + getListPanel() + "'" +
        ", listModel='" + getListModel() + "'" +
        ", imageList='" + getImageList() + "'" +
        "}";
  }

}