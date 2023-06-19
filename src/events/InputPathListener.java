package events;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.InputPathHandler;
import services.PhotoManifestHandler;

public class InputPathListener {
  public void showInputPathDialog(FrameValueObject frameJO) {
    JTextField directoryField = new JTextField(20);
    JButton openButton = new JButton("Find and Open");
    InputPathHandler inputhandler = new InputPathHandler();
    PhotoManifestHandler photoHandler = new PhotoManifestHandler();

    openButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String directoryPath = directoryField.getText().trim();
        if (!directoryPath.isEmpty()) {
          File directory = new File(directoryPath);
          if (directory.exists() && directory.isDirectory()) {
            ImageValueObject vo = inputhandler.loadImgsFromDir(directory);
            frameJO.setImageValObj(vo);
            photoHandler.openPhoto(frameJO, vo.getCurrentPath());
            // 关闭对话框
            Window window = SwingUtilities.getWindowAncestor(openButton);
            if (window instanceof JDialog) {
              JDialog dialog = (JDialog) window;
              dialog.dispose();
            }
          } else {
            String errStr = "Invalid directory path.";
            inputhandler.showErrorDialog(errStr, frameJO.getFrame(), "Error 1");
          }
        } else {
          String errStr = "Please enter a directory path.";
          inputhandler.showErrorDialog(errStr, frameJO.getFrame(), "Error 2");
        }
      }
    });

    directoryField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openButton.doClick();
      }
    });

    inputhandler.showOptPane(frameJO, openButton, directoryField);
  }

}