package components;

import javax.swing.*;
import entities.FrameValueObject;
import java.awt.*;

public class DialogComponent {
  public JDialog getWarnDialog(Component parentComponent, String title, String message) {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), title, true);
    JLabel label = new JLabel(message);

    JButton button = new JButton("OK");
    button.addActionListener(e -> dialog.dispose());
    button.setBorderPainted(false);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(button);

    dialog.setLayout(new FlowLayout());
    dialog.add(label);
    dialog.add(buttonPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(parentComponent);
    dialog.setSize(200, 100);
    dialog.setVisible(true);
    dialog.requestFocus();
    return dialog;
  }

  public JDialog getShowingDialog(JPanel listPanel, FrameValueObject obj, String title) {
    JDialog dialog = new JDialog();
    dialog.setTitle(title);
    dialog.setSize(400, 400);
    dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setLocationRelativeTo(obj.getBasePanel());
    dialog.setContentPane(listPanel);
    dialog.setVisible(true);
    return dialog;
  }
}