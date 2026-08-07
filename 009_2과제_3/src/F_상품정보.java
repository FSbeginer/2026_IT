import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.HeadlessException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class F_상품정보 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_상품정보 frame = new F_상품정보(1);
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
	int pno;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;
	public JLabel label_7;
	public JScrollPane scrollPane;
	public JPanel panel_2;

	public F_상품정보(int pno) {
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 594, 209);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("");
		label.setBounds(12, 24, 220, 153);
		panel.add(label);

		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setBounds(244, 10, 338, 34);
		panel.add(label_1);

		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_2.setBounds(244, 44, 338, 24);
		panel.add(label_2);

		label_3 = new JLabel("New label");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_3.setBounds(244, 78, 338, 24);
		panel.add(label_3);

		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_4.setBounds(244, 112, 338, 24);
		panel.add(label_4);

		label_5 = new JLabel("New label");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_5.setBounds(244, 146, 338, 24);
		panel.add(label_5);

		label_6 = new JLabel("New label");
		label_6.setForeground(Color.GRAY);
		label_6.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_6.setBounds(244, 175, 338, 24);
		panel.add(label_6);

		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(12, 229, 594, 179);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		label_7 = new JLabel("리뷰");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_7.setBounds(12, 10, 40, 15);
		panel_1.add(label_7);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 32, 570, 137);
		panel_1.add(scrollPane);

		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);

		button = new JButton("돌아가기");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		button.setBounds(12, 418, 289, 23);
		getContentPane().add(button);

		button_1 = new JButton("구매하기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		button_1.setBounds(313, 418, 293, 23);
		getContentPane().add(button_1);
		setTitle("상품정보");
		this.pno = pno;
		setBounds(100, 100, 634, 489);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		load();
	}
	int price;
	private void load() {
		try (var rs = DB.res(
				"select *, avg(star) s, sum(`order`.quantity) cnt from product join category using(cno) join sub_area using(sno) join area using(ano) left join `order` using(pno) left join review using(ono) where pno =? group by pno;",
				pno)) {
			rs.next();
			try {
				Helper.getImage(rs.getBytes("img"), label.getWidth(), label.getHeight(),
						x -> label.setIcon(new ImageIcon(x)));
			} catch (Exception e) {
				e.printStackTrace();
			}
			label_1.setText(rs.getString("pname"));
			label_2.setText(rs.getString("description"));
			label_3.setText(String.format("판매지 : %s-%s", rs.getString("aname"), rs.getString("sname")));
			label_4.setText(String.format("분류 : %s", rs.getString("cname")));
			label_5.setText(String.format("가격 : %,d원", rs.getInt("price")));
			label_6.setText(String.format("평점 : %.1f점", rs.getDouble("s")));
			price = rs.getInt("price");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from review join `order` using(ono) join user using(uno) where pno = ?", pno)) {
			int w = 550, h = 54, i = 0;
			while (rs.next()) {
				var pp = new ReviewPanel(rs.getString("uname"), rs.getString("review"), rs.getDouble("star"));
				pp.setLocation(0, 59 * i);
				panel_2.add(pp);
				i++;
			}
			panel_2.setPreferredSize(new Dimension(0, 59 * i));
			scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			previous();
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String input = JOptionPane.showInputDialog(null, "수량을 입력해주세요.", "질문", 1);
			if(input!=null) {
				int cnt=0;
				try {
					cnt = Integer.parseInt(input);
					if(cnt<0) {
						msgErr("1 이상의 숫자를 입력해주세요.");
						return;
					}
				} catch (NumberFormatException e1) {
					msgErr("1 이상의 숫자를 입력해주세요.");
					return;
				}
				try {
					var have = DB.select("select point from user where uno = ?", Integer.class, User.uno);
					if(have < price * cnt) {
						msgErr("포인트가 부족합니다.");
						return;
					}
					DB.insert("`order`", 0,pno, User.uno, LocalDate.now(), cnt);
					DB.update("user", "point = point - ?", "uno = ?", price * cnt, User.uno);
					msgInfo("구매가 완료되었습니다.");
					
					var myLoca = DB.select("select concat(aname,'-',sname) from user join sub_area using(sno) join area using(ano) where uno = ?", Integer.class, User.uno);
					var destination = DB.select("select concat(aname,'-',sname) from user join sub_area using(sno) join area using(ano) where pno = ?", Integer.class, pno);
					msgInfo(String.format("%s에서 %s(고객님의 위치)로 배송 시작합니다.", destination, myLoca));
					showPage(new G_배송(pno));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
