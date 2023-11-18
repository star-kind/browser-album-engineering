package entities;

public class ImageValueObject {
  public Integer currentOrder;
  public String currentPath;
  public String[] currentPathsArray;

  public Integer getCurrentOrder() {
    return this.currentOrder;
  }

  public void setCurrentOrder(Integer currentOrder) {
    this.currentOrder = currentOrder;
  }

  public String getCurrentPath() {
    return this.currentPath;
  }

  public void setCurrentPath(String currentPath) {
    this.currentPath = currentPath;
  }

  public String[] getCurrentPathsArray() {
    return this.currentPathsArray;
  }

  public void setCurrentPathsArray(String[] currentPathsArray) {
    this.currentPathsArray = currentPathsArray;
  }

  public void arrayShow() {
    System.out.println(this.getClass());
    for (String string : currentPathsArray) {
      System.out.print(string + "\n");
    }
  }

  @Override
  public String toString() {
    // arrayShow();
    return "{" +
        " currentOrder='" + getCurrentOrder() + "'" +
        ", currentPath='" + getCurrentPath() + "'" +
        ", currentPathsArray length='" + getCurrentPathsArray().length + "'" +
        "}";
  }

}
