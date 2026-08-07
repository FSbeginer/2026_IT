import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;

public class ReviewPanel extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	public ReviewPanel(String name, String review, double s) {
		setSize(550, 54);
		setLayout(null);
		
		label = new JLabel(BF.getIcon("logo/user.png",30,30));
		label.setBounds(0, 10, 37, 34);
		add(label);
		
		label_1 = new JLabel(name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setBounds(49, 0, 139, 24);
		add(label_1);
		
		label_2 = new JLabel("<html>"+review);
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_2.setBounds(47, 26, 341, 24);
		add(label_2);
		
		label_3 = new JLabel("평점 : "+String.format("%.1f", s));
		label_3.setBounds(480, 8, 57, 15);
		add(label_3);
		
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.lightGray);
		g2.draw(new RoundRectangle2D.Double(0,0,getWidth()-1,getHeight()-1,15,15));
		g2.dispose();
	}
}
