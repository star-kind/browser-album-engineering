package entities;

import java.util.Map;
import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class MenuBarValueObject {
  public Map<String, JMenu> menusBarMap;
  public JMenuBar menuBar;

  public MenuBarValueObject(Map<String, JMenu> menusMap, JMenuBar menuBar) {
    this.menusBarMap = menusMap;
    this.menuBar = menuBar;
  }

  public Map<String, JMenu> getMenusMap() {
    return this.menusBarMap;
  }

  public void setMenusMap(Map<String, JMenu> menusMap) {
    this.menusBarMap = menusMap;
  }

  public JMenuBar getMenuBar() {
    return this.menuBar;
  }

  public void setMenuBar(JMenuBar menuBar) {
    this.menuBar = menuBar;
  }

  @Override
  public String toString() {
    return "{" +
        " menusBarMap='" + getMenusMap() + "'" +
        ", menuBar='" + getMenuBar() + "'" +
        "}";
  }

}