package services;

import java.awt.*;

import constants.Constants;
import entities.*;

public class FunctionalityClass {
  public FrameValueObject frameVO;

  public FunctionalityClass(FrameValueObject frameValObj) {
    this.frameVO = frameValObj;
    addMenuBarToFrame();
    setBasePanelSize();
    displayFrame();
    initializationSlideLantern();
  }

  public void addMenuBarToFrame() {
    this.frameVO.frame.setJMenuBar(this.frameVO.menuBarValueObject.menuBar);
  }

  public void setBasePanelSize() {
    GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
    GraphicsDevice screenDevice = env.getDefaultScreenDevice();
    Rectangle screenBounds = screenDevice.getDefaultConfiguration().getBounds();

    int basePanelHeight = screenBounds.height - this.frameVO.menuBarValueObject.menuBar.getHeight();
    this.frameVO.basePanel.setPreferredSize(new Dimension(screenBounds.width, basePanelHeight));
  }

  public void displayFrame() {
    this.frameVO.frame.getContentPane().add(this.frameVO.basePanel);
    this.frameVO.frame.pack();
    this.frameVO.frame.setVisible(true);
  }

  public void initializationSlideLantern() {
    SlideLantern slideLantern = new SlideLantern();
    slideLantern.setDelay(Constants.slide_play_delay);
    slideLantern.setSwitchSlide(Constants.stop_slide_play);
    this.frameVO.setSlideLantern(slideLantern);
  }

  public FrameValueObject getFrameVO() {
    return this.frameVO;
  }

  public void setFrameVO(FrameValueObject frameVO) {
    this.frameVO = frameVO;
  }

}
