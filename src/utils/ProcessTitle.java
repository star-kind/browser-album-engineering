package utils;

import java.io.File;

public class ProcessTitle {
  public String[] getSplitsArr(String str) {
    String arr[] = str.split(File.separator);
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