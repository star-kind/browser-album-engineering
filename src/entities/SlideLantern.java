package entities;

public class SlideLantern {
  public volatile Boolean switchSlide;
  public Integer delay;

  public Integer getDelay() {
    return this.delay;
  }

  public void setDelay(Integer delay) {
    this.delay = delay;
  }

  public Boolean isSwitchSlide() {
    return this.switchSlide;
  }

  public Boolean getSwitchSlide() {
    return this.switchSlide;
  }

  public void setSwitchSlide(Boolean switchSlide) {
    this.switchSlide = switchSlide;
  }

  @Override
  public String toString() {
    return "{" +
        " switchSlide='" + isSwitchSlide() + "'" +
        ", delay='" + getDelay() + "'" +
        "}";
  }

}