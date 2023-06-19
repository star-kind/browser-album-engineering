package events;

import java.awt.BorderLayout;
import java.awt.Font;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.InputPathHandler;
import services.PhotoManifestHandler;

public class SelectDirectoryListener {
  public void listenerCenter(FrameValueObject frameVO) {
    String dirPath = pickUpDirectory(frameVO.getFrame());

    if (dirPath == null || ("".equals(dirPath))) {
      String errStr = " There are no pictures in this folder.";
      System.out.println(this + errStr);
      return;
    }
    System.out.println(this + " dirPath=" + dirPath);

    ImageValueObject imgVO = getImgVOByDirectory(dirPath, frameVO);
    if (imgVO == null) {
      System.out.println(this + " ImageValueObject had nothing");
      showRemindText(frameVO.getFrame());
      return;
    }

    frameVO.setImageValObj(imgVO);
    PhotoManifestHandler photoHandler = new PhotoManifestHandler();
    photoHandler.openPhoto(frameVO, imgVO.getCurrentPath());
  }

  public void showRemindText(JFrame frame) {
    String textString = "No pictures here";
    JLabel label = new JLabel(textString, JLabel.CENTER);
    label.setFont(label.getFont().deriveFont(Font.BOLD, 22)); // 设置字体尺寸更大
    label.setVerticalAlignment(JLabel.CENTER); // 让文本在标签中垂直居中

    JPanel panel = new JPanel(new BorderLayout());
    panel.add(label, BorderLayout.CENTER);

    frame.add(panel);
    frame.setVisible(true);
  }

  public ImageValueObject getImgVOByDirectory(String directoryPath, FrameValueObject frameVO) {
    File directory = new File(directoryPath);
    InputPathHandler pathHandler = new InputPathHandler();

    if (directory.exists() && directory.isDirectory()) {
      ImageValueObject imgVO = pathHandler.loadImgsFromDir(directory);
      return imgVO;
    } else {
      String errInfo = "Invalid directory path.";
      pathHandler.showErrorDialog(errInfo, frameVO.getFrame(), "Error Info");
      return null;
    }
  }

  public String pickUpDirectory(JFrame frame) {
    File file = null;
    JFileChooser fileChooser = new JFileChooser();
    fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
    int option = fileChooser.showOpenDialog(frame);

    if (option == JFileChooser.APPROVE_OPTION) {
      file = fileChooser.getSelectedFile();
      System.out.println(this + " 选择的目录AbsolutePath: " + file.getAbsolutePath());
      return file.getAbsolutePath();
    } else {
      System.out.println(this + " 打开命令已被取消");
      return null;
    }
  }

}