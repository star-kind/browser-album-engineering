import components.*;
import javax.swing.SwingUtilities;

public class MainApp {
  public static void main(String[] args) {
    PreponderantFrame mainFrame = new PreponderantFrame();

    if (args.length > 0) {
      mainFrame.file_url_argument = args[0];
    }

    SwingUtilities.invokeLater(() -> mainFrame.kernel());
  }
}
