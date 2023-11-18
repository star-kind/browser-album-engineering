package services;

import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import utils.ImageUtil;
import constants.*;

public class ZoomImgHandler {
  public JLabel zoomImage(JLabel imageLabel, Double decision, ImageIcon currentIcon) {
    validateParameters(imageLabel, decision);

    ScaleFactorHandler scaleHandle = new ScaleFactorHandler();
    Double scaleFactor = 0.0;

    scaleFactor = scaleHandle.processDecision(decision);

    if (currentIcon != null) {
      Image scaledImage = scaleImage(currentIcon, scaleFactor);
      imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    return imageLabel;
  }

  public void validateParameters(JLabel imageLabel, Double decision) {// throws CustomException
    if (imageLabel == null) {
      System.out.println(this + " imageLabel is null");
      return;
    }

    if (decision == null) {
      System.out.println(this + " decision is empty");
      return;
    }

    if (!decision.equals(Constants.zoom_in_decision) && !decision.equals(Constants.zoom_out_decision)) {
      System.out.println(this + " decision is illegal");
      return;
    }
  }

  public Image scaleImage(ImageIcon currentIcon, double scaleFactor) {
    ImageUtil util = new ImageUtil();
    // 获取当前图片大小
    int originalWidth = util.getImgOriginSize(currentIcon).get(Constants.img_size_width_key);
    int originalHeight = util.getImgOriginSize(currentIcon).get(Constants.img_size_height_key);

    // 计算缩放后的大小
    int brandNewWidth = (int) (originalWidth * scaleFactor);
    int brandNewHeight = (int) (originalHeight * scaleFactor);
    System.out.println(this + " ScaledSize: " + brandNewWidth + " x " + brandNewHeight);

    // 创建缩放后的图片
    Image brandNewImg = currentIcon.getImage().getScaledInstance(brandNewWidth, brandNewHeight, Image.SCALE_SMOOTH);

    return brandNewImg;
  }

}
