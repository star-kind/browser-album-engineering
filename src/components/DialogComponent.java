package components;

import javax.swing.*;
import java.awt.*;

public class DialogComponent {
  public JDialog getCustomDialog(String btnStr, Component parentComponent,
      String title, String message) {

    if (parentComponent == null) {
      System.out.println(this + " Has Not dialog");
      return null;
    }

    JDialog dialog = new JDialog();
    dialog.setTitle(title);
    dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
    dialog.setModal(true);
    dialog.setLocationRelativeTo(parentComponent);

    dialog = productCustomDialog(dialog, btnStr, message);
    return dialog;
  }

  public JDialog productCustomDialog(JDialog dialog, String btnStr, String message) {
    JLabel label = new JLabel(message);
    label.setFont(new Font("Arial", Font.PLAIN, 20));

    JButton button = new JButton(btnStr);
    button.addActionListener(e -> dialog.dispose());
    button.setBorderPainted(false);
    button.setPreferredSize(new Dimension(120, 40));
    button.setFont(new Font("Arial", Font.BOLD, 20));

    JPanel buttonPanel = new JPanel(new FlowLayout());
    buttonPanel.add(button);

    dialog.setLayout(new BorderLayout()); // 使用边界布局管理器
    dialog.add(label, BorderLayout.CENTER); // 将标签放置在中央位置
    dialog.add(buttonPanel, BorderLayout.SOUTH); // 将按钮面板放置在底部位置
    dialog.pack();// 根据组件的尺寸自动调整
    dialog.setVisible(true);
    dialog.requestFocus();
    return dialog;
  }

  public void displayContentDialog(String content, String title, JPanel backgroundPanel, int width, int height) {
    JPanel contentPanel = new JPanel(new BorderLayout());

    // 创建一个 JLabel 并将其文本居中对齐
    JLabel label = new JLabel(content);
    label.setHorizontalAlignment(SwingConstants.CENTER);
    contentPanel.add(label, BorderLayout.CENTER);

    getShowInfoDialog(contentPanel, title, backgroundPanel, width, height); // 调用getShowInfoDialog
  }

  // 无按钮的面板,只显示信息
  public JDialog getShowInfoDialog(JPanel contentPanel, String title, JPanel backgroundPanel, int width, int height) {
    JDialog dialog = new JDialog();
    dialog.setTitle(title);
    dialog.setSize(width, height);
    dialog.setModalityType(Dialog.ModalityType.MODELESS); // 设置为非模态对话框
    dialog.setLocationRelativeTo(backgroundPanel);
    dialog.setContentPane(contentPanel);
    dialog.setVisible(true);
    return dialog;
  }
}