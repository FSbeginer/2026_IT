import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.Point;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.ActionEvent;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

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
	 * @param pno 
	 */
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
		setBounds(100, 100, 667, 482);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 627, 208);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("");
		label.setBounds(12, 30, 191, 139);
		panel.add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(218, 10, 330, 36);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_2.setBounds(215, 56, 360, 15);
		panel.add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_3.setBounds(215, 81, 330, 15);
		panel.add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_4.setBounds(215, 106, 330, 15);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_5.setBounds(216, 140, 330, 15);
		panel.add(label_5);
		
		label_6 = new JLabel("New label");
		label_6.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_6.setBounds(215, 168, 330, 15);
		panel.add(label_6);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(12, 228, 627, 174);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_7 = new JLabel("리뷰");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_7.setBounds(12, 10, 57, 15);
		panel_1.add(label_7);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 35, 603, 129);
		panel_1.add(scrollPane);
		
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		
		button = new JButton("돌아가기");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		button.setBounds(12, 412, 308, 21);
		getContentPane().add(button);
		
		button_1 = new JButton("구매하기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		button_1.setBounds(332, 412, 308, 21);
		getContentPane().add(button_1);
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		
		load();
	}
	
	int price;
	private void load() {
		try (var rs = DB.res("select *, sum(quantity) cnt, round(avg(review.star),1) star from product left join `order` left join review using(ono) using(pno) join sub_area using(sno) join area using(ano) join category using(cno) where pno = ? group by pno ;",pno)) {
			rs.next();
			Helper.getImageWorker(rs.getBytes("img"), label.getWidth(), label.getHeight(), x->label.setIcon(new ImageIcon(x))).execute();
			label_1.setText(rs.getString("pname"));
			price = rs.getInt("price");
			label_2.setText(rs.getString("description"));
			label_3.setText(String.format("판매지 : %s-%s", rs.getString("aname"),rs.getString("sname")));
			label_4.setText(String.format("분류 : %s", rs.getString("cname")));
			label_5.setText(String.format("가격 : %,d원", rs.getInt("price")));
			label_6.setText(String.format("평점 : %.1f점", rs.getDouble("star")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from `order` join review using(ono) join user using(uno) where pno = ? order by rno",pno)) {
			int w = 577, h = 83, i =0;
			while(rs.next()) {
				var pp = new F_패널(rs.getString("uname"), rs.getString("review"));
				pp.setLocation(0, 88*i);
				panel_2.add(pp);
				i++;
			}
			panel_2.setPreferredSize(new Dimension(0,88*i));
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
			try {
				var input = JOptionPane.showInputDialog(null, "충전할 금액을 입력해주세요.", "직접입력", 1);
				if (input == null)
					return;
				int cnt = Integer.parseInt(input);
				
				int hav = DB.select("select point from user where uno = ?", Integer.class, User.uno);
				if(hav<cnt*price) {
					msgErr("포인트가 부족합니다.");
					return;
				}
				DB.update("user", "point = point - ?, chance = chance + 1", "uno = ?", price, User.uno);
				DB.insert("`order`", 0,pno,User.uno,LocalDate.now(),cnt);
				msgInfo("구매가 완료되었습니다.");
				
				String startArea =  DB.select("select aname from product join sub_area using(sno) join area using(ano) where pno = ?", String.class, pno);
				String startSubArea =  DB.select("select sname from product join sub_area using(sno) join area using(ano) where pno = ?", String.class, pno);
				String endArea =  DB.select("select aname from user join sub_area using(sno) join area using(ano) where uno = ?", String.class, User.uno);
				String endSubArea =  DB.select("select sname from user join sub_area using(sno) join area using(ano) where uno = ?", String.class, User.uno);
				msgInfo(String.format("%s-%s에서 %s-%s(고객님의 위치)로 배송 시작합니다.",startArea, startSubArea, endArea, endSubArea));
				
				showPage(new G_배송(pno));
			} catch (Exception e1) {
				msgErr("1 이상의 숫자를 입력해주세요.");
			}
		}
	}
}