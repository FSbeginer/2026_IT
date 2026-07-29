import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

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

	/**
	 * Create the panel.
	 */
	public D_패널(String txt, String txt2, LocalTime time, int timed, String transfer) {
		setSize(410, 116);
		setLayout(null);
		
		label = new JLabel(txt);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 13, 111, 21);
		add(label);
		
		label_1 = new JLabel(txt2);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(275, 13, 103, 21);
		add(label_1);
		
		label_2 = new JLabel(transfer);
		label_2.setForeground(Color.WHITE);
		label_2.setOpaque(true);
		label_2.setBackground(Color.ORANGE);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(159, 13, 90, 21);
		add(label_2);
		
		label_3 = new JLabel(time.format(DateTimeFormatter.ofPattern("HH:mm")));
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_3.setForeground(new Color(34, 44, 215));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(22, 43, 101, 29);
		add(label_3);
		
		label_4 = new JLabel(time.plusMinutes(timed).format(DateTimeFormatter.ofPattern("HH:mm")));
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_4.setForeground(new Color(34, 44, 215));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(275, 44, 101, 29);
		add(label_4);
		
		label_5 = new JLabel(BF.getIcon("icon/trains.png",80,30));
		label_5.setBounds(32, 72, 91, 34);
		add(label_5);
		
		label_6 = new JLabel(timed+"분 소요");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(174, 82, 57, 15);
		add(label_6);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.blue);
		g2.fillRoundRect(0, 0, 30, getHeight()-1, 20, 20);
		g2.setColor(Color.white);
		g2.fillRect(15, 0, 30, getHeight()-1);
		g2.setColor(new Color(120,150,230));
		g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
	}
}
