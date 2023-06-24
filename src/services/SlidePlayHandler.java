package services;

import java.io.File;
import java.util.concurrent.atomic.AtomicBoolean;

import constants.Constants;
import entities.FrameValueObject;

public class SlidePlayHandler {
  public volatile AtomicBoolean slideFlag;
  public Thread slideshowThread;

  public SlidePlayHandler() {
    slideFlag = new AtomicBoolean(Constants.stop_slide_play);
  }

  public void startSlideShow(FrameValueObject obj, File[] imageFilesArr, OpenPhotoHandler photo, boolean checkSign) {
    slideFlag.set(checkSign);

    // 判断是否有图片可供播放
    if (imageFilesArr != null && imageFilesArr.length > 0) {
      // 创建并启动一个后台线程来执行幻灯片播放
      slideshowThread = new Thread(new Runnable() {
        @Override
        public void run() {
          int interval = obj.getSlideLantern().getDelay();
          try {

            while (slideFlag.get() == true) { // 检查标志位是否为true
              showNextImage(obj, imageFilesArr, photo);// 显示下一张图片
              Thread.sleep(interval); // 暂停一段时间
            }
          } catch (InterruptedException e) {
            System.out.println(this + " interrupte thread");// 线程被异常中断，退出幻灯片播放
          }
        }
      });
      slideshowThread.start();
    }
  }

  public void stopSlideShow() {
    slideFlag.set(Constants.stop_slide_play);
    if (slideshowThread != null) {
      System.out.println(this + " stopSlideShow.");
      slideshowThread.interrupt(); // 中断线程以退出幻灯片播放
    }
  }

  public void showNextImage(FrameValueObject obj, File[] imageFilesArr, OpenPhotoHandler photo) {
    System.out.println(this + " showNextImage Origin slideFlag=" + slideFlag.get());

    int currentIndex = obj.getImageValObj().getCurrentOrder();

    if (imageFilesArr != null && imageFilesArr.length > 0) {
      int updateIndex = (currentIndex + 1) % imageFilesArr.length;
      System.out.println(this + " showNextImage updateIndex=" + currentIndex);
      obj.getImageValObj().setCurrentOrder(updateIndex);

      if (slideFlag.get() == Constants.start_slide_play) {
        String path = imageFilesArr[currentIndex].getAbsolutePath();
        photo.openPhoto(obj, path);
      }
    }
  }
}