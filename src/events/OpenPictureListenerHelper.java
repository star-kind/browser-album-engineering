package events;

import entities.*;
import services.*;

public class OpenPictureListenerHelper {
  public FrameValueObject openPicture(FrameValueObject obj) {
    FileChooserHandler fileHandler = new FileChooserHandler();
    PhotoManifestHandler photo = new PhotoManifestHandler();
    ImageValueObject imgObj = fileHandler.openFileChooser();

    if (imgObj != null) {
      String imagePath = imgObj.getCurrentPath();
      if (imagePath != null) {
        photo.openPhoto(obj, imagePath);
      }

      obj.setImageValObj(imgObj);
      return obj;
    } else {
      System.out.println(this.getClass() + " openPicture 未打开图片");
      return null;
    }
  }
}