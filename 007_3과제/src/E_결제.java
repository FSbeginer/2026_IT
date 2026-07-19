import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class E_결제 extends BF {

	List<Integer> ctnos = new ArrayList<Integer>();
	public JLabel label;
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JSeparator separator;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JLabel label_10;
	public JLabel label_11;
	public JLabel label_12;
	public JLabel label_13;
	public JLabel label_14;
	public JLabel label_15;
	public JLabel label_16;
	public JLabel label_17;
	public JButton button;
	int discount = 0;
	
	public E_결제(List<Integer> list,int discount) {
		this.discount = discount;
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		
		label = new JLabel("결제하기");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setBounds(20, 12, 165, 42);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(20, 62, 553, 422);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 581, 422);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_2.setBounds(583, 62, 323, 422);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		label_1 = new JLabel("주문 정보");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) Color.LIGHT_GRAY));
		label_1.setBounds(20, 12, 275, 31);
		panel_2.add(label_1);
		
		label_2 = new JLabel("주문자");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_2.setBounds(30, 58, 51, 15);
		panel_2.add(label_2);
		
		label_3 = new JLabel("연락처");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_3.setBounds(30, 92, 51, 15);
		panel_2.add(label_3);
		
		label_4 = new JLabel("보유 포인트");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_4.setBounds(30, 119, 76, 15);
		panel_2.add(label_4);
		
		separator = new JSeparator();
		separator.setBounds(22, 159, 275, 9);
		panel_2.add(separator);
		
		label_5 = new JLabel("적립 예정 포인트");
		label_5.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_5.setBounds(25, 228, 108, 15);
		panel_2.add(label_5);
		
		label_6 = new JLabel("할인 금액");
		label_6.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_6.setBounds(28, 195, 73, 15);
		panel_2.add(label_6);
		
		label_7 = new JLabel("총 상품 금액");
		label_7.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_7.setBounds(25, 167, 89, 15);
		panel_2.add(label_7);
		
		label_8 = new JLabel("최종 결제 금액");
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_8.setBorder(null);
		label_8.setBounds(20, 255, 275, 31);
		panel_2.add(label_8);
		
		label_9 = new JLabel("New label");
		label_9.setOpaque(true);
		label_9.setBorder(new LineBorder(Color.BLUE, 2));
		label_9.setBackground(new Color(225, 231, 253));
		label_9.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(20, 298, 280, 36);
		panel_2.add(label_9);
		
		label_10 = new JLabel("결제 후 포인트");
		label_10.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_10.setBounds(20, 346, 108, 15);
		panel_2.add(label_10);
		
		label_11 = new JLabel("New label");
		label_11.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_11.setBounds(159, 55, 137, 22);
		panel_2.add(label_11);
		
		label_12 = new JLabel("New label");
		label_12.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_12.setBounds(159, 85, 137, 22);
		panel_2.add(label_12);
		
		label_13 = new JLabel("New label");
		label_13.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_13.setBounds(158, 112, 137, 22);
		panel_2.add(label_13);
		
		label_14 = new JLabel("New label");
		label_14.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_14.setBounds(153, 165, 137, 22);
		panel_2.add(label_14);
		
		label_15 = new JLabel("New label");
		label_15.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_15.setBounds(153, 193, 137, 22);
		panel_2.add(label_15);
		
		label_16 = new JLabel("New label");
		label_16.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_16.setBounds(152, 224, 137, 22);
		panel_2.add(label_16);
		
		label_17 = new JLabel("New label");
		label_17.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_17.setBounds(150, 340, 137, 22);
		panel_2.add(label_17);
		
		button = new RoundButton("결제");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(113, 116, 221));
		button.setBounds(24, 374, 266, 31);
		panel_2.add(button);
		setTitle("결제");
		ctnos = list;
		setBounds(100, 100, 938, 535);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		load();
	}

	int sum;
	private int uprice;
	private int plus;
	private void load() {
		try (var rs = DB.res("select * from cart join product using(pno) where ctno in ("+String.join(",",ctnos.stream().map(x->x+"").collect(Collectors.toList()))+")")) {
			int i = 0;
			while(rs.next()) {
				var pp =new E_패널(getIcon("product/"+rs.getInt("pno")+".png",80,80), rs.getString("pname"), rs.getInt("ctcount"), rs.getInt("pprice"));
				pp.setLocation(10, 10+90*i);
				sum += rs.getInt("pprice")*rs.getInt("ctcount");
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0, 10+90*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
			rs.next();
			label_11.setText(rs.getString("uname"));
			label_12.setText(rs.getString("unumber"));
			label_13.setText(String.format("%,dP",rs.getInt("uprice")));
			label_14.setText(String.format("%,d원", sum));
			label_15.setText(String.format("-%,d원", discount));
			label_9.setText(String.format("%,d원", sum-discount));
			label_16.setText(String.format("+%,dP", plus =(int)((sum-discount)*0.04)));
			uprice = rs.getInt("uprice");
			label_17.setText(String.format("%,dP", uprice-(sum-discount)+plus));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}	
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
				rs.next();
				uprice = rs.getInt("uprice");
				if(uprice<(sum-discount)) {
					msgErr("포인트가 부족합니다.");
				}
				else {
					DB.update("user", "uprice = ?", "uno = ?", uprice-(sum-discount)+plus, User.uno);
					for (var ctno : ctnos) {
						var product = DB.res("select * from cart join product using(pno) where ctno = ?",ctno);
						product.next();
						DB.insert("orders", 0,product.getInt("pno"),User.uno,product.getInt("ctcount"),LocalDate.now(),product.getInt("pprice"), 0);
						DB.update("product", "pcount = pcount - ?", "pno = ?", product.getInt("ctcount"),product.getInt("pno"));
					}
					DB.delete("cart", "ctno in ("+String.join(",",ctnos.stream().map(x->x+"").collect(Collectors.toList()))+")", null);
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
}
