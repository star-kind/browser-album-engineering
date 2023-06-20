package services;

import java.io.File;

import entities.FrameValueObject;

public class SlidePlayHandler {
  public void startSlideShow(FrameValueObject obj, File[] imageFilesArr, OpenPhotoHandler photo) {
    boolean switchSlide = obj.getSlideLantern().getSwitchSlide();
    judgeSwitchSlide(switchSlide);

    // 判断是否有图片可供播放
    if (imageFilesArr != null && imageFilesArr.length > 0) {
      // 创建并启动一个后台线程来执行幻灯片播放
      Thread slideshowThread = new Thread(new Runnable() {
        @Override
        public void run() {
          int interval = obj.getSlideLantern().getDelay();
          try {
            while (switchSlide == true) { // 检查标志位是否为true
              showNextImage(obj, imageFilesArr, photo);// 显示下一张图片
              Thread.sleep(interval); // 暂停一段时间

            }
          } catch (InterruptedException e) {
            System.out.println("interrupte_thread");// 线程被异常中断，退出幻灯片播放
          }
        }
      });
      slideshowThread.start();
    }
  }

  // 判断是否决定播放幻灯片
  public void judgeSwitchSlide(boolean switchSlide) {
    System.out.println(this.getClass() + " judgeSwitchSlide: " + switchSlide);
    if (switchSlide == false) {
      return; // 直接中止
    }
  }

  public void showNextImage(FrameValueObject obj, File[] imageFilesArr, OpenPhotoHandler photo) {
    int currentIndex = obj.getImageValObj().getCurrentOrder();
    System.out.println(this + " showNextImage-currentIndex=" + currentIndex);

    if (imageFilesArr != null && imageFilesArr.length > 0) {
      currentIndex = (currentIndex + 1) % imageFilesArr.length;
      String path = imageFilesArr[currentIndex].getAbsolutePath();
      photo.openPhoto(obj, path);// TODO showNextImage
    }
  }
}