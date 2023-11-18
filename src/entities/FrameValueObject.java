package entities;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class FrameValueObject {
  public JFrame frame;
  public JPanel basePanel;
  public JLabel imageLabel;
  public ImageValueObject imageValObj;
  public SlideLantern slideLantern;
  public MenuBarValueObject menuBarValueObject;

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

  public JLabel getImageLabel() {
    return this.imageLabel;
  }

  public void setImageLabel(JLabel imageLabel) {
    this.imageLabel = imageLabel;
  }

  public ImageValueObject getImageValObj() {
    return this.imageValObj;
  }

  public void setImageValObj(ImageValueObject imageValObj) {
    this.imageValObj = imageValObj;
  }

  public SlideLantern getSlideLantern() {
    return this.slideLantern;
  }

  public void setSlideLantern(SlideLantern slideLantern) {
    this.slideLantern = slideLantern;
  }

  public MenuBarValueObject getMenuBarValueObject() {
    return this.menuBarValueObject;
  }

  public void setMenuBarValueObject(MenuBarValueObject menuBarValueObject) {
    this.menuBarValueObject = menuBarValueObject;
  }

  @Override
  public String toString() {
    return "{" +
        " frame='" + getFrame() + "'" +
        ", basePanel='" + getBasePanel() + "'" +
        ", imageLabel='" + getImageLabel() + "'" +
        ", imageValObj='" + getImageValObj() + "'" +
        ", slideLantern='" + getSlideLantern() + "'" +
        ", menuBarValueObject='" + getMenuBarValueObject() + "'" +
        "}";
  }

}
