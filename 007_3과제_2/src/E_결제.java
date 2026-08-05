import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.border.MatteBorder;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class E_결제 extends BF {


	List<PayInfo> list;
	int cpno;
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JScrollPane scrollPane;
	public JPanel panel_3;
	public JLabel label_1;
	public JPanel panel_4;
	public JLabel label_2;
	public JLabel lblUser;
	public JLabel label_5;
	public JLabel lblPhone;
	public JLabel label_7;
	public JLabel lblPoint;
	public JPanel panel_5;
	public JLabel label_4;
	public JLabel lblTot;
	public JLabel label_10;
	public JLabel lblSale;
	public JLabel label_12;
	public JLabel lblPlusPoint;
	public JLabel label_3;
	public JLabel lblFinal;
	public JLabel label_8;
	public JLabel lblLeft;
	public JButton button;
	private int sale;
	private int sum;
	
	public E_결제(List<PayInfo> list, int cpno) {
		this.cpno = cpno;
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		
		label = new JLabel("결제하기");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label.setBounds(10, 12, 178, 51);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(230, 230, 230)));
		panel.setBounds(10, 64, 643, 453);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(10, 12, 582, 441);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 611, 441);
		panel_2.add(scrollPane);
		
		panel_3 = new JPanel();
		scrollPane.setViewportView(panel_3);
		panel_3.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(230, 230, 230)));
		panel_1.setBounds(663, 64, 387, 453);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel(" 주문 정보");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label_1.setBorder(null);
		label_1.setBounds(10, 12, 351, 39);
		panel_1.add(label_1);
		
		panel_4 = new JPanel();
		panel_4.setBorder(new MatteBorder(1, 0, 1, 0, (Color) new Color(230, 230, 230)));
		panel_4.setBounds(14, 56, 356, 105);
		panel_1.add(panel_4);
		panel_4.setLayout(new GridLayout(0, 2, 0, 0));
		
		label_2 = new JLabel("주문자");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		panel_4.add(label_2);
		
		lblUser = new JLabel("New label");
		lblUser.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel_4.add(lblUser);
		
		label_5 = new JLabel("연락처");
		label_5.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		panel_4.add(label_5);
		
		lblPhone = new JLabel("New label");
		lblPhone.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel_4.add(lblPhone);
		
		label_7 = new JLabel("보유포인트");
		label_7.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		panel_4.add(label_7);
		
		lblPoint = new JLabel("New label");
		lblPoint.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel_4.add(lblPoint);
		
		panel_5 = new JPanel();
		panel_5.setBounds(11, 167, 356, 105);
		panel_1.add(panel_5);
		panel_5.setLayout(new GridLayout(0, 2, 0, 0));
		
		label_4 = new JLabel("총 상품 금액");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		panel_5.add(label_4);
		
		lblTot = new JLabel("New label");
		lblTot.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTot.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel_5.add(lblTot);
		
		label_10 = new JLabel("할인 금액");
		label_10.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		panel_5.add(label_10);
		
		lblSale = new JLabel("New label");
		lblSale.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSale.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel_5.add(lblSale);
		
		label_12 = new JLabel("적립 예정 포인트");
		label_12.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		panel_5.add(label_12);
		
		lblPlusPoint = new JLabel("New label");
		lblPlusPoint.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPlusPoint.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		panel_5.add(lblPlusPoint);
		
		label_3 = new JLabel("최종 결제 금액");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_3.setBorder(null);
		label_3.setBounds(10, 272, 351, 39);
		panel_1.add(label_3);
		
		lblFinal = new JLabel("New label");
		lblFinal.setOpaque(true);
		lblFinal.setBorder(new LineBorder(new Color(91, 98, 227), 2));
		lblFinal.setBackground(new Color(244, 247, 255));
		lblFinal.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblFinal.setHorizontalAlignment(SwingConstants.CENTER);
		lblFinal.setBounds(14, 323, 354, 48);
		panel_1.add(lblFinal);
		
		label_8 = new JLabel("결제 후 포인트");
		label_8.setBounds(17, 379, 130, 22);
		panel_1.add(label_8);
		
		lblLeft = new JLabel("New label");
		lblLeft.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		lblLeft.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLeft.setBounds(207, 377, 164, 28);
		panel_1.add(lblLeft);
		
		button = new RoundButton("결제");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(91, 98, 227));
		button.setBounds(11, 406, 364, 39);
		panel_1.add(button);
		setTitle("결제");
		this.list = list;
		setBounds(100, 100, 1076, 568);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	private void load() {
		int w = 582, h= 82, i= 0;
		for (var info : list) {
			var pp =new E_패널(info);
			pp.setLocation(0, 87*i);
			panel_3.add(pp);
			i++;
		}
		panel_3.setPreferredSize(new Dimension(0,87*i));
		
		sum = list.stream().mapToInt(x->x.cnt*x.price).sum();
		sale = 0;
		if(cpno!=-1) {
			try {
				var rs =DB.res("select * from coupon join reward using(reno) where cpno = ?",cpno);
				rs.next();
				double reward = rs.getDouble("resale");
				sale = (int) (sum * reward);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
			rs.next();
			lblUser.setText(rs.getString("uname"));
			lblPhone.setText(rs.getString("unumber"));
			lblPoint.setText(String.format("%,dP", rs.getInt("uprice")));
			lblTot.setText(String.format("%,d원", sum));
			lblSale.setText(String.format("-%,d원", sale));
			lblPlusPoint.setText(String.format("+%,dP", (int)((sum-sale)*0.04)));
			lblFinal.setText(String.format("%,d원", sum-sale));
			lblLeft.setText(String.format("%,d원", sum-sale + (int)((sum-sale)*0.04)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				var point = DB.select("select uprice from user where uno = ?", Integer.class, User.uno);
				if(point < sum -sale) {
					msgErr("포인트가 부족합니다.");
					return;
				} else {
					for (var info : list) {
						DB.update("product", "pcount =pcount - ?", "pno = ?", info.cnt, info.pno);
						DB.delete("cart", "uno = ? and pno = ?", User.uno,info.pno);
						DB.insert("orders", 0,info.pno,User.uno,info.cnt,LocalDate.now(),info.price*info.cnt,0);
					}
					DB.update("user", "uprice = ?","uno = ?", point -(sum-sale)+((int)(sum-sale)*0.04));
					var nick = DB.select("select unick from user where uno = ?", String.class, User.uno);
					msgInfo(nick+"님 결제가 완료되었습니다.");
					dispose();
					while(!prev.isEmpty()) {
						var  p= prev.pop();
						if(p instanceof A_메인) {
							p.updateForm();
							p.setVisible(true);
							break;
						}
						else {
							p.dispose();
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
