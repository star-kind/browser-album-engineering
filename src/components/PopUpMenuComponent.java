package components;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import constants.MenuBarConstants;
import entities.FrameValueObject;
import entities.PopUpValueObject;
import events.TrawlImgDetailListener;

public class PopUpMenuComponent {
  public PopUpValueObject createPopupMenu(FrameValueObject frameVal) {
    JPopupMenu popupMenu = new JPopupMenu();
    Font fontConfig = new Font("Arial", Font.BOLD, 16);
    Dimension popupSize = new Dimension(200, 150);

    // JMenuItem menuItem1 =
    // createMenuItem(MenuBarConstants.MENU_ITEM_CHECK_PROPERTIES, fontConfig);
    // menuItem1.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // TrawlImgDetailListener listener = new TrawlImgDetailListener();
    // listener.checkDetail(frameVal);
    // }
    // });

    // JMenuItem menuItem3 = createMenuItem("Test 3", fontConfig);
    // menuItem3.addActionListener(new ActionListener() {
    // @Override
    // public void actionPerformed(ActionEvent e) {
    // doSomething(333);
    // }
    // });

    JMenuItem menuItem1 = trawlDetailItem(fontConfig, frameVal);
    JMenuItem menuItem2 = test2(fontConfig);
    JMenuItem menuItem3 = test3(fontConfig);

    popupMenu.add(menuItem1);
    popupMenu.add(menuItem2);
    popupMenu.add(menuItem3);
    popupMenu.setPreferredSize(popupSize);

    PopUpValueObject popUpObj = new PopUpValueObject();
    popUpObj.setPopUpMenu(popupMenu);

    return popUpObj;
  }

  public JMenuItem trawlDetailItem(Font fontConfig, FrameValueObject frameVal) {
    JMenuItem menuItem1 = createMenuItem(MenuBarConstants.MENU_ITEM_CHECK_PROPERTIES, fontConfig);
    menuItem1.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        TrawlImgDetailListener listener = new TrawlImgDetailListener();
        listener.checkDetail(frameVal);
      }
    });
    return menuItem1;
  }

  public JMenuItem test2(Font fontConfig) {
    JMenuItem menuItem2 = createMenuItem("Test 2", fontConfig);
    menuItem2.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doSomething(222);
      }
    });
    return menuItem2;
  }

  public JMenuItem test3(Font fontConfig) {
    JMenuItem menuItem = createMenuItem("Test 3", fontConfig);
    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        doSomething(333);
      }
    });
    return menuItem;
  }

  public JMenuItem createMenuItem(String text, Font font) {
    JMenuItem menuItem = new JMenuItem(text);
    menuItem.setFont(font);
    return menuItem;
  }

  public void showAtCentral(Component component, JPopupMenu popupMenu) {
    int screenWidth = Toolkit.getDefaultToolkit().getScreenSize().width;
    int screenHeight = Toolkit.getDefaultToolkit().getScreenSize().height;
    int popupWidth = popupMenu.getPreferredSize().width;
    int popupHeight = popupMenu.getPreferredSize().height;

    int x = (screenWidth - popupWidth) / 2;
    int y = (screenHeight - popupHeight) / 2;

    popupMenu.show(component, x, y);
  }

  public void doSomething(int arg) {
    System.out.println(this + " Doing something... " + arg);
  }
}