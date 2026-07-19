import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;

public class A_메인 extends BF {
	public JPanel panel;
	public JLabel label;
	public JTextField textField;
	public JLabel label_1;
	public JPanel panel_1;
	public JLabel label_2;
	public JScrollPane scrollPane;
	public JPanel panel_5;
	public JPanel panel_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	List<DetailLabel> bundle;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_메인 frame = new A_메인();
					frame.setVisible(true);
					frame.setLocationRelativeTo(null);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public A_메인() {
		setTitle("메인");
		setBounds(100, 100, 1085, 663);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(228, 0, 841, 624);
		getContentPane().add(panel);
		panel.setLayout(null);

		label_2 = new JLabel("Skillmall");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label_2.setBounds(24, 14, 169, 34);
		panel.add(label_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(24, 59, 805, 551);
		panel.add(scrollPane);

		panel_5 = new JPanel();
		panel_5.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel_5);
		panel_5.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 240, 240));
		panel_2.setBounds(508, 14, 302, 19);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 1, 0, 0));

		label_3 = new JLabel("룰렛");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_3);

		label_4 = new JLabel("로그아웃");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setBorder(new MatteBorder(0, 1, 0, 1, (Color) Color.GRAY));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_4);

		label_5 = new JLabel("장바구니");
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setBorder(new MatteBorder(0, 0, 0, 1, (Color) Color.GRAY));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_5);

		label_6 = new JLabel("마이페이지");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		panel_2.add(label_6);

		label = new JLabel(getIcon("logo.png", 150, 100));
		label.setBounds(12, 14, 204, 66);
		getContentPane().add(label);

		textField = new JTextField();
		textField.setBounds(12, 94, 143, 37);
		getContentPane().add(textField);
		textField.setColumns(10);

		label_1 = new JLabel("검색");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setBounds(167, 94, 47, 37);
		getContentPane().add(label_1);

		panel_1 = new JPanel();
		panel_1.setBounds(12, 145, 204, 465);
		getContentPane().add(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);

		updateForm();
	}
	String like = "", where = "";
	
	private void loadProduct() {
		panel_5.removeAll();
		try (var rs = DB.res("select * from product where true "+like+where)) {
			int w = (scrollPane.getWidth() - 70 - 40) / 5, h = scrollPane.getHeight() / 3, i = 0;
			Random rand = new Random();
			while (rs.next()) {
				int rno = rand.nextInt(3) + 1;
				if (5 - i % 5 < rno)
					rno = 1;
				int pno = rs.getInt("pno");
				switch (rno) {
				case 1: {
					var pp = new A_패널(getIcon("product/" + rs.getInt("pno") + ".png", 90, 90), rs.getString("pname"),
							rs.getInt("pprice"), rs.getInt("pcount"));
					pp.setPreferredSize(new Dimension(w, h));
					panel_5.add(pp);
					pp.paybutton.addActionListener(new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if (User.uno == -1) {
								msgErr("로그인을 해주세요.");
								showPage(new B_로그인(new Runnable() {
									@Override
									public void run() {
										 showPage(new C_상품상세정보(pno));	
									}
								}));
							} else {
								showPage(new C_상품상세정보(pno));
							}
						}
					});
					break;
				}
				case 2:
				case 3: {
					int pw = w * rno + 10 * (rno - 1);
					var pp = new A_패널2(getIcon("product/" + rs.getInt("pno") + ".png", 90, 90), rs.getString("pname"),
							rs.getString("pcontent"), rs.getInt("pprice"), rs.getInt("pcount"));
					pp.setPreferredSize(new Dimension(pw, h));
					panel_5.add(pp);
					pp.paybutton.addActionListener(new ActionListener() {

						@Override
						public void actionPerformed(ActionEvent e) {
							if (User.uno == -1) {
								msgErr("로그인을 해주세요.");
								showPage(new B_로그인(new Runnable() {
								@Override
								public void run() {
									 showPage(new C_상품상세정보(pno));	
								}
							}));
							} else {
								showPage(new C_상품상세정보(pno));
							}

						}
					});
					break;
				}
				}
				i += rno;
			}
			panel_5.setPreferredSize(new Dimension(0, 10 + (h + 10) * ((i + 4) / 5)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_5.revalidate();
		panel_5.repaint();
	}

	@Override
	public void updateForm() {
		JLabel[] navi = { label_3, label_4, label_5, label_6 };
		if (User.uno == -1) {
			for (var jl : navi) {
				jl.setVisible(false);
			}
			navi[2].setVisible(true);
			navi[2].setBorder(null);
			navi[2].setText("로그인");
		} else {
			for (var jl : navi) {
				jl.setVisible(true);
			}
			navi[2].setBorder(new MatteBorder(0, 0, 0, 1, Color.gray));
			navi[2].setText("장바구니");
		}
		textField.setText("");
		addCategory();
		loadProduct();
	}

	private void addCategory() {
		panel_1.removeAll();
		bundle = new ArrayList<DetailLabel>();
		try (var rs = DB.res("select * from category")) {
			while (rs.next()) {
				CategoryPanel pp = new CategoryPanel(rs.getInt(1), rs.getString(2), bundle);
				panel_1.add(pp);
			}
			for (var detail : bundle) {
				detail.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selectedDetail(detail);
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_1.revalidate();
		panel_1.repaint();
	}

	DetailLabel prevSelected;

	private void selectedDetail(DetailLabel detail) {
		if (prevSelected != null)
			prevSelected.setSelected(false);
		detail.setSelected(true);
		where = " and dno = "+detail.dno;
		prevSelected = detail;
		loadProduct();
	}

	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (((JLabel) e.getSource()).isVisible()) {
				User.uno = -1;
				msgErr("로그아웃되었습니다.");
				updateForm();
			}
		}
	}

	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (((JLabel) e.getSource()).isVisible()) {
				showPage(new F_마이페이지());
			}
		}
	}

	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (((JLabel) e.getSource()).isVisible()) {
				if (User.uno == -1) {
					showPage(new B_로그인(null));
				} else {
					showPage(new D_장바구니());
				}
			}
		}
	}

	private class Label_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (((JLabel) e.getSource()).isVisible()) {
				showPage(new G_룰렛());
			}
		}
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(textField.getText().isBlank()) {
				msgErr("빈칸이 존재합니다..");
				like = where = "";
				textField.setText("");
				prevSelected = null;
				updateForm();
			}
			like = " and pname like '%"+textField.getText()+"%'";
			loadProduct();
			if(panel_5.getComponentCount()==0) {
				msgErr("검색 결과가 없습니다.");
				like = where = "";
				textField.setText("");
				prevSelected = null;
				updateForm();
			}
		}
	}
}
