package utils;

import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import java.io.File;
import java.io.IOException;

import constants.Constants;
import entities.ImageValueObject;
import json.JsonFileHandler;

public class ImageUtil {
  public void injectValObj2JsonFile(ImageValueObject imgVal) {
    if (imgVal != null) {
      JsonFileHandler jfHandler = new JsonFileHandler();
      try {
        jfHandler.writeJsonToFile(imgVal, Constants.json_folder_path, Constants.img_json_file);
      } catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      System.out.println(this + " ImageValueObject imgVal is NULL");
    }
  }

  public ImageIcon createImageIcon(String imagePath) {
    File imageFile = new File(imagePath);

    if (!imageFile.exists()) {
      // 文件不存在，可以抛出异常或者返回默认图标
      throw new IllegalArgumentException("指定的图片文件不存在: " + imagePath);
    }

    // 检查文件是否是有效的图片文件
    if (!isImageFile(imageFile)) {
      // 文件不是有效的图片文件，可以抛出异常或者返回默认图标
      throw new IllegalArgumentException("指定的文件不是有效的图片文件: " + imagePath);
    }

    return new ImageIcon(imageFile.getPath());
  }

  public boolean isImageFile(File file) {
    String name = file.getName();
    String extension = name.substring(name.lastIndexOf('.') + 1).toLowerCase();
    // 检查文件扩展名是否为图片格式，你可以根据需要添加其他格式的判断条件
    return extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif");
  }

  public Map<String, Integer> getImgOriginSize(ImageIcon currentIcon) {// 获取当前图片大小
    int originalWidth = currentIcon.getIconWidth();
    int originalHeight = currentIcon.getIconHeight();

    Map<String, Integer> mp = new HashMap<>();
    mp.put(Constants.img_size_width_key, originalWidth);
    mp.put(Constants.img_size_height_key, originalHeight);
    return mp;
  }

}
