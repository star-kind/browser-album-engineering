package components;

import javax.swing.*;
import java.awt.*;

public class DialogComponent {
  public JDialog getCustomDialog(String btnStr, Component parentComponent,
      String title, String message, int width,
      int height) {

    if (parentComponent == null) {
      System.out.println(this + " Has Not dialog");
      return null;
    }

    JDialog dialog = new JDialog();
    dialog.setTitle(title);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setModal(true);
    dialog.setLocationRelativeTo(parentComponent);

    showCustomDialog(dialog, btnStr, message, width, height);
    return dialog;
  }

  public void showCustomDialog(JDialog dialog, String btnStr, String message, int width, int height) {
    JLabel label = new JLabel(message);
    label.setFont(new Font("Arial", Font.PLAIN, 20));

    JButton button = new JButton(btnStr);
    button.addActionListener(e -> dialog.dispose());
    button.setBorderPainted(false);
    button.setPreferredSize(new Dimension(120, 40));
    button.setFont(new Font("Arial", Font.BOLD, 20));

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(button);

    dialog.setLayout(new FlowLayout());
    dialog.add(label);
    dialog.add(buttonPanel);
    dialog.pack();
    dialog.setSize(width, height);
    dialog.setVisible(true);
    dialog.requestFocus();
  }

  public JDialog createCustomDialog(Component parentComponent, String title) {
    if (parentComponent == null) {
      System.out.println(this + " parentComponent is null. Cannot create custom dialog.");
      return null;
    }

    if (parentComponent.isShowing()) {
      JDialog dialog = new JDialog((Frame) SwingUtilities.getWindowAncestor(parentComponent), title, true);
      System.out.println(this + " Custom dialog created successfully.");
      return dialog;
    } else {
      System.out.println(this + " parentComponent is not showing. Cannot create custom dialog.");
      return null;
    }
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
    dialog.setModalityType(Dialog.ModalityType.MODELESS); // 设置为非模态对话框
    dialog.setLocationRelativeTo(basePanel);
    dialog.setContentPane(panel);
    dialog.setVisible(true);
    return dialog;
  }
}