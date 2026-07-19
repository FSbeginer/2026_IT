import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;


public class A_패널 extends JPanel {
	public JLabel label;
	public JPanel panel;
	public JLabel label_1;
	public JLabel label_2;

	/**
	 * Create the panel.
	 */
	boolean soldOut = false;
	RoundButton paybutton;
	
	public A_패널(ImageIcon img, String name, int price, int left) {
		addMouseListener(new ThisMouseListener());
		soldOut = left == 0;
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
		add(label, BorderLayout.CENTER);

		panel = new JPanel();
		panel.setBorder(new EmptyBorder(5, 5, 5, 5));
		panel.setPreferredSize(new Dimension(10, 70));
		add(panel, BorderLayout.SOUTH);
		panel.setLayout(new BorderLayout(0, 0));

		label_1 = new JLabel(
				String.format("<html><font color = red>%,d원 </font><font color = gray>재고 %d개", price, left));
		panel.add(label_1, BorderLayout.CENTER);

		label_2 = new JLabel(name);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		panel.add(label_2, BorderLayout.NORTH);
		
		if(!soldOut)
			label_1.setText(String.format("<html><font color = red>%,d원 </font><font color = gray>재고 %d개", price, left));
		else
			label_1.setText(String.format("<html><font color = red>%,d원 품절", price));
		
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
class RoundButton extends JButton{
	public RoundButton(String txt) {
		super(txt);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
		setForeground(Color.white);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0,0,getWidth()-1,getHeight()-1,25,25);
		super.paintComponent(g);
	}
}