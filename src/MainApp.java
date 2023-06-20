import components.*;
import javax.swing.SwingUtilities;

public class MainApp {
  public static void main(String[] args) {
    PreponderantFrame mainFrame = new PreponderantFrame();
    SwingUtilities.invokeLater(() -> mainFrame.kernel());
  }
}
