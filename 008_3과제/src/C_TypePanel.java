import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;

public class C_TypePanel extends JPanel {
	private JLabel label;
	private JLabel label_1;
	private JSeparator separator;
	private JLabel label_2;

	/**
	 * Create the panel.
	 */
	public C_TypePanel(String name, String price, String[] values) {
		setBorder(new LineBorder(new Color(0, 128, 0), 3, true));
		setSize(236, 296);
		setLayout(null);
		
		label = new JLabel(name);
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setForeground(new Color(0, 128, 0));
		label.setBounds(12, 10, 212, 33);
		add(label);
		
		label_1 = new JLabel(price);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		label_1.setBounds(12, 53, 212, 27);
		add(label_1);
		
		separator = new JSeparator();
		separator.setBounds(12, 90, 212, 9);
		add(separator);
		
		label_2 = new JLabel(String.format("<html>✔️%s", String.join("<br>✔️", values)));
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setBounds(12, 109, 212, 177);
		add(label_2);
	}
}
