import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.HeadlessException;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

public class E_충전소 extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;
	public JLabel label;
	public JLabel label_1;

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
		setBounds(100, 100, 568, 434);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(10, 15, 526, 155);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("충전소");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label.setBounds(8, 5, 57, 15);
		panel.add(label);

		label_1 = new JLabel(getIcon("logo/cash.png", 50, 50));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setText("포인트 충전");
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setVerticalTextPosition(SwingConstants.BOTTOM);
		label_1.setBounds(179, 5, 167, 145);
		panel.add(label_1);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		panel_1.setBounds(10, 180, 523, 168);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 10, 10));

		button = new JButton("돌아가기");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(14, 361, 242, 26);
		getContentPane().add(button);

		button_1 = new JButton("결제하기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(289, 362, 242, 26);
		getContentPane().add(button_1);

		addMenu();
	}

	JLabel[] jls = new JLabel[6];
	int[] price = { 1000, 5000, 10000, 50000, 100000, 0 };
	int idx = -1;

	private void addMenu() {
		for (int i = 0; i < 5; i++) {
			int idx = i;
			JLabel jl = new JLabel(String.format("%,d원", price[idx]), 0);
			jl.setFont(new Font("맑은 고딕", 1, 13));
			jl.setBorder(new LineBorder(Color.LIGHT_GRAY));
			jl.setOpaque(true);
			jl.setBackground(Color.white);
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					E_충전소.this.idx = idx;
					for (var jl : jls) {
						jl.setBorder(new LineBorder(Color.lightGray));
					}
					jl.setBorder(new LineBorder(Color.blue, 2));
				}
			});
			jls[idx] = jl;
			panel_1.add(jl);
		}
		JLabel jl = new JLabel(String.format(
				"<html><div align = 'center'>직접입력<span style = 'font-weight:normal;font-size:9px;color:gray'><br><br>원하는 금액"),
				0);
		jl.setFont(new Font("맑은 고딕", 1, 13));
		jl.setBorder(new LineBorder(Color.LIGHT_GRAY));
		jl.setOpaque(true);
		jl.setBackground(Color.white);
		jl.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					var input = JOptionPane.showInputDialog(null, "충전할 금액을 입력해주세요.", "직접입력", 1);
					if (input == null)
						return;

					int r = Integer.parseInt(input);
					price[5] = r;
					idx = 5;
					for (var jl : jls) {
						jl.setBorder(new LineBorder(Color.lightGray));
					}
					jl.setBorder(new LineBorder(Color.blue, 2));
					jl.setText(String.format(
							"<html><div align = 'center'>직접입력<span style = 'font-weight:normal;font-size:9px;color:gray'><br><br>%,d원",
							r));
				} catch (NumberFormatException e1) {
					msgErr("1이상의 숫자만 입력해주세요.");
				}
			}
		});
		panel_1.add(jl);
		jls[5] = jl;
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			previous();
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (idx == -1) {
				msgErr("금액을 선택해주세요.");
				return;
			}
			try {
				DB.update("user","point = point+?", "uno = ?", price[idx], User.uno);
				msgInfo("충전이 완료되었습니다.");
				dispose();
				previous();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
