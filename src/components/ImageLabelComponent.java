package components;

import java.awt.Dimension;
import javax.swing.JLabel;

public class ImageLabelComponent {
  public JLabel createImageLabel() {
    JLabel imageLabel = new JLabel();
    imageLabel.setHorizontalAlignment(JLabel.CENTER);
    imageLabel.setPreferredSize(new Dimension(800, 600)); // 设置固定尺寸
    return imageLabel;
  }
}