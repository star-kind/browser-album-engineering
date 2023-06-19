package utils;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class DealFilePathStr {
  public String[] getImgPathsArray(File selectedFile) {
    String parentPath = selectedFile.getParent();
    File parentDirectory = new File(parentPath);
    File[] filesArr = parentDirectory.listFiles();

    File[] imgFileArr = orderByName(filesArr);
    String[] imgStrArr = filesArr2StrArr(imgFileArr);
    return imgStrArr;
  }

  public String[] filesArr2StrArr(File[] filesArr) {
    List<String> imgList = new ArrayList<>();

    for (File f : filesArr) {
      if (f.isFile() && isImageFile(f.getName())) {
        imgList.add(f.getAbsolutePath());
      }
    }
    return imgList.toArray(new String[0]);
  }

  /**
   * 按名称对 File[] 排序
   * 
   * @param filesArr
   * @return File[]
   */
  public File[] orderByName(File[] filesArr) {
    List<File> fileList = Arrays.asList(filesArr);

    Collections.sort(fileList, new Comparator<File>() {
      @Override
      public int compare(File o1, File o2) {
        if (o1.isDirectory() && o2.isFile())
          return -1;
        if (o1.isFile() && o2.isDirectory())
          return 1;
        return o1.getName().compareTo(o2.getName());
      }
    });
    return filesArr;
  }

  public boolean isImageFile(String fileName) {
    String extension = getFileExtension(fileName);
    return extension != null
        && (extension.equals("jpg") || extension.equals("jpeg") || extension.equals("png") || extension.equals("gif"));
  }

  public String getFileExtension(String fileName) {
    int dotIndex = fileName.lastIndexOf(".");
    if (dotIndex > 0 && dotIndex < fileName.length() - 1) {
      return fileName.substring(dotIndex + 1).toLowerCase();
    }
    return null;
  }

  public File[] strArr2FileArr(String[] strArr) {
    File[] filesArr = new File[strArr.length];
    for (int i = 0; i < strArr.length; i++) {
      filesArr[i] = new File(strArr[i]);
    }
    return filesArr;
  }
}