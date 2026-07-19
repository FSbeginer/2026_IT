import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class Profile extends JPanel {

	boolean drawCircle = false;
	Color circleColor = new Color(255,80,80);
	Image img;

	public Profile(boolean drawCircle) {
		this.drawCircle = drawCircle;
	}

	public void setImage(Image img) {
		this.img = img;
		repaint();
	}
	public void setCircleColor(Color circleColor) {
		this.circleColor = circleColor;
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2f));
		if (drawCircle) {
			g2.setColor(circleColor);
			g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
		}
		g2.clip(new Ellipse2D.Double(3, 3, getWidth() - 6, getHeight() - 6));
		g2.drawImage(img, 2, 2, this);
	}
}
