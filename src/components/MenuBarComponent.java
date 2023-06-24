package components;

import javax.swing.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import constants.*;
import entities.MenuBarValueObject;

public class MenuBarComponent {
  public MenuBarValueObject generateMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    Map<String, JMenu> fileMenusMap = new HashMap<>();

    JMenu fileMenu = createMenu(MenuBarConstants.MENU_TITLE_1, MenuBarConstants.MENU_ITEM_ARRAY_1);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_1, fileMenu);

    JMenu fileMenu0 = createMenu(MenuBarConstants.MENU_TITLE_2, MenuBarConstants.MENU_ITEM_ARRAY_2);

    JMenu fileMenu1 = createMenu(MenuBarConstants.MENU_TITLE_3, MenuBarConstants.MENU_ITEM_ARRAY_3);

    JMenu fileMenu2 = createMenu(MenuBarConstants.MENU_TITLE_4, MenuBarConstants.MENU_ITEM_ARRAY_4);

    fileMenusMap.put(MenuBarConstants.MENU_TITLE_1, fileMenu);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_2, fileMenu0);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_3, fileMenu1);
    fileMenusMap.put(MenuBarConstants.MENU_TITLE_4, fileMenu2);

    List<Map.Entry<String, JMenu>> list = sortMenu(fileMenusMap);

    // 将所有的 fileMenu 添加到菜单栏
    for (Map.Entry<String, JMenu> mapping : list) {
      menuBar.add(mapping.getValue());
    }

    MenuBarValueObject menuBarVO = new MenuBarValueObject(fileMenusMap, menuBar);
    return menuBarVO;
  }

  public List<Map.Entry<String, JMenu>> sortMenu(Map<String, JMenu> fileMenusMap) {
    List<Map.Entry<String, JMenu>> list = new ArrayList<Map.Entry<String, JMenu>>(fileMenusMap.entrySet());

    Collections.sort(list, new Comparator<Map.Entry<String, JMenu>>() {
      // 升序排序
      @Override
      public int compare(Entry<String, JMenu> o1, Entry<String, JMenu> o2) {
        return o1.getValue().getText().compareTo(o2.getValue().getText());
      }

    });

    for (Map.Entry<String, JMenu> mapping : list) {
      System.out.println(this + " " + mapping.getKey() + ": " + mapping.getValue().getText());
    }
    return list;
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
