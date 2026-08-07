import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
public class B_홈 extends BP {
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JPanel panel_4;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JPanel panel_5;
	public JLabel label_3;
	public JLabel label_4;
	public JPanel panel_6;
	public JPanel panel_7;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;

	/**
	 * Create the panel.
	 */
	public B_홈() {
		setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(12, 10, 888, 207);
		add(panel);
		panel.setLayout(new GridLayout(0, 3, 10, 0));
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		label = new JLabel("광고");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label.setBounds(12, 10, 57, 15);
		panel_2.add(label);
		
		panel_5 = new JPanel();
		panel_5.setBounds(12, 48, 265, 137);
		panel_2.add(panel_5);
		
		panel_3 = new JPanel();
		panel_3.addMouseListener(new Panel_3MouseListener());
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		label_3 = new JLabel(getIcon("logo/cash.png",50,50));
		label_3.setBounds(50, 38, 180, 117);
		panel_3.add(label_3);
		
		label_4 = new JLabel("<html><div align = 'center'><b><font size = 4>충전소</font></b><br><font color = gray>포인트 충전하기");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(47, 159, 183, 37);
		panel_3.add(label_4);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_4);
		panel_4.setLayout(null);
		
		label_1 = new JLabel("바로가기");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setBounds(12, 10, 57, 15);
		panel_4.add(label_1);
		
		panel_6 = new JPanel();
		panel_6.addMouseListener(new Panel_6MouseListener());
		panel_6.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_6.setBounds(12, 35, 131, 162);
		panel_4.add(panel_6);
		panel_6.setLayout(null);
		
		label_5 = new JLabel(getIcon("logo/maze.png",50,50));
		label_5.setBounds(0, 1, 131, 116);
		panel_6.add(label_5);
		
		label_7 = new JLabel("<html><div align = 'center'><b><font size = 4>미로</font></b><br><font color = gray>포인트 적립");
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_7.setBounds(0, 121, 130, 37);
		panel_6.add(label_7);
		
		panel_7 = new JPanel();
		panel_7.addMouseListener(new Panel_7MouseListener());
		panel_7.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_7.setBounds(148, 35, 129, 162);
		panel_4.add(panel_7);
		panel_7.setLayout(null);
		
		label_6 = new JLabel(getIcon("logo/roulette.png",50,50));
		label_6.setBounds(2, 0, 124, 116);
		panel_7.add(label_6);
		
		label_8 = new JLabel("<html><div align = 'center'><b><font size = 4>경품</font></b><br><font color = gray>룰렛 뽑기");
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_8.setBounds(0, 121, 130, 37);
		panel_7.add(label_8);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(12, 227, 888, 223);
		add(panel_1);
		panel_1.setLayout(null);
		
		label_2 = new JLabel("사람들이 많이 구매하는 카테고리");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setBounds(12, 10, 225, 15);
		panel_1.add(label_2);

	}

	private class Panel_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((MainFrame)SwingUtilities.getWindowAncestor(label)).showpage(new E_충전소());
		}
	}
	private class Panel_7MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((MainFrame)SwingUtilities.getWindowAncestor(label)).showpage(new H_경품());
		}
	}
	private class Panel_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((MainFrame)SwingUtilities.getWindowAncestor(label)).showpage(new I_미로());
		}
	}
	class AdvertisementPanel extends JPanel {
		float alpha;
		int hold, idx;
		Timer timer;
		boolean fade;
		public AdvertisementPanel() {
			timer = new Timer(30, e->{
				if(!fade) {
					hold ++;
					if(hold>=70) {
						fade=true;
					}
				}
			});
			timer.start();
		}
	}
}
