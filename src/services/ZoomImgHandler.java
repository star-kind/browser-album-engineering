package services;

import java.awt.Image;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import exceptions.CustomException;
import utils.ImageUtil;
import constants.*;

public class ZoomImgHandler {
  public JLabel zoomImage(JLabel imageLabel, Double decision, ImageIcon currentIcon) throws CustomException {
    validateParameters(imageLabel, decision);

    ScaleFactorHandler scaleHandle = new ScaleFactorHandler();
    Double scaleFactor = 0.0;

    try {
      scaleFactor = scaleHandle.processDecision(decision);
    } catch (IOException e) {
      e.printStackTrace();
    }

    if (currentIcon != null) {
      Image scaledImage = scaleImage(currentIcon, scaleFactor);
      imageLabel.setIcon(new ImageIcon(scaledImage));
    }

    return imageLabel;
  }

  public void validateParameters(JLabel imageLabel, Double decision) throws CustomException {
    if (imageLabel == null) {
      throw new CustomException("imageLabel is null");
    }

    if (decision == null) {
      throw new CustomException("decision is empty");
    }

    if (!decision.equals(Constants.zoom_in_decision) && !decision.equals(Constants.zoom_out_decision)) {
      throw new CustomException("decision is illegal");
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
    System.out.println(this.getClass() + " ScaledSize: " + brandNewWidth + " x " + brandNewHeight);

    // 创建缩放后的图片
    Image brandNewImg = currentIcon.getImage().getScaledInstance(brandNewWidth, brandNewHeight, Image.SCALE_SMOOTH);

    return brandNewImg;
  }

}
