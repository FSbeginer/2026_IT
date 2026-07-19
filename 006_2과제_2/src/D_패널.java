import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalTime;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class D_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;

	/**
	 * Create the panel.
	 */
	public D_패널(String start, String end, String txt, LocalTime time, LocalTime time2, int passingtime) {
		setSize(390, 108);
		setLayout(null);
		
		label = new JLabel(start);
		label.setBounds(39, 12, 87, 25);
		add(label);
		
		label_1 = new JLabel(end);
		label_1.setBounds(262, 12, 87, 25);
		add(label_1);
		
		label_2 = new JLabel(txt);
		label_2.setBackground(Color.ORANGE);
		label_2.setOpaque(true);
		label_2.setForeground(new Color(255, 255, 255));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(146, 12, 82, 18);
		add(label_2);
		
		label_3 = new JLabel(time.toString());
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_3.setForeground(Color.BLUE);
		label_3.setBounds(33, 44, 70, 30);
		add(label_3);
		
		label_4 = new JLabel(time2.toString());
		label_4.setForeground(Color.BLUE);
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_4.setBounds(261, 46, 70, 30);
		add(label_4);
		
		label_5 = new JLabel("→");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(Color.BLUE);
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_5.setBounds(150, 46, 70, 30);
		add(label_5);
		
		label_6 = new JLabel(BF.getIcon("icon/trains.png",80,30));
		label_6.setBounds(32, 75, 82, 30);
		add(label_6);
		
		label_7 = new JLabel(passingtime+"분 소요");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(146, 77, 82, 22);
		add(label_7);
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.blue);
		g2.fillRoundRect(0, 0, 50, getHeight(), 25, 25);
		g2.setColor(Color.white);
		g2.fillRect(15, 0, 50, getHeight());
		g2.setColor(new Color(120,155,200));
		g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
		g2.dispose();
	}
}
