package constants;

import java.nio.file.Path;
import java.nio.file.Paths;

public class Constants {
  public static String json_folder_path = "../temporary";

  public static String img_json_file = "PICTURE.JSON";

  public static String scale_json_file = "SCALE-FACTOR.JSON";

  /**
   * 放大
   */
  public static final Double zoom_in_decision = 1.1;

  /**
   * 缩小
   */
  public static final Double zoom_out_decision = 0.9;

  /**
   * 扩大的上限
   */
  public static final double zoom_upper_limit = 1.9;

  /**
   * 缩放的下限
   */
  public static final double zoom_lower_limit = 0.5;

  /**
   * 下1张
   */
  public static final Integer next_pic = 1;

  /**
   * 上1张
   */
  public static final Integer previous_pic = 0;

  public static final String list_dialog_title = "Image List";

  public static final String warn_dialog_msg = "No images available.";

  public static final String warn_dialog_title = "Warning";

  /**
   * 开始播放幻灯片
   */
  public static final Boolean start_slide_play = true;

  /**
   * 停止播放幻灯片
   */
  public static final Boolean stop_slide_play = false;

  /**
   * 幻灯片延迟时间
   */
  public static final Integer slide_play_delay = 5000;

  public static final String img_size_width_key = "width";
  public static final String img_size_height_key = "height";

  /**
   * 第1张
   */
  public static final Integer first_picture = 0;

  /**
   * 末1张
   */
  public static final Integer last_picture = 1;

  /**
   * Conflate 合成
   * 
   * @param folderPath
   * @param filePath
   * @return
   */
  public static Path getConflatePath(String folderPath, String filePath) {
    Path conflatePath = Paths.get(folderPath, filePath);
    return conflatePath;
  }
}