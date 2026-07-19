import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;

import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class A_패널2 extends JPanel {
	public JLabel label;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	boolean soldOut;
	public RoundButton paybutton;
	
	public A_패널2(ImageIcon img, String name, String description, int price, int left) {
		soldOut = left == 0;
		addMouseListener(new ThisMouseListener());
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setLayout(new BorderLayout(0, 0));
		
		label = new JLabel(img) {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if(soldOut) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
					g2.setColor(Color.black);
					g2.translate(getWidth()/2, getHeight()/2);
					int w = 70, h = 35;
					g2.fillRect(-w/2, -h/2, w, h);
					g2.setColor(Color.white);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
					g2.setFont(new Font("맑은 고딕", 1, 20));
					var str = "품절";
					g2.drawString(str, -g2.getFontMetrics().stringWidth(str)/2, 6);
				}
			}
		};
		label.setPreferredSize(new Dimension(150, 17));
		add(label, BorderLayout.WEST);
		
		label_1 = new JLabel();
		add(label_1, BorderLayout.CENTER);
		
		if(!soldOut)
			label_1.setText(String.format("<html><b><span style ='font-weight:bold;font-size:12px''>%s</b></span><br><br>"
				+ "<font color = gray>%s</font><br><br><font color =red>%,d원</font><br><br><font color = gray>재고 %d개", 
				name, description, price, left));
		else
			label_1.setText(String.format("<html><b><span style ='font-weight:bold;font-size:12px''>%s</b></span><br><br>"
					+ "<font color = gray>%s</font><br><br><font color =red>%,d원<br><br>품절", 
					name, description, price));
		
		label.setLayout(new GridBagLayout());
		label.add(paybutton = new RoundButton("결제하기"));
		paybutton.setBackground(new Color(255,100,100));
		paybutton.setVisible(false);
		paybutton.addMouseListener(new ThisMouseListener());
	}

	private class ThisMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			if(soldOut) return;
			setBorder(new LineBorder(new Color(255,100,100), 2));
			paybutton.setVisible(true);
		}

		@Override
		public void mouseExited(MouseEvent e) {
			if(soldOut) return;
			setBorder(new LineBorder(Color.LIGHT_GRAY));
			paybutton.setVisible(false);
		}
	}
}
