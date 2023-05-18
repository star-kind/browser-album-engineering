import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

public class ImageGalleryPanel extends JPanel {
  private int checkSlideDelayTime = 3000;// 幻灯片切换间隔时间（单位：毫秒）
  private JLabel imageLabel;
  private File[] imageFiles;
  private int currentIndex;
  private JMenuItem playMenuItem;
  private JMenuItem openMenuItem;
  private JMenuItem showListMenuItem;
  private JMenuItem selectDirectoryMenuItem;
  private volatile boolean slideshowRunning = false; // 幻灯片播放状态标志位

  public ImageGalleryPanel() {
    setLayout(new BorderLayout());
    createMenuBar();
    createImageLabel();
    setFocusTraversalKeysEnabled(false);
    bindKeyEvents();
  }

  private void createMenuBar() {
    JMenuBar menuBar = new JMenuBar();
    JMenu fileMenu = new JMenu("文件");

    openMenuItem = new JMenuItem("打开图片 ctrl + o");
    playMenuItem = new JMenuItem("播放 F5 (暂停:F9)");
    showListMenuItem = new JMenuItem("显示列表 ctrl + l");
    selectDirectoryMenuItem = new JMenuItem("选择路径 ctrl + p");

    fileMenu.add(openMenuItem);
    fileMenu.add(playMenuItem);
    fileMenu.add(showListMenuItem);
    fileMenu.add(selectDirectoryMenuItem);

    menuBar.add(fileMenu);
    add(menuBar, BorderLayout.NORTH);
  }

  private void createImageLabel() {
    imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setPreferredSize(new Dimension(800, 600)); // 设置固定尺寸
    add(imageLabel, BorderLayout.CENTER);
  }

  private void bindKeyEvents() {
    setFocusable(true);
    requestFocusInWindow();
    bindOpenImageEvent();
    bindKeyNavigationEvents();
    bindZoomEvents();
    bindOpenImageShortcut();
    bindPlaySlideshowEvent();
    bindShowImageListEvent();
    bindMoveImageEvent();
    addSelectDirectoryMenuItemActionListener();
  }

