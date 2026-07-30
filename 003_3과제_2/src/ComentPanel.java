import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.Color;

public class ComentPanel extends JPanel {
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	public ComentPanel(Profile profile, String txt, String txt2) {
		setSize(500, 50);
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 50, 50);
		panel.add(profile);
		add(panel);
		panel.setLayout(null);
		
		label = new JLabel(txt);
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		label.setBounds(54, 4, 443, 21);
		add(label);
		
		label_1 = new JLabel(txt2);
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		label_1.setBounds(52, 26, 445, 19);
		add(label_1);
	}

}
