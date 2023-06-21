package events;

import java.awt.BorderLayout;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import constants.Constants;
import entities.FrameValueObject;
import entities.ImageValueObject;
import exceptions.CustomException;
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

      System.out.println(this + " Zoom will index=" + imgDO.getCurrentOrder());
      System.out.println(this + " Zoom will PATH=" + path);

      JLabel imageLabel = frameData.getImageLabel();
      JPanel panel = frameData.getBasePanel();
      try {
        imageLabel = handle.zoomImage(imageLabel, decision, currentIcon);

      } catch (CustomException e) {
        e.printStackTrace();
      }
      panel.add(imageLabel, BorderLayout.CENTER);
      frameData.setImageLabel(imageLabel);

    } else {
      System.out.println(this.getClass() + " treatZoomImg 未曾打开任何图片");
    }

  }

  public void reconversionImageSize(FrameValueObject frameData) {
    JLabel imageLabel = frameData.getImageLabel();
    ImageValueObject imgVO = frameData.getImageValObj();

    if (imgVO != null) {
      int index = imgVO.getCurrentOrder();
      String path = imgVO.getCurrentPath();

      System.out.println(this + " reconversionImageSize index=" + index);
      System.out.println(this + " reconversionImageSize path=" + path);

      ImageUtil util = new ImageUtil();
      ImageIcon currentIcon = util.createImageIcon(path);// 获取当前图标

      // 获取当前图片大小
      int originalWidth = util.getImgOriginSize(currentIcon).get(Constants.img_size_width_key);
      int originalHeight = util.getImgOriginSize(currentIcon).get(Constants.img_size_height_key);

      // 创建缩放后的图片
      Image brandNewImg = currentIcon.getImage().getScaledInstance(originalWidth, originalHeight, Image.SCALE_SMOOTH);
      imageLabel.setIcon(new ImageIcon(brandNewImg));
    } else {
      System.out.println(this.getClass() + " reconversionImageSize 未打开任何图片");
    }
  }

}