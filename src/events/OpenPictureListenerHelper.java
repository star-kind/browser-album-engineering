package events;

import entities.*;
import services.*;

public class OpenPictureListenerHelper {
  public FrameValueObject openPicture(FrameValueObject obj) {
    FileChooserHandler fileHandler = new FileChooserHandler();
    OpenPhotoHandler photohHandler = new OpenPhotoHandler();

    ImageValueObject imgObj = fileHandler.getImgsDataModel();

    if (imgObj != null) {
      String imagePath = imgObj.getCurrentPath();
      if (imagePath != null) {
        obj.setImageValObj(imgObj);
        photohHandler.openPhoto(obj, imagePath);
      }

      return obj;
    } else {
      System.out.println(this.getClass() + " openPicture 未打开图片");
      return null;
    }
  }
}