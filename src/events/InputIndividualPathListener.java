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
import services.InputPathHandler;

public class InputIndividualPathListener {
  public void showInputUrlDialog(FrameValueObject frameJO) {
    JTextField textField = new JTextField(20);
    JButton openButton = new JButton("Open");
    InputPathHandler inputHandler = new InputPathHandler();

    openButton.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        String isolateUrl = textField.getText().trim();

        if (isolateUrl.isBlank()) {
          System.out.println(this + " Please input path");
          popUpError(inputHandler, frameJO.getFrame(), "Please input path", "Error");
          return;
        }

        File isolateImg = new File(isolateUrl);

        if (!(isolateImg.exists())) {
          System.out.println(this + " Picture absent");
          popUpError(inputHandler, frameJO.getFrame(), "Picture absent", "Error");
          return;
        }

        System.out.println(this + " isolateUrl= " + isolateUrl);
        OpenPictureListenerHelper open = new OpenPictureListenerHelper();
        assortOpen(frameJO, isolateUrl, openButton, open);
      }
    });

    textField.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        openButton.doClick();
      }
    });

    inputHandler.showOptPane(frameJO, openButton, textField);
  }

  public void assortOpen(FrameValueObject frameJO, String isolateUrl, JButton openButton,
      OpenPictureListenerHelper open) {
    open.openImageByUrlStr(isolateUrl, frameJO);

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