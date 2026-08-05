import java.awt.EventQueue;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.ActionEvent;

public class F_마이페이지 extends BF {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JButton button;
	public JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_마이페이지 frame = new F_마이페이지();
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
	public F_마이페이지() {
		getContentPane().setBackground(new Color(240, 240, 240));
		setTitle("마이페이지");
		setBounds(100, 100, 855, 533);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(230, 230, 230)));
		panel.setBounds(10, 12, 819, 128);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("New label");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setBounds(5, 12, 258, 39);
		panel.add(label);

		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(7, 56, 193, 29);
		panel.add(label_1);

		label_2 = new JLabel("New label");
		label_2.setBounds(9, 87, 193, 29);
		panel.add(label_2);

		label_3 = new JLabel("New label");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_3.setBounds(376, 19, 285, 39);
		panel.add(label_3);

		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_4.setBounds(375, 62, 286, 39);
		panel.add(label_4);

		button = new RoundButton("쿠폰함");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(Color.ORANGE);
		button.setForeground(Color.BLACK);
		button.setBounds(659, 23, 151, 31);
		panel.add(button);

		button_1 = new RoundButton("찜한 목록 보러가기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(255, 98, 98));
		button_1.setBounds(661, 70, 151, 31);
		panel.add(button_1);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(10, 158, 819, 324);
		getContentPane().add(scrollPane);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);

		load();
	}

	private void load() {
		try (var rs = DB.res("select * from orders where uno = ? order by odate desc,oprice", User.uno)) {
			int w = 772, h = 131, i = 0;
			while (rs.next()) {
				var pp = new F_패널(rs.getInt("ono"));
				pp.setLocation(15, 15 + 141 * i);
				int pno = rs.getInt("pno");
				pp.button.addActionListener(e -> {
					showPage(new C_상품_상세정보(pno));
				});
				pp.label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							boolean flag = DB.select("select 1 from review where uno = ? and pno =?", Boolean.class,
									User.uno, pno);
							if (e.getClickCount() == 2) {
								if (flag) {
									var r = JOptionPane.showConfirmDialog(null, "리뷰가 등록되어있습니다.\n보러가시겠습니까?", "질문", 0, 3);
									if(r==0) {
										showPage(new I_리뷰(pno));
									}
								} else {
									showPage(new J_리뷰작성(pno));
								}
							}
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0, 15 + 141 * i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from user where uno = ?", User.uno)) {
			rs.next();
			label.setText(rs.getString("uname") + "님의 정보");
			label_1.setText("이름 : " + rs.getString("uname"));
			label_2.setText("연락처 : " + rs.getString("unumber"));
			label_3.setText(String.format("보유 포인트 %,dP", rs.getInt("uprice")));
			label_4.setText("총 주문수 " + panel_1.getComponentCount() + "개");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showPage(new H_찜리스트());
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
		}
	}
}
