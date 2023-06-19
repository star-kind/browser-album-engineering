package components;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import entities.*;
import utils.GetTargetMenuItem;
import events.*;
import services.*;
import constants.*;

public class PreponderantFrame {
  public FrameValueObject frameData;

  public void kernel(MenuBarComponent menuBar) {
    this.frameData = new FrameValueObject();

    initialize(menuBar);

    FunctionalityClass functionality = new FunctionalityClass(this.frameData);
    this.frameData = functionality.getFrameVO();

    openAndDisplayImage();

    bindZoomInEvent();
    bindZoomOutEvent();
    bindZoomReconversionEvent();// TODO 触发快捷键之时无响应(小键盘数字无法触发)

    bindPrevNavEvent();
    bindNextNavEvent();

    bindShowImageListEvent();

    bindStartPlayEvent();
    bindStopPlayEvent();

    bindDeleteEvent();

    bindSelectEvent();

    bindJumpFirstOrLastEvent();

    bindCloseAllDialogEvent();
  }

  public void initialize(MenuBarComponent menuBar) {
    createFrame();
    createLabel();
    createBasePanel();
    this.frameData.setMenuBar(menuBar);
  }

  public void createFrame() {
    this.frameData = new FrameValueObject();
    this.frameData.frame = new JFrame("Main Component");
    this.frameData.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frameData.frame.setFocusable(true);
    this.frameData.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 设置窗口最大化
  }

  public void createLabel() {
    ImageLabelComponent component = new ImageLabelComponent();
    this.frameData.imageLabel = component.createImageLabel();
  }

  public void createBasePanel() {
    this.frameData.basePanel = new JPanel(new BorderLayout());
    this.frameData.basePanel.add(this.frameData.imageLabel, BorderLayout.CENTER);
  }

