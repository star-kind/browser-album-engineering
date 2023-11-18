package utils;

public class ProcessTitle {
  public String[] getSplitsArr(String str) {
    String arr[] = str.split("\\\\");
    return arr;
  }

  public String getTitleFromArr(String arr[]) {
    int n = arr.length - 1;
    return arr[n];
  }

  public String getSingleTitle(String str) {
    String arr[] = getSplitsArr(str);
    String res = getTitleFromArr(arr);
    return res;
  }
}