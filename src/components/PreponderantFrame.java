package components;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import entities.*;
import utils.GetTargetMenuItem;
import events.*;
import services.*;
import constants.*;

public class PreponderantFrame {
  public FrameValueObject frameData;

  public void kernel() {
    this.frameData = new FrameValueObject();

    initializeUI();
    produceFrameValueObject();

    openAndDisplayImage();

    bindZoomInEvent();
    bindZoomOutEvent();
    bindZoomReconversionEvent();

    bindPrevNavEvent();
    bindNextNavEvent();

    bindSlidePlayEvent();

    bindShowImageListEvent();

    bindDeleteEvent();

    bindSelectEvent();

    bindJumpFirstOrLastEvent();

    bindInputPathEvent();

    bindRightClickEvent();
    bindFilePropertyEvent();
  }

  public void initializeUI() {
    createFrame();
    createLabel();
    createBasePanel();
  }

  public void produceFrameValueObject() {
    MenuBarComponent menuBar = new MenuBarComponent();
    this.frameData.menuBarValueObject = menuBar.generateMenuBar();

    FunctionalityClass functionality = new FunctionalityClass(this.frameData);
    this.frameData = functionality.getFrameVO();
  }

  public void createFrame() {
    this.frameData = new FrameValueObject();
    this.frameData.frame = new JFrame("Main Component");
    this.frameData.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.frameData.frame.setFocusable(true);
    this.frameData.frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 设置窗口最大化
  }

