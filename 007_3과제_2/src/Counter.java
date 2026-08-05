import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Counter extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	private int cnt = 1;
	private int max = 999999;
	public void setCnt(int cnt) {
		if(cnt<1||cnt>max) return;
		this.cnt = cnt;
		label_1.setText(cnt+"");
	}
	public int getCnt() {
		return cnt;
	}
	public void setMax(int max) {
		this.max = max;
	}
	/**
	 * Create the panel.
	 */
	public Counter() {
		setLayout(new BorderLayout(0, 0));
		
		label = new JLabel("-");
		label.addMouseListener(new LabelMouseListener());
		label.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label.setPreferredSize(new Dimension(30, 0));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label, BorderLayout.WEST);
		
		label_1 = new JLabel("1");
		label_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_1.setPreferredSize(new Dimension(40, 0));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(label_1, BorderLayout.CENTER);
		
		label_2 = new JLabel("+");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_2.setPreferredSize(new Dimension(30, 0));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		add(label_2, BorderLayout.EAST);

	}

	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setCnt(cnt+1);
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setCnt(cnt-1);
		}
	}
}
