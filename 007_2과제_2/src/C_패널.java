import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class C_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public C_패널.OverLap jl;

	/**
	 * Create the panel.
	 */
	public C_패널(ImageIcon img, String name, int no, String dayoff) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setSize(236, 158);
		setLayout(null);

		label = new JLabel(img);
		label.setBounds(23, 10, 188, 112);
		label.setForeground(Color.white);
		label.setFont(new Font("맑은 고딕", 1, 11));
		add(label);

		label_1 = new JLabel(name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(33, 132, 176, 26);
		add(label_1);

		label.setLayout(new BorderLayout());
		label.setHorizontalTextPosition(0);
		label.setVerticalTextPosition(0);
		jl = new OverLap();
		jl.setText(String.format("<html><div align = 'center'>진료횟수 %d번<br><br>휴일 : %s<br><br>더블클릭으로 선택하세요.", no, dayoff+"요일"));
		label.add(jl);
		jl.setVisible(false);
	}

	class OverLap extends JLabel {
		public OverLap() {
			setHorizontalAlignment(0);
			setFont(new Font("맑은 고딕",1,11));
			setForeground(Color.white);
		}
		@Override
		protected void paintComponent(Graphics g) {
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setColor(new Color(78, 132, 233));
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.dispose();
			super.paintComponent(g);
		}
	}
}