  public void createLabel() {
    JLabel label = new JLabel();
    label.setHorizontalAlignment(JLabel.CENTER);
    label.setPreferredSize(new Dimension(800, 600)); // 设置固定尺寸
    this.frameData.imageLabel = label;
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
    JMenuItem openPicItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE,
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
      }
    });

    openPicItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
  }

  public void bindZoomInEvent() {
    JPanel panel = frameData.basePanel;
    ZoomImgListener listener = new ZoomImgListener();

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem zoomInMenuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_0,
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
    JMenuItem outMenuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_0,
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

  public void bindZoomReconversionEvent() {
    JPanel panel = frameData.basePanel;
    ZoomImgListener listener = new ZoomImgListener();

    GetTargetMenuItem t = new GetTargetMenuItem();
    JMenuItem menuItem = t.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_0,
        MenuBarConstants.MENU_ITEM_ZOOM_RECONVERION, frameData);

    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.reconversionImageSize(frameData);
      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_0 && e.isControlDown()) {
          listener.reconversionImageSize(frameData);

        } else if (keyCode == KeyEvent.VK_NUMPAD0 && e.isControlDown()) {
          listener.reconversionImageSize(frameData);

        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
    panel.setFocusable(true);
  }

  public void bindPrevNavEvent() {
    JPanel panel = frameData.basePanel;
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem prevMenuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_1,
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
    JMenuItem nextMenuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_1,
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

    JMenuItem listItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_2,
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

  public void bindDeleteEvent() {
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem deleteMenuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE,
        MenuBarConstants.MENU_ITEM_DELETE, frameData);

    // 设置快捷键 Delete
    KeyStroke deleteKeyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_DELETE, 0);
    String key = "DELETE";
    DeletePhotoListener listener = new DeletePhotoListener();

    ActionListener deleteListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.deletePhoto(frameData);
      }
    };

    deleteMenuItem.addActionListener(deleteListener);
    deleteMenuItem.setAccelerator(deleteKeyStroke);
    deleteMenuItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.deletePhoto(frameData);
      }
    });

    deleteMenuItem.getInputMap().put(deleteKeyStroke, key);
  }

  public void bindSelectEvent() {
    SelectDirectoryListener select = new SelectDirectoryListener();

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem menuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE,
        MenuBarConstants.MENU_ITEM_SELECT_DIRECTORY, frameData);

    // 设置快捷键
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_F, KeyEvent.CTRL_DOWN_MASK);
    String key = "SelectOneDirectory";

    ActionListener selectListener = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        select.listenerCenter(frameData);
      }
    };

    menuItem.addActionListener(selectListener);
    menuItem.setAccelerator(keyStroke);
    menuItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        select.listenerCenter(frameData);
      }
    });

    menuItem.getInputMap().put(keyStroke, key);
  }

  public void bindInputPathEvent() {
    InputPathListener listener = new InputPathListener();

    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem menuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE,
        MenuBarConstants.MENU_ITEM_INPUT_PATH, frameData);

    // 设置快捷键
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_P, KeyEvent.CTRL_DOWN_MASK);
    String key = "INPUT_PATH";

    ActionListener action = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.showInputPathDialog(frameData);
      }
    };

    menuItem.addActionListener(action);
    menuItem.setAccelerator(keyStroke);
    menuItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.showInputPathDialog(frameData);
      }
    });

    menuItem.getInputMap().put(keyStroke, key);
  }

  public void bindJumpFirstOrLastEvent() {
    JPanel panel = frameData.basePanel;
    JumpFirstOrLastListener listener = new JumpFirstOrLastListener();

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_HOME) {
          listener.jumpByFlag(frameData, Constants.first_picture);
        } else if (keyCode == KeyEvent.VK_END) {
          listener.jumpByFlag(frameData, Constants.last_picture);
        }
      }
    });

    panel.requestFocusInWindow();// 确保面板获得焦点
    panel.setFocusable(true);
  }

  public void bindRightClickEvent() {
    JPanel panel = frameData.basePanel;

    PopUpMenuComponent comp = new PopUpMenuComponent();
    JPopupMenu popupMenu = comp.createPopupMenu(frameData).getPopUpMenu();

    panel.addMouseListener(new MouseAdapter() { // 当前组件的鼠标点击事件
      @Override
      public void mouseClicked(MouseEvent e) {
        // MouseEvent.BUTTON1: 左键点击,MouseEvent.BUTTON2: 中间点击(滑轮)
        if (e.getButton() == MouseEvent.BUTTON3) {// 设置右键点击事件
          popupMenu.show(e.getComponent(), e.getX(), e.getY());
        }
      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_CONTEXT_MENU) {
          comp.showAtCentral(panel, popupMenu);
        }
      }
    });

    panel.requestFocusInWindow(); // 确保面板获得焦点
    panel.setFocusable(true);
  }

  public void bindFilePropertyEvent() {
    TrawlImgDetailListener listener = new TrawlImgDetailListener();
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem menuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_2,
        MenuBarConstants.MENU_ITEM_CHECK_PROPERTIES, frameData);

    String key = "FilePropertyAction";
    KeyStroke keyStroke = KeyStroke.getKeyStroke(KeyEvent.VK_I, KeyEvent.CTRL_DOWN_MASK);

    ActionListener action = new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.checkDetail(frameData);
      }
    };

    menuItem.addActionListener(action);
    menuItem.setAccelerator(keyStroke);
    menuItem.getActionMap().put(key, new AbstractAction() {
      @Override
      public void actionPerformed(ActionEvent e) {
        listener.checkDetail(frameData);
      }
    });

    menuItem.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(keyStroke, key);
  }

  public void bindSlidePlayEvent() {
    JPanel panel = frameData.basePanel;
    GetTargetMenuItem target = new GetTargetMenuItem();
    JMenuItem menuItem = target.getDesignateMenuItem(MenuBarConstants.MENU_TITLE_1,
        MenuBarConstants.MENU_ITEM_SLIDE_PLAY, frameData);

    SlidePlayListener listener = new SlidePlayListener();

    menuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        boolean flag = frameData.getSlideLantern().getSwitchSlide();
        System.out.println(this + " bindSlidePlayEvent FLAG=" + flag);

        if (flag == Constants.stop_slide_play) {
          listener.autoPlaying(frameData, Constants.start_slide_play);
        } else if (flag == Constants.start_slide_play) {
          listener.autoPlaying(frameData, Constants.stop_slide_play);
        }

      }
    });

    panel.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_F9) {
          listener.autoPlaying(frameData, Constants.stop_slide_play);
        } else if (keyCode == KeyEvent.VK_F5) {
          listener.autoPlaying(frameData, Constants.start_slide_play);
        }
      }
    });
    // 确保面板获得焦点
    panel.requestFocusInWindow();
  }
}