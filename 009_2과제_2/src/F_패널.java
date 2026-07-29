import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;

public class F_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;

	/**
	 * Create the panel.
	 */
	public F_패널(String name, String txt) {
		setBorder(new Roundborder(Color.LIGHT_GRAY));
		setSize( 577, 83);
		setLayout(null);
		
		label = new JLabel(BF.getIcon("logo/user.png",50,50));
		label.setBounds(8, 17, 57, 50);
		add(label);
		
		label_1 = new JLabel(name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setBounds(77, 10, 126, 23);
		add(label_1);
		
		label_2 = new JLabel(txt);
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_2.setBounds(77, 43, 457, 23);
		add(label_2);
	}

}
class Roundborder extends LineBorder{
	public Roundborder(Color c) {
		super(c);
	}
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(lineColor);
		g2.drawRoundRect(x, y, width-1, height-1, 15, 15);
		g2.dispose();
	}
}