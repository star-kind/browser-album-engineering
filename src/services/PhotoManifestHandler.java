package services;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.file.Files;

import entities.*;
import utils.ProcessTitle;

public class PhotoManifestHandler {
  public FrameValueObject openPhoto(FrameValueObject obj, String imagePath) {
    ProcessTitle p = new ProcessTitle();
    unfoldPhoto(obj.getBasePanel(), obj.getImageLabel(), imagePath, obj.getFrame());

    String title = p.getSingleTitle(imagePath);
    obj.getFrame().setTitle(title);
    return obj;
  }

  public void unfoldPhoto(JPanel panel, JLabel imageLabel, String imagePath, JFrame frame1) {
    clearPanel(panel);
    JLabel label = showImage(imageLabel, imagePath, frame1);
    panel.add(label, BorderLayout.CENTER);
  }

  public void clearPanel(JPanel panel) {
    // 移除 JPanel 中的所有子组件
    panel.removeAll();
    // 重新验证面板的布局
    panel.revalidate();
    // 重新绘制面板
    panel.repaint();
  }

  public JLabel showImage(JLabel imageLabel, String imagePath, JFrame frame1) {
    File imageFile = new File(imagePath);// 获取当前索引位置的图片文件
    System.out.println(this.getClass() + " showImage:" + imageFile.getName());

    // 通过SwingWorker将图片加载和显示的逻辑放在单独的线程中来改善性能
    SwingWorker<BufferedImage, Void> worker = new SwingWorker<BufferedImage, Void>() {
      @Override
      protected BufferedImage doInBackground() throws Exception {
        String fileType = Files.probeContentType(imageFile.toPath());

        if (fileType != null && fileType.equals("image/gif")) {// 检测文件类型
          LoadGifHandler load = new LoadGifHandler();
          load.loadGif(imageLabel, imagePath); // 处理 GIF 图片
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
            Image scaledImage = scaleImage(imageLabel, image);
            // 将缩放后的图片设置为 imageLabel 的图标
            imageLabel.setIcon(new ImageIcon(scaledImage));
          }
        } catch (Exception ex) {
          System.out.println(this.getClass() + " Error loading image:" + ex.getMessage());
        }
      }
    };
    worker.execute();
    return imageLabel;
  }

  public Image scaleImage(JLabel imageLabel, BufferedImage image) {
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
}