package events;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Window;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import entities.FrameValueObject;

public class RevampDelayTimeListener {
  public JTextField textField;
  public int timeVal;

  public void revampTime(FrameValueObject frameObj) {
    openPopup(frameObj.getFrame());
    System.out.println(this + " revampTime timeVal=" + timeVal);
    frameObj.getSlideLantern().setDelay(timeVal * 1000);
  }

  public void openPopup(JFrame parentFrame) {
    JTextField textField = new JTextField();
    JDialog dialog = createDialog(parentFrame);
    JPanel contentPanel = createContentPanel(dialog);

    addComponentsToPanel(contentPanel, textField);
    setTextFieldSize(textField, 200, 30); // 设置输入框的宽度为200，高度为30
    increaseFontSize(contentPanel, 18); // 设置字体大小

    addEnterKeyListener(textField, contentPanel); // 添加回车键监听器
    showDialog(dialog);
  }

  public JDialog createDialog(JFrame parentFrame) {
    JDialog dialog = new JDialog(parentFrame, "Popup Dialog", true);
    dialog.setPreferredSize(new Dimension(350, 200));
    dialog.setLocationRelativeTo(parentFrame);
    return dialog;
  }

  public JPanel createContentPanel(JDialog dialog) {
    JPanel contentPanel = new JPanel();
    contentPanel.setLayout(new BorderLayout(10, 10)); // 使用BorderLayout布局管理器
    dialog.add(contentPanel);
    return contentPanel;
  }

  public void setTextFieldSize(JTextField textField, int width, int height) {
    Dimension size = new Dimension(width, height);
    textField.setPreferredSize(size);
  }

  public void increaseFontSize(Container container, int fontSize) {
    for (Component component : container.getComponents()) {
      if (component instanceof Container) {
        increaseFontSize((Container) component, fontSize);
      }
      if (component instanceof JComponent) {
        Font currentFont = component.getFont();
        Font newFont = currentFont.deriveFont(Font.PLAIN, fontSize);
        component.setFont(newFont);
      }
    }
  }

  public void addEnterKeyListener(JTextField textField, JPanel contentPanel) {
    textField.addActionListener(e -> handleConfirmButton(textField, contentPanel));
  }

  public void addComponentsToPanel(JPanel contentPanel, JTextField textField) {
    JPanel inputPanel = createInputPanel(textField);
    JButton confirmButton = createConfirmButton(textField, contentPanel);

    contentPanel.add(inputPanel, BorderLayout.CENTER);
    contentPanel.add(confirmButton, BorderLayout.SOUTH);
  }

  public JPanel createInputPanel(JTextField textField) {
    JPanel inputPanel = new JPanel();
    inputPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
    JLabel label = new JLabel("Enter a new slide play time(second):");
    inputPanel.add(label);
    inputPanel.add(textField);
    return inputPanel;
  }

  public JButton createConfirmButton(JTextField textField, JPanel contentPanel) {
    JButton confirmButton = new JButton("Confirm");
    confirmButton.addActionListener(e -> handleConfirmButton(textField, contentPanel));
    return confirmButton;
  }

  public void handleConfirmButton(JTextField textField, JPanel contentPanel) {
    String input = textField.getText().trim();
    if (input.isEmpty()) {
      showWarningDialog("Input cannot be empty!", "Warning");
    } else {
      try {
        int number = Integer.parseInt(input);
        if (number <= 0) {
          showWarningDialog("Invalid number.(must be greater than 0).", "Warning");
        } else {
          sendDataToBackend(number);
          closeDialog(contentPanel);
        }
      } catch (NumberFormatException ex) {
        showWarningDialog("Invalid input! Please enter a number.", "Warning");
      }
    }
  }

  public void showWarningDialog(String message, String title) {
    JOptionPane.showMessageDialog(null, message, title, JOptionPane.WARNING_MESSAGE);
  }

  public void closeDialog(JPanel contentPanel) {
    Window window = SwingUtilities.getWindowAncestor(contentPanel);
    if (window instanceof JDialog) {
      JDialog dialog = (JDialog) window;
      dialog.dispose();
    }
  }

  public void showDialog(JDialog dialog) {
    dialog.pack();
    dialog.setVisible(true);
  }

  public void sendDataToBackend(int number) {
    System.out.println(this + " Sending data to backend: " + number);
    timeVal = number;
  }
}