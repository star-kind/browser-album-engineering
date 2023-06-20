package events;

import java.io.File;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.OpenPhotoHandler;
import services.SlidePlayHandler;
import utils.DealFilePathStr;

public class SlidePlayListener {
  public void autoPlaying(FrameValueObject obj, boolean switchSlide) {
    SlidePlayHandler handler = new SlidePlayHandler();
    OpenPhotoHandler photo = new OpenPhotoHandler();
    DealFilePathStr deal = new DealFilePathStr();
    ImageValueObject imgObj = obj.getImageValObj();

    if (imgObj != null) {
      File[] filesArr = deal.strArr2FileArr(imgObj.getCurrentPathsArray());
      obj.getSlideLantern().setSwitchSlide(switchSlide);

      handler.startSlideShow(obj, filesArr, photo);
    } else {
      System.out.println(this.getClass() + " autoPlaying 尚未打开任何图片");
    }
  }

}