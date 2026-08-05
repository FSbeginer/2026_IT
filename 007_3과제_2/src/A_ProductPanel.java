import javax.swing.JPanel;

import java.awt.AlphaComposite;
import java.awt.BorderLayout;

import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Control.Type;
import javax.sound.sampled.Line.Info;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Ellipse2D.Double;
import java.awt.geom.RoundRectangle2D;

import javax.swing.border.EmptyBorder;

public class A_ProductPanel extends JPanel {
	public JLabel label;
	public JLabel label_1;
	boolean soldOut = false, enter = false;

	/**
	 * Create the panel.
	 */
	public A_ProductPanel() {
		setBorder(new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(5, 5, 5, 5)));
		setLayout(new BorderLayout(0, 0));

		label = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				if (soldOut) {
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f));
					g2.setColor(Color.black);
					g2.fillRect(getWidth() / 2 - 45, getHeight() / 2 - 20, 90, 40);
					g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
					g2.setFont(new Font("맑은 고딕", 1, 20));
					g2.setColor(Color.white);
					g2.drawString("품절", (getWidth() - g2.getFontMetrics().stringWidth("품절")) / 2, getHeight() / 2 + 9);
				} else if (enter) {
					g2.setColor(Color.red.brighter());
					g2.fill(new RoundRectangle2D.Double(getWidth() / 2 - 50, getHeight() / 2 - 15, 100, 30, 15, 15));
					g2.setColor(Color.white);
					g2.setFont(new Font("맑은 고딕", 1, 12));
					g2.drawString("결제하기", (getWidth() - g2.getFontMetrics().stringWidth("결제하기")) / 2,
							getHeight() / 2 + 5);
				}
				g2.dispose();
			}
			@Override
			public boolean contains(int x, int y) {
				return new RoundRectangle2D.Double(getWidth() / 2 - 50, getHeight() / 2 - 15, 100, 30, 15, 15).contains(x,y);
			}
		};
		label.setHorizontalAlignment(0);
		add(label, BorderLayout.CENTER);

		label_1 = new JLabel();
		add(label_1, BorderLayout.SOUTH);

		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(soldOut) return;
				setEnter(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(soldOut) return;
				setEnter(false);
			}
		});
		label.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				if(soldOut) return;
				setEnter(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				if(soldOut) return;
				setEnter(false);
			}
		});
	}

	public void setEnter(boolean enter) {
		this.enter = enter;
		if (enter)
			setBorder(new CompoundBorder(new LineBorder(Color.red.brighter(), 2), new EmptyBorder(5, 5, 5, 5)));
		else
			setBorder(new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(5, 5, 5, 5)));
		repaint();
	}

	public void setSoldOut(boolean soldOut) {
		this.soldOut = soldOut;
		repaint();
	}
}