import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;

public class F_상품정보 extends BF {
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
	int pno;
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JScrollPane scrollPane;
	public JPanel panel_2;
	public F_상품정보(int pno) {
		getContentPane().setBackground(new Color(240, 240, 240));
		setTitle("상품정보");
		this.pno = pno;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 673, 523);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel.setBounds(12, 14, 633, 206);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("");
		label.setBounds(12, 29, 189, 143);
		panel.add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(211, 14, 334, 33);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setBounds(213, 53, 336, 15);
		panel.add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setBounds(213, 81, 336, 15);
		panel.add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setBounds(213, 110, 336, 15);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_5.setBounds(213, 139, 336, 26);
		panel.add(label_5);
		
		label_6 = new JLabel("New label");
		label_6.setBounds(213, 177, 336, 15);
		panel.add(label_6);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel_1.setBounds(12, 234, 633, 194);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_7 = new JLabel("리뷰");
		label_7.setBounds(12, 14, 50, 24);
		panel_1.add(label_7);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 52, 609, 128);
		panel_1.add(scrollPane);
		
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		
		button = new JButton("돌아가기");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(12, 447, 306, 23);
		getContentPane().add(button);
		
		button_1 = new JButton("구매하기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(339, 449, 306, 23);
		getContentPane().add(button_1);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
	}
	int price;
	String loc;
	private void load() {
		try (var rs = DB.res("select *, avg(star) st, count(*) cnt from product join sub_area using(sno) join area using(ano) join category using(cno) left join `order` using(pno) left join review using(ono) where pno = ? group by pno", pno)) {
			rs.next();
			var data = rs.getBytes("img");
			new Thread(new Runnable() {
				@Override
				public void run() {
					label.setIcon(getIcon(data, label.getWidth(),label.getHeight()));
				}
			}).start();
			label_1.setText(rs.getString("pname"));
			label_2.setText(rs.getString("description"));
			label_3.setText("판매지: "+rs.getString("aname")+"-"+rs.getString("sname"));
			loc = rs.getString("aname")+"-"+rs.getString("sname");
			label_4.setText("분류: "+rs.getString("cname"));
			label_5.setText(String.format("가격: %,d원", price = rs.getInt("price")));
			label_6.setText(String.format("평점: %.1f점", rs.getDouble("st")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from product right join `order` using(pno) right join review using(ono) left join user using(uno) where pno = ?", pno)) {
			int i = 0;
			while(rs.next()) {
				var pp = new F_패널(rs.getString("uname"), rs.getInt("star"), rs.getString("review"));
				pp.setLocation(0, (128*2/3+5)*i);
				panel_2.add(pp);
				i++;
			}
			panel_2.setPreferredSize(new Dimension(0, (128*2/3+5)*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var r = JOptionPane.showInputDialog(null,  "수량을 입력해주세요.", "질문", 3);
			if(r==null) return;
			try {
				int cnt = Integer.parseInt(r);
				int point = getpoint();
				if(cnt*price > point) {
					msgErr("포인트가 부족합니다.");
					return;
				}
				DB.update("user", "point = point -?", "uno = ?", price  * cnt, User.uno);
				DB.insert("`order`", 0,pno,User.uno,LocalDate.now(),cnt);
				msgInfo("구매가 완료되었습니다.");
				msgInfo(String.format("%s에서 %s(고객님의 위치)로 배송 시작합니다.", loc, getLoc()));
				showPage(new G_배송());
			} catch (NumberFormatException e1) {
				msgErr("1 이상의 숫자를 입력해주세요.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		private String getLoc() {
			try (var rs = DB.res("select * from user join sub_area(sno) join area using(ano) where uno = ?", User.uno)) {
				rs.next();
				return rs.getString("aname")+"-"+rs.getString("sname");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return null;
		}

		private int getpoint() {
			try (var rs = DB.res("select * from user where uno = ?", User.uno)) {
				rs.next();
				return rs.getInt("point");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return 0;
		}
	}
}
