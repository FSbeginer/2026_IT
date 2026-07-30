import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

public class Rows {
	public static JLabel profileStory(int uno, String nick,String content, int w, int h) {
		var jl = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(getCircle(40,40,Color.white), 0, 0, null);
				g2.dispose();
			}
		};
		jl.setSize(w, h);
		Helper.getImage("profile/" + uno + ".jpg", 40,40, true,x->jl.setIcon(new ImageIcon(x)));
		jl.setFont(new Font("맑은 고딕",0,9));
		jl.setText(String.format("<html><b><font color = 'white' size = '4'>%s</b></font><br><font color = 'white' size ='2'>%s", nick,content));
		return jl;
	}
	public static JLabel profileHnick(int uno, String nick, int w, int h) {
		var jl = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(getCircle(35, 35, Color.red), 0, 0, null);
				g2.dispose();
			}
		};
		jl.setSize(w, h);
		Helper.getImage("profile/" + uno + ".jpg", 35,35, true,x->jl.setIcon(new ImageIcon(x)));
		jl.setText("  "+nick);
		jl.setFont(new Font("맑은 고딕",1,12));
		return jl;
	}
	public static JLabel profileVnick(int uno, String nick, int w, int h) {
		var jl = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(getCircle(w, h-20, Color.red), 0, 0, null);
				g2.dispose();
			}
		};
		jl.setSize(w, h);
		Helper.getImage("profile/" + uno + ".jpg", w, h-20, true, x->jl.setIcon(new ImageIcon(x)));
		jl.setVerticalTextPosition(3);
		jl.setHorizontalTextPosition(0);
		jl.setHorizontalAlignment(0);
		jl.setText(nick);
		jl.setFont(new Font("맑은 고딕",0,10));
		return jl;
	}
	public static JLabel myStory(int w, int h) {
		var c = new Color(0,128,255);
		var jl = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(c);
				g2.setFont(new Font("맑은 고딕",1,35));
				g2.drawString("+", w/2-12, (h-20)/2+12);
				g2.dispose();
			}
		};
		jl.setIcon(new ImageIcon(getCircle(w, h-20, c)));
		jl.setSize(w, h);
		jl.setVerticalTextPosition(3);
		jl.setHorizontalTextPosition(0);
		jl.setHorizontalAlignment(0);
		jl.setText("내 스토리");
		jl.setFont(new Font("맑은 고딕",0,10));
		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				((BF)SwingUtilities.getWindowAncestor(jl)).showPage(new E_스토리추가());
			}
		});
		return jl;
	}

	public static BufferedImage getCircle(int w, int h, Color c) {
		var bi = new BufferedImage(w, h, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2f));
		g2.setColor(c);
		g2.draw(new Ellipse2D.Double(1, 1, w-2, h-2));
		g2.dispose();
		return bi;
	}
}
