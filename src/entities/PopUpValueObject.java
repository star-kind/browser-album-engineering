package entities;

import javax.swing.JPopupMenu;

public class PopUpValueObject {
  public JPopupMenu popUpMenu;

  public JPopupMenu getPopUpMenu() {
    return this.popUpMenu;
  }

  public void setPopUpMenu(JPopupMenu popUpMenu) {
    this.popUpMenu = popUpMenu;
  }

  @Override
  public String toString() {
    return "{" +
        " popUpMenu='" + getPopUpMenu() + "'" +
        "}";
  }

}