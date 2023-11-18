package utils;

import java.awt.Component;
import java.util.Map;

import javax.swing.JMenu;
import javax.swing.JMenuItem;

import entities.FrameValueObject;

public class GetTargetMenuItem {
  public JMenuItem getMenuItemByName(JMenu menu, String targetMenuItemName) {
    for (int i = 0; i < menu.getMenuComponentCount(); i++) {
      Component menuComponent = menu.getMenuComponent(i);
      if (menuComponent instanceof JMenuItem) {
        JMenuItem menuItem = (JMenuItem) menuComponent;
        if (menuItem.getText().equals(targetMenuItemName)) {
          return menuItem;
        }
      }
    }
    return null;
  }

  /**
   * Designate 指定
   */
  public JMenuItem getDesignateMenuItem(String menuTitle, String itemName, FrameValueObject frameData) {
    Map<String, JMenu> fileMenuMap = frameData.getMenuBarValueObject().getMenusMap();
    JMenu fileMenu = fileMenuMap.get(menuTitle);
    JMenuItem item = getMenuItemByName(fileMenu, itemName);
    return item;
  }
}