package components;

import javax.swing.*;
import java.awt.*;

public class DialogComponent {
  /**
   * 
   * @param btnStr
   * @param parentComponent
   * @param title
   * @param message
   * @param width
   * @param height
   * @return
   */
  public JDialog getCustomDialog(String btnStr, Component parentComponent, String title, String message, int width,
      int height) {
    JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), title, true);
    // TODO try catch

    JLabel label = new JLabel(message);

    JButton button = new JButton(btnStr);
    button.addActionListener(e -> dialog.dispose());
    button.setBorderPainted(false);

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(button);

    dialog.setLayout(new FlowLayout());
    dialog.add(label);
    dialog.add(buttonPanel);
    dialog.pack();
    dialog.setLocationRelativeTo(parentComponent);
    dialog.setSize(width, height);
    dialog.setVisible(true);
    dialog.requestFocus();
    return dialog;
  }

  /**
   * 无按钮的面板,只显示信息
   * 
   * @param panel
   * @param title
   * @param basePanel
   * @return
   */
  public JDialog getShowInfoDialog(JPanel panel, String title, JPanel basePanel) {
    JDialog dialog = new JDialog();
    dialog.setTitle(title);
    dialog.setSize(400, 400);
    // dialog.setModalityType(Dialog.ModalityType.APPLICATION_MODAL);
    dialog.setModalityType(Dialog.ModalityType.MODELESS); // TODO 设置为非模态对话框
    dialog.setLocationRelativeTo(basePanel);
    dialog.setContentPane(panel);
    dialog.setVisible(true);
    return dialog;
  }
}