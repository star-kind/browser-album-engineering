package entities;

public class ScaleFactorObj {
  /**
   * 放大
   */
  public Double inScaleFactor;

  /**
   * 缩小
   */
  public Double outScaleFactor;

  public Double getInScaleFactor() {
    return this.inScaleFactor;
  }

  public void setInScaleFactor(Double inScaleFactor) {
    this.inScaleFactor = inScaleFactor;
  }

  public Double getOutScaleFactor() {
    return this.outScaleFactor;
  }

  public void setOutScaleFactor(Double outScaleFactor) {
    this.outScaleFactor = outScaleFactor;
  }

  @Override
  public String toString() {
    return "{" +
        " inScaleFactor='" + getInScaleFactor() + "'" +
        ", outScaleFactor='" + getOutScaleFactor() + "'" +
        "}";
  }

}