  private void addSelectDirectoryMenuItemActionListener() {
    selectDirectoryMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showSelectDirectoryDialog();
      }
    });

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_P && e.isControlDown()) {
          showSelectDirectoryDialog();
        }
      }
    });
  }

  private void showSelectDirectoryDialog() {
    JPanel selectDirectoryPanel = new JPanel(new BorderLayout());
    JTextField directoryField = new JTextField(20);
    JButton openButton = new JButton("Find and Open");

    openButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String directoryPath = directoryField.getText().trim();
        if (!directoryPath.isEmpty()) {
          File directory = new File(directoryPath);
          if (directory.exists() && directory.isDirectory()) {
            loadImagesFromDirectory(directory);
            showImage();
            // 关闭对话框
            Window window = SwingUtilities.getWindowAncestor(openButton);
            if (window instanceof JDialog) {
              JDialog dialog = (JDialog) window;
              dialog.dispose();
            }
          } else {
            showError("Invalid directory path.");
          }
        } else {
          showError("Please enter a directory path.");
        }
      }
    });

    directoryField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openButton.doClick();
      }
    });

    selectDirectoryPanel.add(directoryField, BorderLayout.CENTER);
    selectDirectoryPanel.add(openButton, BorderLayout.EAST);

    JOptionPane.showMessageDialog(this, selectDirectoryPanel, "Select Directory", JOptionPane.PLAIN_MESSAGE);
  }

  private void loadImagesFromDirectory(File directory) {
    List<File> imageFileList = new ArrayList<>();
    searchImagesInDirectory(directory, imageFileList);

    imageFiles = imageFileList.toArray(new File[0]);
    currentIndex = 0;
  }

  private void searchImagesInDirectory(File directory, List<File> imageFileList) {
    File[] files = directory.listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          searchImagesInDirectory(file, imageFileList); // 递归调用，搜索子目录中的图片文件
        } else if (isImageFile(file)) {
          imageFileList.add(file);
        }
      }
    }
  }

  private void showError(String message) {
    JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
  }

  private void bindOpenImageEvent() {
    openMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openImage();
      }
    });
  }

  private void bindKeyNavigationEvents() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_LEFT || keyCode == KeyEvent.VK_PAGE_UP || keyCode == KeyEvent.VK_UP) {
          showPreviousImage();
        } else if (keyCode == KeyEvent.VK_RIGHT || keyCode == KeyEvent.VK_PAGE_DOWN || keyCode == KeyEvent.VK_DOWN) {
          showNextImage();
        }
      }
    });
  }

  private void bindZoomEvents() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_ADD && e.isControlDown()) {
          zoomInImage();
        } else if (keyCode == KeyEvent.VK_SUBTRACT && e.isControlDown()) {
          zoomOutImage();
        }
      }
    });
  }

  private void bindOpenImageShortcut() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (e.isControlDown() && keyCode == KeyEvent.VK_O) {
          openImage();
        }
      }
    });
  }

  private void bindPlaySlideshowEvent() {
    playMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        /*
         * 在用户点击 playMenuItem 菜单项时触发 actionPerformed() 方法，并在该方法中调用
         * startSlideshow()方法来开始幻灯片播放
         */
        startSlideshow();
      }
    });

    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_F5) {
          slideshowRunning = true; // 在开始幻灯片播放时将 slideshowRunning 设置为 true
          startSlideshow();
        } else if (keyCode == KeyEvent.VK_F9) {
          slideshowRunning = false; // 设置标志位为false，停止幻灯片播放
          System.out.println("f9.slideshowRunning: " + slideshowRunning);
        }
      }
    });
  }

  private void bindShowImageListEvent() {
    showListMenuItem.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        showImageList();
      }
    });

    // 添加按键事件监听器
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (e.isControlDown() && keyCode == KeyEvent.VK_L) {
          showImageList();
        }
      }
    });
  }

  private void bindMoveImageEvent() {
    addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        if (keyCode == KeyEvent.VK_DELETE) {
          moveImageToDirectory();
        }
      }
    });
  }

  // 新方法：绑定键盘事件
  private void bindEnterKeyEvents(JList<String> imageList, DefaultListModel<String> listModel) {
    imageList.addKeyListener(new KeyAdapter() {
      @Override
      public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_ENTER) {
          int selectedIndex = imageList.getSelectedIndex();
          if (selectedIndex >= 0) {
            System.out.println("Selected: " + listModel.getElementAt(selectedIndex));
            currentIndex = selectedIndex;
            System.out.println("currentIndex: " + currentIndex);
            showImage();
          }
        }
      }
    });
  }

  // 新方法：绑定鼠标双击事件
  private void bindMouseDoubleClickEvent(JList<String> imageList, DefaultListModel<String> listModel) {
    imageList.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (e.getClickCount() == 2) {
          int selectedIndex = imageList.getSelectedIndex();
          if (selectedIndex >= 0) {
            System.out.println("Double-clicked: " + listModel.getElementAt(selectedIndex));
          }
          currentIndex = selectedIndex;
          System.out.println("currentIndex: " + currentIndex);
          showImage();
        }
      }
    });
  }

  // 显示图片列表
  private void showImageList() {
    if (imageFiles == null || imageFiles.length == 0) {
      JOptionPane.showMessageDialog(this, "No images available.", "Image List", JOptionPane.INFORMATION_MESSAGE);
      return;
    }

    JPanel listPanel = new JPanel(new BorderLayout());
    DefaultListModel<String> listModel = new DefaultListModel<>();
    int index = 1;

    for (File file : imageFiles) {
      String imageName = file.getName();
      String item = String.format("%d. %s", index, imageName);
      listModel.addElement(item);
      index++;
    }

    JList<String> imageList = new JList<>(listModel);
    JScrollPane scrollPane = new JScrollPane(imageList);
    listPanel.add(scrollPane);

    // 绑定键盘事件
    bindEnterKeyEvents(imageList, listModel);

    // 绑定鼠标双击事件
    bindMouseDoubleClickEvent(imageList, listModel);

    JDialog dialog = new JDialog();
    dialog.setTitle("图片列表");
    dialog.setSize(400, 300);
    dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setLocationRelativeTo(this);
    dialog.setContentPane(listPanel);
    dialog.setVisible(true);
  }

  private void moveImageToDirectory() {
    if (imageFiles != null && imageFiles.length > 0) {
      File imageFile = imageFiles[currentIndex];
      String os = System.getProperty("os.name").toLowerCase();

      File destinationDirectory;
      if (os.contains("linux")) {
        destinationDirectory = new File(System.getProperty("user.home"));
      } else if (os.contains("windows")) {
        destinationDirectory = new File(System.getProperty("user.home"), "Pictures");
      } else {
        System.out.println("Unsupported operating system.");
        return;
      }

      File destinationFile = new File(destinationDirectory, imageFile.getName());

      try {
        Files.move(imageFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
        System.out.println("图片移动成功");

        // 移除已移动的图片文件
        List<File> remainingFiles = new ArrayList<>(Arrays.asList(imageFiles));
        remainingFiles.remove(currentIndex);

        // 更新 imageFiles 数组和 currentIndex 指针
        imageFiles = remainingFiles.toArray(new File[0]);
        if (currentIndex >= imageFiles.length) {
          currentIndex = imageFiles.length - 1;
        }

        // 显示下一张图片（或者没有图片时清空显示）
        if (currentIndex >= 0) {
          showImage();
        } else {
          imageLabel.setIcon(null);
        }
      } catch (IOException ex) {
        System.out.println("图片移动失败: " + ex.getMessage());
      }
    }
  }

  private void startSlideshow() {
    System.out.println("slide show running: " + slideshowRunning);
    if (slideshowRunning == false) {// 判断是否已经在播放幻灯片
      return; // 如果已经在播放，直接返回
    }

    // 判断是否有图片可供播放
    if (imageFiles != null && imageFiles.length > 0) {
      // 创建并启动一个后台线程来执行幻灯片播放
      Thread slideshowThread = new Thread(new Runnable() {
        @Override
        public void run() {
          int slideshowInterval = checkSlideDelayTime;
          try {
            while (slideshowRunning == true) { // 检查标志位是否为true
              System.out.println("while.slideshowRunning: " + slideshowRunning);
              showNextImage();// 显示下一张图片
              Thread.sleep(slideshowInterval); // 暂停一段时间
            }
          } catch (InterruptedException e) {
            System.out.println("interrupte_thread");// 线程被中断，退出幻灯片播放
          }
        }
      });
      slideshowThread.start();
    }
  }

  private void openImage() {
    JFileChooser fileChooser = new JFileChooser();// 创建文件选择器
    fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
    int result = fileChooser.showOpenDialog(this); // 显示文件选择对话框

    if (result == JFileChooser.APPROVE_OPTION) {// 如果选择了文件
      File selectedFile = fileChooser.getSelectedFile();// 获取选中的文件
      File parentDirectory = selectedFile.getParentFile(); // 获取选中文件的父目录
      File[] filesInDirectory = parentDirectory.listFiles();// 获取父目录中的所有文件
      List<File> imageFileList = new ArrayList<>();// 创建用于存储图片文件的列表

      for (File file : filesInDirectory) { // 遍历父目录中的所有文件
        if (isImageFile(file)) {// 检查文件是否为图片文件
          imageFileList.add(file); // 将图片文件添加到列表中
        }
      }

      if (!imageFileList.isEmpty()) {// 如果存在图片文件
        imageFiles = imageFileList.toArray(new File[0]);// 将图片文件列表转换为数组
        currentIndex = 0;// 重置当前索引为第一个图片
        showImage();// 显示图片
      }
    }
  }

  private boolean isImageFile(File file) {
    String name = file.getName().toLowerCase();
    return name.endsWith(".jpg") || name.endsWith(".jpeg") || name.endsWith(".png") || name.endsWith(".gif");
  }

  private void showPreviousImage() {
    if (imageFiles != null && imageFiles.length > 0) {
      currentIndex = (currentIndex - 1 + imageFiles.length) % imageFiles.length;
      showImage();
    }
  }

  private void showNextImage() {
    if (imageFiles != null && imageFiles.length > 0) {
      currentIndex = (currentIndex + 1) % imageFiles.length;
      showImage();
    }
  }

  private void showImage() {
    // 获取当前索引位置的图片文件
    File imageFile = imageFiles[currentIndex];
    System.out.println("show_image: " + imageFile.getName());

    // 通过SwingWorker将图片加载和显示的逻辑放在单独的线程中来改善性能
    SwingWorker<BufferedImage, Void> worker = new SwingWorker<BufferedImage, Void>() {
      @Override
      protected BufferedImage doInBackground() throws Exception {
        // 检测文件类型
        String fileType = Files.probeContentType(imageFile.toPath());
        if (fileType != null && fileType.equals("image/gif")) {
          loadGif(imageFile.getAbsolutePath()); // 处理 GIF 图片
        } else {
          return ImageIO.read(imageFile); // 返回读取到的图片
        }
        return null;
      }

      @Override
      protected void done() {
        try {
          BufferedImage image = get();
          if (image != null) {
            // 缩放图片，并设置平滑缩放模式
            Image scaledImage = scaleImage(image);
            // 将缩放后的图片设置为 imageLabel 的图标
            imageLabel.setIcon(new ImageIcon(scaledImage));
          }
          updateImageLabel(imageFile); // 更新图片标签和面板标题
        } catch (Exception ex) {
          System.out.println("Error loading image: " + ex.getMessage());
        }
      }
    };

    worker.execute();
  }

  private void loadGif(String filePath) {
    try {
      File gifFile = new File(filePath);
      BufferedImage image = ImageIO.read(gifFile);
      ImageIcon gifIcon = new ImageIcon(image);
      imageLabel.setIcon(gifIcon);

      // 更新图片标签和面板标题
      updateImageLabel(gifFile);
    } catch (IOException ex) {
      System.out.println("Error loading GIF image: " + ex.getMessage());
    }
  }

  private void updateImageLabel(File imageFile) {
    // 获取当前图片的路径和名称
    String imagePath = imageFile.getAbsolutePath();

    JFrame frame = (JFrame) SwingUtilities.getWindowAncestor(this);
    frame.setTitle(imagePath);// 将路径设置为面板的标题
    // 创建一个新的Font对象，设置字体为当前字体，但是字号更大
    Font currentFont = frame.getFont();
    Font biggerFont = currentFont.deriveFont(currentFont.getSize() + 4f); // 增加4个字号
    frame.setFont(biggerFont);
  }

  private Image scaleImage(BufferedImage image) {
    // 获取图片的原始宽度和高度
    int originalWidth = image.getWidth();
    int originalHeight = image.getHeight();
    // 获取图片标签的宽度和高度
    int labelWidth = imageLabel.getWidth();
    int labelHeight = imageLabel.getHeight();
    // 计算宽度和高度的缩放比例
    double widthScale = (double) labelWidth / originalWidth;
    double heightScale = (double) labelHeight / originalHeight;
    double scale = Math.min(widthScale, heightScale);
    // 缩放图片，并设置平滑缩放模式
    return image.getScaledInstance((int) (originalWidth * scale), (int) (originalHeight * scale), Image.SCALE_SMOOTH);
  }

  private void zoomInImage() {
    // 获取当前图标
    ImageIcon currentIcon = (ImageIcon) imageLabel.getIcon();
    if (currentIcon != null) {
      // 获取当前图片大小
      int currentWidth = currentIcon.getIconWidth();
      int currentHeight = currentIcon.getIconHeight();
      // 计算缩放后的大小
      int newWidth = (int) (currentWidth * 1.1);
      int newHeight = (int) (currentHeight * 1.1);
      // 创建缩放后的图片
      Image newImage = currentIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
      // 更新图片标签的图标
      imageLabel.setIcon(new ImageIcon(newImage));
    }
  }

  private void zoomOutImage() {
    // 获取当前图标
    ImageIcon currentIcon = (ImageIcon) imageLabel.getIcon();
    if (currentIcon != null) {
      // 获取当前图片大小
      int currentWidth = currentIcon.getIconWidth();
      int currentHeight = currentIcon.getIconHeight();
      // 计算缩放后的大小
      int newWidth = (int) (currentWidth * 0.9);
      int newHeight = (int) (currentHeight * 0.9);
      // 创建缩放后的图片
      Image newImage = currentIcon.getImage().getScaledInstance(newWidth, newHeight, Image.SCALE_SMOOTH);
      // 更新图片标签的图标
      imageLabel.setIcon(new ImageIcon(newImage));
    }
  }

  public static void main(String[] args) {
    SwingUtilities.invokeLater(new Runnable() {
      public void run() {
        JFrame frame = new JFrame("Image Gallery");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); // 设置窗口最大化
        frame.setContentPane(new ImageGalleryPanel());
        frame.setVisible(true);
        frame.getContentPane().requestFocusInWindow(); // 请求焦点
      }
    });
  }
}
