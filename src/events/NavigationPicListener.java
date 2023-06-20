package events;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.NavigationPicHandler;
import services.OpenPhotoHandler;

public class NavigationPicListener {
  public void navigation(FrameValueObject obj, int navFlag) {
    NavigationPicHandler handler = new NavigationPicHandler();
    OpenPhotoHandler photo = new OpenPhotoHandler();
    ImageValueObject imageValueObj = obj.getImageValObj();

    if (imageValueObj != null) {
      String[] imageFiles = imageValueObj.getCurrentPathsArray();
      int currentIndex = imageValueObj.getCurrentOrder();
      obj = handler.showByImageFlag(navFlag, imageFiles, currentIndex, photo, obj);
    } else {
      System.out.println(this.getClass() + " navigation:当前未打开任何图片");
    }
  }

}