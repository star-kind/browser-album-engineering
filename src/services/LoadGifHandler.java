package services;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LoadGifHandler {
  public void loadGif(JLabel imageLabel, String filePath) throws IOException {
    System.out.println(this.getClass() + " loadGif: filePath:" + filePath);
    imageLabel.removeAll();
    displayGif(filePath, imageLabel);
    imageLabel.revalidate();
    imageLabel.repaint();
  }

  public void displayGif(String imagePath, JLabel gifLabel) {
    ImageIcon imageIcon = createImageIcon(imagePath);
    if (imageIcon != null) {
      gifLabel.setIcon(imageIcon);
      gifLabel.setHorizontalAlignment(JLabel.CENTER); // 水平居中
    } else {
      System.out.println(this + " imageLabel: Failed to open GIF image.");
    }
  }

  public ImageIcon createImageIcon(String imagePath) {
    try {
      File imageFile = new File(imagePath);
      URL imageUrl = imageFile.toURI().toURL();
      return new ImageIcon(imageUrl);
    } catch (MalformedURLException e) {
      e.printStackTrace();
      return null;
    }
  }
}