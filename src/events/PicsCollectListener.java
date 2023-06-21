package events;

import components.DialogComponent;
import constants.Constants;
import entities.FrameValueObject;
import entities.ImageValueObject;
import services.PicsListHandler;

public class PicsCollectListener {
  public void coreListener(FrameValueObject obj) {
    PicsListHandler p = new PicsListHandler();
    String[] array = null;
    ImageValueObject imgJO = obj.getImageValObj();

    if (imgJO != null) {
      array = imgJO.getCurrentPathsArray();
      p.showImageList(array, obj);
    } else {
      System.out.println(this.getClass() + " coreListener 未打开任何1张图片");
      warnByJudge(array, obj);
    }
  }

  public void warnByJudge(String[] imagesUrlArray, FrameValueObject obj) {
    if (imagesUrlArray == null || imagesUrlArray.length == 0) {
      String btnString = "Confirm";
      DialogComponent dialog = new DialogComponent();

      dialog.getCustomDialog(btnString, null, Constants.warn_dialog_title, Constants.warn_dialog_msg);
      return;
    }
  }
}