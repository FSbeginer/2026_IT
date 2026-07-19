import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class F_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	public F_패널(String name, int star, String review) {
		setSize(589, 128*2/3);
		setLayout(null);
		
		label = new JLabel(BF.getIcon("logo/user.png", 40, 40));
		label.setBounds(4, 14, 66, 57);
		add(label);
		
		label_1 = new JLabel(name);
		label_1.setBounds(88, 5, 122, 23);
		add(label_1);
		
		label_2 = new JLabel(String.format("평점 : %d", star));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(464, 5, 113, 23);
		add(label_2);
		
		label_3 = new JLabel("<html>"+review);
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_3.setBounds(88, 43, 489, 28);
		add(label_3);
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(new Color(192,192,192));
		g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
	}
}
