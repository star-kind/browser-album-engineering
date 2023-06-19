import components.*;
import javax.swing.SwingUtilities;

public class MainApp {
  public static void main(String[] args) {
    PreponderantFrame mainFrame = new PreponderantFrame();
    MenuBarComponent menuBar = new MenuBarComponent();
    SwingUtilities.invokeLater(() -> mainFrame.kernel(menuBar));
  }
}
