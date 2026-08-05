import java.awt.EventQueue;

import javax.swing.Action;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import java.awt.FlowLayout;

public class A_메인 extends BF {
	public JPanel panel;
	public JLabel label;
	public JTextField textField;
	public JLabel label_1;
	public JPanel panel_1;
	public JLabel label_2;
	public JScrollPane scrollPane;
	public JPanel panel_2;
	public JLabel label_3;
	public JPanel panel_3;
	public JPanel panel_4;
	public JPanel panel_5;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_메인 frame = new A_메인();
					frame.setLocationRelativeTo(null);
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
	public A_메인() {
		setTitle("메인");
		setBounds(100, 100, 1065, 691);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(212, 0, 837, 652);
		panel.setBackground(new Color(240, 240, 240));
		getContentPane().add(panel);
		panel.setLayout(null);

		label_2 = new JLabel("Skillmall");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		label_2.setBounds(10, 12, 142, 47);
		panel.add(label_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 77, 804, 533);
		panel.add(scrollPane);

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(new FlowLayout(FlowLayout.LEFT, 20, 20));

		label_3 = new JLabel("룰렛");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setForeground(Color.GRAY);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(426, 35, 83, 15);
		panel.add(label_3);

		panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 0, 0));
		panel_3.setBounds(505, 35, 1, 15);
		panel.add(panel_3);

		panel_4 = new JPanel();
		panel_4.setBackground(Color.BLACK);
		panel_4.setBounds(596, 35, 1, 15);
		panel.add(panel_4);

		panel_5 = new JPanel();
		panel_5.setBackground(Color.BLACK);
		panel_5.setBounds(680, 35, 1, 15);
		panel.add(panel_5);

		label_4 = new JLabel("룰렛");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setForeground(Color.GRAY);
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(508, 36, 83, 15);
		panel.add(label_4);

		label_5 = new JLabel("룰렛");
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setForeground(Color.GRAY);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(597, 35, 83, 15);
		panel.add(label_5);

		label_6 = new JLabel("룰렛");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setForeground(Color.GRAY);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(686, 35, 83, 15);
		panel.add(label_6);

		label = new JLabel(getIcon("logo.png", 160, 86));
		label.setBounds(10, 12, 175, 56);
		getContentPane().add(label);

		textField = new JTextField();
		textField.setBounds(10, 80, 125, 33);
		getContentPane().add(textField);
		textField.setColumns(10);

		label_1 = new JLabel(getIcon("search.png", 35, 35));
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setBounds(144, 73, 45, 44);
		getContentPane().add(label_1);

		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(10, 125, 191, 479);
		getContentPane().add(panel_1);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);

		addMenu();
		updateForm();
	}

	@Override
	public void updateForm() {
		if (User.uno == -1) {
			label_3.setText("");
			label_4.setText("로그인");
			label_5.setText("");
			label_6.setText("");
			label_5.setForeground(Color.black);
			label_6.setForeground(Color.black);
			panel_3.setVisible(false);
			panel_4.setVisible(false);
			panel_5.setVisible(false);
		} else if (User.uno == 0) {
			label_3.setText("룰렛");
			label_4.setText("로그아웃");
			label_5.setText("상품추가");
			label_6.setText("분석");
			label_5.setForeground(Color.red);
			label_6.setForeground(Color.blue.brighter());
			panel_3.setVisible(true);
			panel_4.setVisible(true);
			panel_5.setVisible(true);
		} else {
			label_3.setText("룰렛");
			label_4.setText("로그아웃");
			label_5.setText("장바구니");
			label_6.setText("마이페이지");
			label_5.setForeground(Color.black);
			label_6.setForeground(Color.black);
			panel_3.setVisible(true);
			panel_4.setVisible(true);
			panel_5.setVisible(true);
		}
		addProduct();
	}

	private void addProduct() {
		panel_2.removeAll();
		try (var rs = DB.res("select * from product where true " + where + like)) {
			int w = (scrollPane.getWidth() - 150) / 5, h = scrollPane.getHeight() / 3, i = 0;
			Random rand = new Random();
			while (rs.next()) {
				int pno =rs.getInt(1);
				if (User.uno == 0) { // 관리자
					var pp = new K_관리자패널(getIcon("product/"+rs.getInt(1)+".png",80,80), rs.getString("pname"), rs.getInt("pprice"), rs.getInt("pcount"));
					pp.button.addActionListener(e->{
						showPage(new L_상품수정(pno));
					});
					pp.button_1.addActionListener(e->{
						try {
							var rnos = getReviewNos(pno);
							DB.delete("orders", "pno = ?", pno);
							if(rnos.size()>0)
								DB.delete("review", "rno in ("+rnos.toString().replaceAll("\\[|\\]|| ", "")+")");
							DB.delete("product", "pno = ?", pno);
							for (var rno : rnos) {
								Files.delete(Path.of("./datafiles/review/"+rno+".png"));
								Files.delete(Path.of("./datafiles/review/"+rno+".jpg"));
								Files.delete(Path.of("./datafiles/review/"+rno+".gif"));
							}
							Files.delete(Path.of("./datafiles/product/"+pno+".png"));
							addProduct();
						} catch (SQLException e1) {
							e1.printStackTrace();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
					});
					pp.setPreferredSize(new Dimension(w,h));
					panel_2.add(pp);
					i++;
				} else { // 일반
					var pp = new A_ProductPanel();
					pp.label.setIcon(getIcon("product/" + rs.getInt(1) + ".png", 80, 80));
					pp.setPreferredSize(new Dimension(w, h));
					int rno = rand.nextInt(3);
					if (i % 5 >= 4)
						rno = 0;
					else if (i % 5 >= 3)
						rno = rand.nextInt(2);
					if (rno == 0) {
						pp.label_1.setPreferredSize(new Dimension(0, (h - 10) / 2));
						pp.label_1.setText(String.format(
								"<html><prev><b>%s</b><br><br><font color = red>%,d원</font>  <font color = gray>재고%d개",
								rs.getString("pname"), rs.getInt("pprice"), rs.getInt("pcount")));
					} else {
						pp.add(pp.label_1, "East");
						int nw = (w + 20) * (rno + 1) - 20;
						pp.label_1.setPreferredSize(new Dimension(nw - w, 0));
						pp.label_1.setText(String.format(
								"<html><span style = 'size:13px;font-weight:bold'>%s</span><br><br><font color = gray>%s</font><br><br><font color = red>%,d원</font><br><br><font color =gray>재고 %d개",
								rs.getString("pname"), rs.getString("pcontent"), rs.getInt("pprice"),
								rs.getInt("pcount")));
						pp.setPreferredSize(new Dimension(nw, h));
					}
					if (rs.getInt("pcount") <= 0)
						pp.setSoldOut(true);
					else {
						pp.label.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								if (User.uno == -1) {
									msgErr("로그인을 해주세요.");
									showPage(new B_로그인(() -> showPage(new C_상품_상세정보(pno))));
									return;
								}
								showPage(new C_상품_상세정보(pno));
							}
						});
					}
					panel_2.add(pp);
					i += rno + 1;
				}
			}
			i += 4;
			panel_2.setPreferredSize(new Dimension(0, 20 + (h + 20) * (i / 5)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_2.revalidate();
		panel_2.repaint();
	}

	private Set<Integer> getReviewNos(int pno) {
		var result = new HashSet<Integer>();
		try (var rs = DB.res("select rno from review where pno = ?",pno)) {
			while(rs.next()) result.add(rs.getInt(1));
			return result;
		} catch (SQLException e) {
			return null;
		}
	}

	String where = "", like = "";

	private void addMenu() {
		panel_1.removeAll();
		A_MenuBar.jls.clear();
		try (var rs = DB.res("select * from category ")) {
			while (rs.next()) {
				var pp = new A_MenuBar(rs.getInt(1), rs.getString(2));
				panel_1.add(pp);
			}
			var jls = A_MenuBar.jls;
			for (JLabel jl : jls) {
				jl.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						for (JLabel jl : jls) {
							jl.setForeground(Color.gray);
						}
						var sql = " and dno = " + jl.getName();
						if (where.equals(sql)) {
							jl.setForeground(Color.gray);
							where = "";
						} else {
							jl.setForeground(Color.red);
							where = sql;
						}
						addProduct();
					}
				});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_1.revalidate();
		panel_1.repaint();
	}

	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (User.uno == 0) {
				showPage(new N_분석());
			} else {
				showPage(new F_마이페이지());
			}
		}
	}

	private class Label_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new G_룰렛());
		}
	}

	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (User.uno == 0) {
				showPage(new M_상품등록());
			} else {
				showPage(new D_장바구니());
			}
		}
	}

	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (User.uno == -1) {
				showPage(new B_로그인());
			} else {
				User.uno = -1;
				msgInfo("로그아웃되었습니다.");
				updateForm();
			}
		}
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (textField.getText().isBlank()) {
				msgErr("빈칸이 존재합니다.");
				reset();
				return;
			}
			like = " and pname like '%" + textField.getText() + "%'";
			addProduct();
			if (panel_2.getComponentCount() == 0) {
				msgErr("검색 결과가 없습니다.");
				reset();
			}
		}

	}

	private void reset() {
		like = where = "";
		textField.setText("");
		addMenu();
		addProduct();
	}
}
