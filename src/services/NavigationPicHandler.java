package services;

import constants.Constants;
import entities.FrameValueObject;

public class NavigationPicHandler {
  public FrameValueObject showByImageFlag(int navFlag, String[] imageFilesArr, int currentIndex,
      OpenPhotoHandler photo,
      FrameValueObject obj) {
    int index = calculateIndex(navFlag, imageFilesArr, currentIndex);

    if (imageFilesArr != null && imageFilesArr.length > 0) {
      String path = imageFilesArr[index];
      obj.getImageValObj().setCurrentOrder(index);

      FrameValueObject frameJO = photo.openPhoto(obj, path);
      System.out.println(this + " showByImageFlag index=" + index);
      System.out.println(this + " showByImageFlag path=" + path);

      return frameJO;
    }
    return null;
  }

  public int calculateIndex(int navFlag, String[] imageFiles, int currentIndex) {
    int index = currentIndex;
    if (navFlag == Constants.next_pic) {
      index = (currentIndex + 1) % imageFiles.length;
    } else if (navFlag == Constants.previous_pic) {
      index = (currentIndex - 1 + imageFiles.length) % imageFiles.length;
    }
    return index;
  }

}