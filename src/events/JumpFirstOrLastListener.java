package events;

import constants.Constants;
import entities.FrameValueObject;
import entities.ImageValueObject;
import services.OpenPhotoHandler;

public class JumpFirstOrLastListener {
  public void jumpByFlag(FrameValueObject frameDO, int flag) {
    ImageValueObject imgJO = frameDO.getImageValObj();
    if (imgJO == null) {
      System.out.println(this + " Has not any being opening picture");
      return;
    }

    OpenPhotoHandler photoHandler = new OpenPhotoHandler();
    if (flag == Constants.first_picture) {
      String path = imgJO.getCurrentPathsArray()[0];
      photoHandler.openPhoto(frameDO, path);
    } else if (flag == Constants.last_picture) {
      int lastIndex = imgJO.getCurrentPathsArray().length - 1;
      String path = imgJO.getCurrentPathsArray()[lastIndex];
      photoHandler.openPhoto(frameDO, path);
    }
  }

}