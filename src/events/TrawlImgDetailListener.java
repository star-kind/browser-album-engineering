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
    JDialog jd = createDialog(parent);
    JTextArea textArea = createTextArea();
    JScrollPane scrollPane = createScrollPane(textArea);
    jd.add(scrollPane, BorderLayout.CENTER);

    displayFileDetails(textArea, filePath, index);

    jd.setVisible(true);
  }

  public JDialog createDialog(Frame parent) {
    JDialog jd = new JDialog(parent, "File Details", true);
    jd.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    jd.setSize(600, 400);
    jd.setLocationRelativeTo(null);
    return jd;
  }

  public JTextArea createTextArea() {
    JTextArea textArea = new JTextArea();
    textArea.setEditable(false);
    textArea.setFont(textArea.getFont().deriveFont(20f));
    textArea.setLineWrap(true); // 设置自动换行
    textArea.setWrapStyleWord(true); // 设置按词边界换行
    return textArea;
  }

  public JScrollPane createScrollPane(JTextArea textArea) {
    JScrollPane scrollPane = new JScrollPane(textArea);
    return scrollPane;
  }

  public void displayFileDetails(JTextArea textArea, String filePath, int index) {
    File file = new File(filePath);
    if (file.exists()) {
      StringBuilder sb = new StringBuilder();
      sb.append("Current Index: ").append(index).append("\n\n");
      sb.append("File Name: ").append(file.getName()).append("\n\n");
      sb.append("Absolute Path: ").append(file.getAbsolutePath()).append("\n\n");
      sb.append("Size: ").append(file.length() / 1024).append(" KiloBytes").append("\n\n");
      sb.append("Is Directory: ").append(file.isDirectory()).append("\n\n");
      sb.append("Is File: ").append(file.isFile()).append("\n\n");

      SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
      sb.append("Last Modified: ").append(dateFormat.format(file.lastModified())).append("\n");

      textArea.setText(sb.toString());
    } else {
      textArea.setText("File does not exist.");
    }
  }

}