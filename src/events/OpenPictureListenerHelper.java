package events;

import entities.*;
import services.*;

public class OpenPictureListenerHelper {
  public FrameValueObject openPicture(FrameValueObject obj) {
    FileChooserHandler fileHandler = new FileChooserHandler();
    OpenPhotoHandler photohHandler = new OpenPhotoHandler();

    if (obj.getImageValObj() != null) {
      return argumentMode(obj, fileHandler, photohHandler);
    }
    return routineMode(obj, fileHandler, photohHandler);
  }

  public FrameValueObject argumentMode(FrameValueObject obj, FileChooserHandler fileHandler,
      OpenPhotoHandler photohHandler) {
    ImageValueObject imgObj = fileHandler.getImgByFilePath(obj.getImageValObj().getCurrentPath());
    return openExecute(obj, imgObj, photohHandler);
  }

  public FrameValueObject routineMode(FrameValueObject obj, FileChooserHandler fileHandler,
      OpenPhotoHandler photohHandler) {
    ImageValueObject imgObj = fileHandler.getImgsDataModel();
    return openExecute(obj, imgObj, photohHandler);
  }

  public FrameValueObject openExecute(FrameValueObject obj, ImageValueObject imgObj, OpenPhotoHandler photohHandler) {
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

  public FrameValueObject openImageByUrlStr(String fileUrlArgument, FrameValueObject frameData) {
    if ((fileUrlArgument.isBlank()) == false) {// 若不为空白
      ImageValueObject imageVal = getImageValues(fileUrlArgument);
      frameData.setImageValObj(imageVal);
      openPicture(frameData);
      return frameData;
    }
    return null;
  }

  public ImageValueObject getImageValues(String fileUrlStr) {
    ImageValueObject imageVal = new ImageValueObject();
    imageVal.setCurrentPath(fileUrlStr);
    imageVal.setCurrentOrder(0);
    return imageVal;
  }
}