  public void openAndDisplayImage() {
    String key = "OpenPictureAction";
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_O, KeyEvent.CTRL_DOWN_MASK);
    OpenPictureListenerHelper open = new OpenPictureListenerHelper();

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem openPicItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_OPEN_PICTURE, frameData);

    ActionListener openPictureListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        open.openPicture(frameData);
      }
    };

    openPicItem.addActionListener(openPictureListener);
    openPicItem.setAccelerator(keyStroke);

    openPicItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        open.openPicture(frameData);
        System.out.println(this + " =CurrentOrder= " + frameData.getImageValObj().getCurrentOrder());
      }
    });

    openPicItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
  }

  public void bindZoomInEvent() {
    JPanel panel = frameData.basePanel;
    ZoomImgListener listener = new ZoomImgListener();

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem zoomInMenuItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_ZOOM_IN, frameData);

    zoomInMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.treatZoomImg(frameData, Constants.zoom_in_decision);
      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ADD && e.isControlDown()) {
          listener.treatZoomImg(frameData, Constants.zoom_in_decision);
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
    panel.setFocusable(true);
  }

  public void bindZoomOutEvent() {
    JPanel panel = frameData.basePanel;
    ZoomImgListener listener = new ZoomImgListener();

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem outMenuItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_ZOOM_OUT, frameData);

    outMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.treatZoomImg(frameData, Constants.zoom_out_decision);
      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_SUBTRACT && e.isControlDown()) {
          listener.treatZoomImg(frameData, Constants.zoom_out_decision);
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
    panel.setFocusable(true);
  }

  // public void bindZoomReconversionEvent() {
  // JPanel panel = frameData.basePanel;
  // ZoomImgListener listener = new ZoomImgListener();

  // GetTargetMenuItem t = new GetTargetMenuItem();
  // JMenuItem menuItem = t.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
  // MenuBarConstants.MENU_ITEM_ZOOM_RECONVERION, frameData);

  // menuItem.addActionListener(new ActionListener() {
  // @Override
  // public void actionPerformed(ActionEvent e) {
  // listener.reconversionImageSize(frameData);
  // }
  // });

  // panel.addKeyListener(new KeyAdapter() {
  // @Override
  // public void keyPressed(KeyEvent e) {
  // int keyCode = e.getKeyCode();
  // if (keyCode == KeyEvent.VK_0 && e.isControlDown()) {
  // System.out.println(this + " bindZoomReconversionEvent");
  // listener.reconversionImageSize(frameData);
  // }
  // }
  // });
  // // 确保面板获得焦点
  // panel.requestFocusInWindow();
  // panel.setFocusable(true);
  // }

  public void bindZoomReconversionEvent() {
    String key = "RECONVERION";
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_0, KeyEvent.CTRL_DOWN_MASK);

    ZoomImgListener listener = new ZoomImgListener();
    GetTargetMenuItem target = new GetTargetMenuItem();

    JMenuItem item = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_ZOOM_RECONVERION, frameData);

    ActionListener actListen = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.reconversionImageSize(frameData);
      }
    };

    item.addActionListener(actListen);
    item.setAccelerator(keyStroke);
    item.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.reconversionImageSize(frameData);
      }
    });

    // 给JMenuItem请求焦点
    item.requestFocusInWindow();
    // 修改输入映射参数为 JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT
    item.getInputMap(JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT).put(keyStroke, key);
    // item.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);

  }

  public void bindPrevNavEvent() {
    JPanel panel = frameData.basePanel;
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem prevMenuItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_PREVIOUS, frameData);
    NavigationPicListener nav = new NavigationPicListener();

    prevMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        nav.navigation(frameData, Constants.previous_pic);
      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_PAGE_UP || keyCode == KeyEvent.VK_UP) {
          nav.navigation(frameData, Constants.previous_pic);
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
  }

  public void bindNextNavEvent() {
    JPanel panel = frameData.basePanel;
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem nextMenuItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_NEXT, frameData);
    NavigationPicListener nav = new NavigationPicListener();

    nextMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        nav.navigation(frameData, Constants.next_pic);
      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_PAGE_DOWN
            || keyCode == KeyEvent.VK_DOWN) {
          nav.navigation(frameData, Constants.next_pic);
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
  }

  public void bindShowImageListEvent() {
    String key = "ListPictureAction";
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK);

    PicsCollectListener p = new PicsCollectListener();
    GetTargetMenuItem target = new GetTargetMenuItem();

    JMenuItem listItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_EXAMINE_LIST, frameData);

    ActionListener listPictureListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        p.coreListener(frameData);
      }
    };

    listItem.addActionListener(listPictureListener);
    listItem.setAccelerator(keyStroke);
    listItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        p.coreListener(frameData);
      }
    });

    listItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
  }

  public void bindStartPlayEvent() {
    String key = "StartPlayAction";
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F5, 0);

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem startItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_SLIDE_START, frameData);

    SlidePlayListener slide = new SlidePlayListener();

    ActionListener startListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        slide.autoPlaying(frameData, Constants.start_slide_play);
      }
    };

    startItem.addActionListener(startListener);
    startItem.setAccelerator(keyStroke);
    startItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        slide.autoPlaying(frameData, Constants.start_slide_play);
      }
    });

    startItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
  }

  public void bindStopPlayEvent() {
    String key = "StopPlayAction";
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F9, 0);

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem stopItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_SLIDE_STOP, frameData);
    SlidePlayListener slide = new SlidePlayListener();

    ActionListener stopListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        slide.autoPlaying(frameData, Constants.stop_slide_play);
      }
    };

    stopItem.addActionListener(stopListener);
    stopItem.setAccelerator(keyStroke);
    stopItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        slide.autoPlaying(frameData, Constants.stop_slide_play);
      }
    });

    stopItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
  }

  public void bindDeleteEvent() {
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem deleteMenuItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_DELETE, frameData);

    // 设置快捷键 Delete
    KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
    String key = "DELETE";

    ActionListener deleteListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println(this + " bindDeleteEvent");
      }
    };

    deleteMenuItem.addActionListener(deleteListener);
    deleteMenuItem.setAccelerator(deleteKeyStroke);
    deleteMenuItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println(this + " bindDeleteEvent");
      }
    });

    deleteMenuItem.getInputMap().put(deleteKeyStroke, key);
  }

  public void bindSelectEvent() {
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem menuItem = target.getDesignateMenuItem(MenuBarConstants.FILE_MENU_TITLE,
        MenuBarConstants.MENU_ITEM_SELECT_DIRECTORY, frameData);

    // 设置快捷键 Delete
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
    String key = "SelectOneDirectory";

    ActionListener selectListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println(this + " bind Select Event");
      }
    };

    menuItem.addActionListener(selectListener);
    menuItem.setAccelerator(keyStroke);
    menuItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        System.out.println(this + " bind Select Event");
      }
    });

    menuItem.getInputMap().put(keyStroke, key);
  }

  public void bindJumpFirstOrLastEvent() {// TODO 跳转到首张或末张图片
    JPanel panel = frameData.basePanel;

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_HOME) {
          System.out.println(this + " bindJumpFirstOrLastEvent 跳转到首张");
        } else if (keyCode == KeyEvent.VK_END) {
          System.out.println(this + " bindJumpFirstOrLastEvent 跳转到末张图片");
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
    panel.setFocusable(true);
  }

  public void bindCloseAllDialogEvent() {// TODO bindCloseAllDialogEvent
    JPanel panel = frameData.basePanel;

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ESCAPE) {
          System.out.println(this + " bind Close All Dialog Event");
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
    panel.setFocusable(true);
  }
}