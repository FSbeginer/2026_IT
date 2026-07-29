import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JPanel;

public class Profile extends JPanel {

	Image img;
	boolean circle;
	Color c =Color.red;
	public Profile(String path, int w,int h, boolean circle, Color c) {
		this(path, w,h);
		this.circle = circle;
		this.c=c;
	}
	public Profile(String path, int w,int h, boolean circle) {
		this(path, w,h);
		this.circle = circle;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public Profile(String path, int w,int h) {
		setSize(w, h);
		Helper.getImage(path, w-3,h-3, x->{
			img=x;
			repaint();
		});
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2.5f));
		if(circle) {
			g2.setColor(c);
			g2.drawOval(1, 1, getWidth()-3, getHeight()-3);
		}
		g2.clip(new Ellipse2D.Double(3, 3, getWidth()-6, getHeight()-6));
		g2.drawImage(img, 0, 0, this);
		g2.dispose();
	}
}
