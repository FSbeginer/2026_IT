import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import java.awt.Dimension;
import javax.swing.border.MatteBorder;
import java.awt.Color;
import java.awt.Font;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Counter extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	private int cnt = 1;
	
	public void setCnt(int cnt) {
		if(cnt<1 || cnt>max) return;
		this.cnt = cnt;
		label_2.setText(cnt+"");
	}
	public int getCnt() {
		return cnt;
	}
	
	/**
	 * Create the panel.
	 */
	private int max = 999999;
	
	public void setMax(int max) {
		this.max = max;
	}
	public Counter() {
		setLayout(new BorderLayout(0, 0));
		
		label = new JLabel("-");
		label.addMouseListener(new LabelMouseListener());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		label.setPreferredSize(new Dimension(35, 35));
		add(label, BorderLayout.WEST);
		
		label_1 = new JLabel("+");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		label_1.setPreferredSize(new Dimension(35, 35));
		add(label_1, BorderLayout.EAST);
		
		label_2 = new JLabel("1");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setBorder(new MatteBorder(1, 1, 1, 1, (Color) Color.LIGHT_GRAY));
		add(label_2, BorderLayout.CENTER);

	}

	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setCnt(cnt-1);
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setCnt(cnt+1);
		}
	}
}
