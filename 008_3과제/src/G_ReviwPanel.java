import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;

import javax.swing.SwingConstants;

public class G_ReviwPanel extends JPanel {
	private JLabel label;
	private JPanel panel;
	private JLabel label_1;

	/**
	 * Create the panel.
	 */
	public G_ReviwPanel(String txt, String txt2, double score) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setBackground(new Color(224, 237, 230));
		setSize(422, 146);
		setLayout(null);
		if(txt.length()>=14)
			txt = txt.substring(0,14)+"...";
		
		label = new JLabel(txt);
		label.setForeground(new Color(0, 128, 0));
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setBounds(12, 10, 217, 26);
		add(label);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(Color.black);
				var empty = "☆☆☆☆☆";
				var fill = "★★★★★";
				var fm =g2.getFontMetrics();
				g2.drawString(empty, 0, fm.getHeight()-2);
				int maxWidth =fm.stringWidth(fill);
				g2.clip(new Rectangle2D.Double(0, 0, (score/5)*maxWidth, getHeight()));
				g2.setColor(Color.orange);
				g2.drawString(fill, 0, fm.getHeight()-2);
				g2.dispose();
			}
		};
		panel.setBackground(new Color(224, 237, 230));
		panel.setBounds(274, 10, 136, 26);
		add(panel);
		
		txt2 = txt2.replaceAll("\"", "");
		if(txt2.length()>=55)
			txt2 = txt2.substring(0,55)+"...";
		label_1 = new JLabel("<html>"+txt2);
		label_1.setVerticalAlignment(SwingConstants.TOP);
		label_1.setBounds(12, 54, 378, 66);
		add(label_1);
	}

}
