import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingConstants;

public class G_패널 extends JPanel {
	public JLabel label;
	public JPanel panel;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	public G_패널(String title, double score, String txt) {
		setBackground(new Color(243, 252, 245));
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setSize(355, 141);
		setLayout(null);
		if(title.length()>=14)
			title = title.substring(0,14)+"...";
		label = new JLabel(title);
		label.setFont(new Font("굴림", Font.BOLD, 14));
		label.setForeground(new Color(0, 128, 0));
		label.setBounds(12, 10, 176, 27);
		add(label);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("Dialog", 0, 20));
				var fm = g2.getFontMetrics();
				String txt = "☆☆☆☆☆";
				String txt2 = "★★★★★";
				int mw = fm.stringWidth(txt2);
				g2.drawString(txt, 0, 20);
				g2.clip(new Rectangle2D.Double(0,0,(score/5)*mw, 1000));
				g2.setColor(Color.ORANGE);
				g2.drawString(txt2, 0, 20);
				g2.dispose();
			}
		};
		panel.setBackground(new Color(243, 252, 245));
		panel.setBounds(224, 10, 119, 27);
		add(panel);
		if(txt.length()>=55)
			txt= txt.substring(0,55)+"...";
		label_1 = new JLabel("<html>"+txt);
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(12, 47, 331, 64);
		add(label_1);
	}

}
