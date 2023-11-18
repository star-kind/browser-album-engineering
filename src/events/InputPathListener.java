package events;

import java.awt.Window;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entities.FrameValueObject;
import entities.ImageValueObject;
import services.InputPathHandler;
import services.OpenPhotoHandler;

public class InputPathListener {
  public void showInputPathDialog(FrameValueObject frameJO) {
    JTextField directoryField = new JTextField(20);
    JButton openButton = new JButton("Find & Open");
    InputPathHandler inputHandler = new InputPathHandler();
    OpenPhotoHandler photoHandler = new OpenPhotoHandler();

    openButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String directoryPath = directoryField.getText().trim();

        if (directoryPath.isEmpty()) {
          popUpError(inputHandler, frameJO.getFrame(), "Please enter a directory path", "Error");
          return;
        }

        File directory = new File(directoryPath);

        if (!(directory.exists()) && !(directory.isDirectory())) {
          popUpError(inputHandler, frameJO.getFrame(), "Invalid directory path", "Error");
          return;
        }

        ImageValueObject vo = inputHandler.loadImgsFromDir(directory);

        if (vo == null) {
          System.out.println(this + " No any pictures");
          popUpError(inputHandler, frameJO.getFrame(), "No picture at this directory", "Error");
          return;
        }

        exectuteOpen(frameJO, vo, openButton, photoHandler);
      }
    });

    directoryField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openButton.doClick();
      }
    });

    inputHandler.showOptPane(frameJO, openButton, directoryField);
  }

  public void exectuteOpen(FrameValueObject frameJO, ImageValueObject vo, JButton openButton,
      OpenPhotoHandler photoHandler) {

    frameJO.setImageValObj(vo);
    photoHandler.openPhoto(frameJO, vo.getCurrentPath());

    Window window = SwingUtilities.getWindowAncestor(openButton);
    if (window instanceof JDialog) {
      JDialog dialog = (JDialog) window;
      dialog.dispose();// 关闭对话框
    }
  }

  public void popUpError(InputPathHandler inputHandler, JFrame frame, String errStr, String title) {
    inputHandler.showErrorDialog(errStr, frame, title);
  }

}