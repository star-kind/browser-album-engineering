package entities;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import components.MenuBarComponent;

public class FrameValueObject {
  public JFrame frame;
  public JPanel basePanel;
  public JLabel imageLabel;
  public MenuBarComponent menuBar;
  public ImageValueObject imageValObj;
  public SlideLantern slideLantern;

  public SlideLantern getSlideLantern() {
    return this.slideLantern;
  }

  public void setSlideLantern(SlideLantern slideLantern) {
    this.slideLantern = slideLantern;
  }

  public JLabel getImageLabel() {
    return this.imageLabel;
  }

  public void setImageLabel(JLabel imageLabel) {
    this.imageLabel = imageLabel;
  }

  public JFrame getFrame() {
    return this.frame;
  }

  public void setFrame(JFrame frame) {
    this.frame = frame;
  }

  public JPanel getBasePanel() {
    return this.basePanel;
  }

  public void setBasePanel(JPanel basePanel) {
    this.basePanel = basePanel;
  }

  public MenuBarComponent getMenuBar() {
    return this.menuBar;
  }

  public void setMenuBar(MenuBarComponent menuBar) {
    this.menuBar = menuBar;
  }

  public ImageValueObject getImageValObj() {
    return this.imageValObj;
  }

  public void setImageValObj(ImageValueObject imageValObj) {
    this.imageValObj = imageValObj;
  }

  @Override
  public String toString() {
    return "{" +
        " frame='" + getFrame() + "'" +
        ", basePanel='" + getBasePanel() + "'" +
        ", imageLabel='" + getImageLabel() + "'" +
        ", menuBar='" + getMenuBar() + "'" +
        ", imageValObj='" + getImageValObj() + "'" +
        ", slideLantern='" + getSlideLantern() + "'" +
        "}";
  }

}
