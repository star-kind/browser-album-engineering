package components;

import javax.swing.*;
import java.util.HashMap;
import java.util.Map;

import constants.*;
import entities.MenuBarValueObject;

public class MenuBarComponent { // extends JMenuBar
  // private Map<String, JMenu> fileMenusMap;
  // public MenuBarComponent() {
  // }

  public MenuBarValueObject generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    Map<String, JMenu> fileMenusMap = new HashMap<>();

    JMenu fileMenu = createMenu(MenuBarConstants.MENU_TITLE, MenuBarConstants.MENU_ITEM_ARRAY);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE, fileMenu);

    JMenu fileMenu0 = createMenu(MenuBarConstants.MENU_TITLE_0, MenuBarConstants.MENU_ITEM_ARRAY_0);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_0, fileMenu0);

    JMenu fileMenu1 = createMenu(MenuBarConstants.MENU_TITLE_1, MenuBarConstants.MENU_ITEM_ARRAY_1);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_1, fileMenu1);

    JMenu fileMenu2 = createMenu(MenuBarConstants.MENU_TITLE_2, MenuBarConstants.MENU_ITEM_ARRAY_2);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_2, fileMenu2);

    // 将所有的 fileMenu 添加到菜单栏
    for (JMenu menu : fileMenusMap.values()) {
      menuBar.add(menu);
    }

    MenuBarValueObject menuBarVO = new MenuBarValueObject(fileMenusMap, menuBar);
    return menuBarVO;
  }

  public JMenu createMenu(String menuString, String[] menuItemStrings) {
    JMenu menu = new JMenu(menuString);

    for (String menuItemString : menuItemStrings) {
      JMenuItem menuItem = new JMenuItem(menuItemString);
      menu.add(menuItem);
    }
    return menu;
  }

}
