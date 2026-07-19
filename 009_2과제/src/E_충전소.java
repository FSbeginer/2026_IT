import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Line.Info;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class E_충전소 extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					E_충전소 frame = new E_충전소();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public E_충전소() {
		getContentPane().setBackground(new Color(240, 240, 240));
		setTitle("충전소");
		setBounds(100, 100, 586, 482);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel.setBounds(12, 14, 546, 157);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));
		
		label = new JLabel(getIcon("logo/cash.png",50,50));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setText("포인트 충전");
		panel.add(label, BorderLayout.CENTER);
		
		label_1 = new JLabel(" 충전소");
		panel.add(label_1, BorderLayout.NORTH);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		panel_1.setBounds(12, 185, 546, 213);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(2, 3, 10, 10));
		
		button = new JButton("돌아가기");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(12, 406, 256, 23);
		getContentPane().add(button);
		
		button_1 = new JButton("결제하기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(302, 406, 256, 23);
		getContentPane().add(button_1);
		
		
		addPrice();
	}
	JLabel[] jls = new JLabel[6];
	public JLabel label;
	public JLabel label_1;
	int[] price = {1000, 5000, 10000, 50000, 100000, -1};
	String defaultText = String.format("<html><div align = 'center'>직접입력<br><br><font color = 'gray' size = 4>원하는 금액");
	int selIdx = -1;
	private void addPrice() {
		for (int i = 0; i < jls.length; i++) {
			jls[i] = new JLabel(String.format("%,d원", price[i]),0);
			if(i==5) {
				jls[i].setText(defaultText);
			}
			jls[i].setFont(new Font("맑은 고딕", 1, 15));
			jls[i].setOpaque(true);
			jls[i].setBackground(Color.white);
			int idx = i;
			jls[i].addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(idx==5) {
						var r = JOptionPane.showInputDialog(null, "충전할 금액을 입력해주세요.", "직접입력", 3);
						if(r==null) return;
						try {
							int input = Integer.parseInt(r);
							price[idx] = input;
						} catch (NumberFormatException e1) {
							msgErr("1이상의 숫자만 입력해주세요.");
							return;
						}
						jls[idx].setText(String.format("<html><div align = 'center'>직접입력<br><br><font color = 'gray' size = 4>%,d원", price[idx]));
					}
					selIdx = idx;
					for (var jl : jls) {
						jl.setBorder(new LineBorder(new Color(192,192,192)));
					}
					if(idx!=-1)
						jls[idx].setBorder(new LineBorder(new Color(55,128,255)));
				}
			});
			jls[i].setBorder(new LineBorder(new Color(192,192,192)));
			panel_1.add(jls[i]);
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(selIdx==-1) {
				msgErr("금액을 선택해주세요.");
				return;
			}
			try {
				DB.update("uesr", "point = point+?", "uno = ?", price[selIdx], User.uno);
				msgInfo("충전이 완료되었습니다.");
				dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
}
