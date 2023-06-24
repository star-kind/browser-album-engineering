package events;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;
import entities.FrameValueObject;
import entities.ImageValueObject;
import services.ZoomImgHandler;
import utils.ImageUtil;

public class ZoomImgListener {
  public void treatZoomImg(FrameValueObject frameData, Double decision) {
    System.out.println(this + " getZoomImgData decision: " + decision);

    ZoomImgHandler handle = new ZoomImgHandler();
    ImageValueObject imgDO = frameData.getImageValObj();

    if (imgDO != null) {
      String path = imgDO.getCurrentPath();
      ImageUtil util = new ImageUtil();
      ImageIcon currentIcon = util.createImageIcon(path);// 获取当前图标

      JLabel imageLabel = frameData.getImageLabel();
      JPanel panel = frameData.getBasePanel();

      imageLabel = handle.zoomImage(imageLabel, decision, currentIcon);

      panel.add(imageLabel, BorderLayout.CENTER);
      frameData.setImageLabel(imageLabel);

    } else {
      System.out.println(this + " treatZoomImg 未曾打开任何图片");
    }

  }

  public void reconversionImageSize(FrameValueObject frameData) {
    JLabel imageLabel = frameData.getImageLabel();
    ImageValueObject imgVO = frameData.getImageValObj();

    if (imgVO != null) {
      String path = imgVO.getCurrentPath();

      ImageUtil util = new ImageUtil();
      ImageIcon currentIcon = util.createImageIcon(path);// 获取当前图标

      // 获取当前图片大小
      int originalWidth = util.getImgOriginSize(currentIcon).get(Constants.img_size_width_key);
      int originalHeight = util.getImgOriginSize(currentIcon).get(Constants.img_size_height_key);
      System.out.println(this + " OriginalSize:" + originalWidth + " x " +
          originalHeight);

      // 创建缩放后的图片
      Image brandNewImg = currentIcon.getImage().getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);
      imageLabel.setIcon(new ImageIcon(brandNewImg));
    } else {
      System.out.println(this.getClass() + " reconversionImageSize 未打开任何图片");
    }
  }

}