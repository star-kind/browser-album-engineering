package components;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import constants.*;

public class MenuBarComponent extends JMenuBar {
  private Map<String, JMenu> fileMenus;

  public MenuBarComponent() {
    fileMenus = new HashMap<>();

    JMenu fileMenu1 = createMenu(MenuBarConstants.FILE_MENU_TITLE, MenuBarConstants.MENU_ITEM_ARR);
    fileMenus.put(MenuBarConstants.FILE_MENU_TITLE, fileMenu1);

    // 将所有的 fileMenu 添加到菜单栏
    for (JMenu fileMenu : fileMenus.values()) {
      add(fileMenu);
    }
  }

  public JMenu createMenu(String menuString, String[] menuItemStrings) {
    JMenu menu = new JMenu(menuString);

    for (String menuItemString : menuItemStrings) {
      JMenuItem menuItem = new JMenuItem(menuItemString);
      menu.add(menuItem);
    }

    return menu;
  }

  public Map<String, JMenu> getFileMenus() {
    return fileMenus;
  }
}
