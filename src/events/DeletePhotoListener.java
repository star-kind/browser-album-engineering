package events;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.DeletePhotoHandler;
import services.OpenPhotoHandler;
import utils.DealFilePathStr;

import java.io.File;

public class DeletePhotoListener {
  public void deletePhoto(FrameValueObject framePOJO) {
    ImageValueObject imgJO = framePOJO.getImageValObj();
    if (imgJO == null) {
      System.out.println(this + " doesn't had any picture.");
      return;
    }

    DealFilePathStr deal = new DealFilePathStr();
    File[] fileArr = deal.strArr2FileArr(imgJO.getCurrentPathsArray());
    DeletePhotoHandler dele = new DeletePhotoHandler();

    imgJO = dele.moveImageToDirectory(fileArr, imgJO.getCurrentOrder(), framePOJO.getImageLabel());
    if (imgJO == null) {
      System.out.println(this + " Has not any picture");
      return;
    }

    framePOJO.setImageValObj(imgJO);
    OpenPhotoHandler handler = new OpenPhotoHandler();
    handler.openPhoto(framePOJO, imgJO.getCurrentPath());
  }

}