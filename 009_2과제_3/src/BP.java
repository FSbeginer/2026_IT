import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.awt.Color;

public class BP extends JPanel {

	/**
	 * Create the panel.
	 */
	public BP() {
		setBackground(new Color(240, 240, 240));
		setSize(957, 501);
	}

	public static ImageIcon getIcon(String path) {
		return new ImageIcon("./datafiles/" + path);
	}

	public static ImageIcon getIcon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon("./datafiles/" + path).getImage().getScaledInstance(w, h, 4));
	}

	public static void msgInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", 1);
	}

	public static void msgErr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", 0);
	}
}
