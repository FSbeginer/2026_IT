import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.MatteBorder;
import javax.swing.SwingConstants;

public class 요금제패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;

	/**
	 * Create the panel.
	 */
	public 요금제패널(String txt, int price, String[] effect) {
		setBorder(new LineBorder(new Color(0, 128, 0), 2, true));
		setSize(251, 298);
		setPreferredSize(getSize());
		setLayout(null);
		
		label = new JLabel(txt);
		label.setFont(new Font("굴림", Font.BOLD, 13));
		label.setForeground(new Color(0, 128, 0));
		label.setBounds(23, 22, 186, 33);
		add(label);
		
		label_1 = new JLabel(String.format("%,d원 / 월", price));
		label_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(12, 57, 227, 27);
		add(label_1);
		
		label_2 = new JLabel(String.format("<html>"+"✔️ %s<br>".repeat(effect.length), effect));
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setBounds(12, 94, 227, 176);
		add(label_2);
	}

}
