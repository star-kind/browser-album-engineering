package events;

import java.io.File;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.OpenPhotoHandler;
import services.SlidePlayHandler;
import utils.DealFilePathStr;

public class SlidePlayListener {
  private SlidePlayHandler handler; // 成员变量

  public SlidePlayListener() {
    handler = new SlidePlayHandler(); // 在构造函数中创建 SlidePlayHandler 对象
  }

  public void autoPlaying(FrameValueObject obj, boolean switchSlide) {
    OpenPhotoHandler photo = new OpenPhotoHandler();
    DealFilePathStr deal = new DealFilePathStr();
    ImageValueObject imgObj = obj.getImageValObj();

    if (imgObj != null) {
      File[] filesArr = deal.strArr2FileArr(imgObj.getCurrentPathsArray());

      if (switchSlide == true) {
        handler.startSlideShow(obj, filesArr, photo, switchSlide);
      } else if (switchSlide == false) {
        handler.stopSlideShow();
      }
    } else {
      System.out.println(this + " autoPlaying 尚未打开任何图片");
    }
  }
}