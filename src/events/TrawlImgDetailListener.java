package events;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.io.File;
import java.text.SimpleDateFormat;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import components.DialogComponent;
import entities.FrameValueObject;
import entities.ImageValueObject;

public class TrawlImgDetailListener {
  public void checkDetail(FrameValueObject fvo) {
    ImageValueObject ivo = fvo.getImageValObj();

    if (ivo != null) {
      String path = ivo.getCurrentPath();
      fileDetailsDialog(fvo.getFrame(), path, ivo.getCurrentOrder());

    } else {
      System.out.println(this + " ImageValueObject ivo is nothingness");
      DialogComponent comp = new DialogComponent();
      comp.getCustomDialog("OK", fvo.getFrame(), "Error", "No picture have been opened yet.");
    }
  }

  public void fileDetailsDialog(Frame parent, String filePath, int index) {
    System.out.println(this + " fileDetailsDialog=" + filePath);

    JDialog jd = new JDialog(parent, "File Details", true);
    jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    jd.setSize(600, 260);
    jd.setLocationRelativeTo(null); // 设置对话框居中显示

    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setFont(textArea.getFont().deriveFont(20f)); // 设置文本字体大小为 16

    JScrollPane scrollPane = new JScrollPane(textArea);
    jd.add(scrollPane, BorderLayout.CENTER);

    File file = new File(filePath);
    if (file.exists()) {

      StringBuilder sb = new StringBuilder();
      sb.append("Current Index: ").append(index).append("\n");
      sb.append("File Name: ").append(file.getName()).append("\n");
      sb.append("Absolute Path: ").append(file.getAbsolutePath()).append("\n");
      sb.append("Size: ").append(file.length() / 1024).append(" KiloBytes").append("\n");
      sb.append("Is Directory: ").append(file.isDirectory()).append("\n");
      sb.append("Is File: ").append(file.isFile()).append("\n");

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sb.append("Last Modified: ").append(dateFormat.format(file.lastModified())).append("\n");

      textArea.setText(sb.toString());
    } else {
      textArea.setText("File does not exist.");
    }
    jd.setVisible(true); // 显示对话框
  }
}