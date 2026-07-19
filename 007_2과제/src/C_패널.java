import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class C_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public OverlayLabel over;
	/**
	 * Create the panel.
	 */
	public C_패널(ImageIcon img,String name, int cnt, int dayoff) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setSize(234, 186);
		setLayout(null);

		label = new JLabel(img);
		label.setBounds(23, 10, 186, 130);
		add(label);

		label_1 = new JLabel(name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(23, 150, 186, 26);
		add(label_1);
		
		label.setLayout(new BorderLayout());
		label.add(over = new OverlayLabel());
		over.setText(String.format("<html><font size = 3>진료횟수%d번<br><br>휴일 : %s요일<br><br></font>더블클릭으로 선택하세요.", cnt, getWeekText(dayoff)));
	}
	
	private String getWeekText(int week) {
		return "일,월,화,수,목,금,토".split(",")[week-1];
	}
	

	class OverlayLabel extends JLabel {
		public OverlayLabel() {
			setForeground(Color.white);
			setHorizontalAlignment(0);
			setFont(new Font("맑은 고딕", 1, 9));
			setVisible(false);
		}

		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
			g2.setColor(new Color(55,125,225));
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.dispose();
			super.paintComponent(g);
		}
	}
}
