import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.time.LocalDate;

import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class D_결제 extends BF {
	private JLabel label;
	private JLabel label_1;
	private JPanel panel;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JSeparator separator;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	private JSeparator separator_1;
	private JLabel label_12;
	private JLabel label_13;
	private JLabel label_14;
	private JLabel label_15;
	private JPanel panel_1;
	private JLabel label_16;
	private JLabel lblMonth;
	private JButton button;
	private JLabel label_18;
	private JLabel label_19;
	private JLabel label_20;
	private JLabel label_21;
	private JLabel label_22;
	private JLabel label_23;
	private JLabel label_24;
	private JLabel label_25;
	private JLabel label_26;
	private JLabel label_27;
	private JLabel label_28;

	PayInfo info;
	public D_결제(PayInfo payInfo) {
		info = payInfo;
		getContentPane().setBackground(new Color(236, 244, 234));
		setTitle("결제");
		setBounds(100, 100, 415, 687);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("결제 상세 내역");
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setForeground(new Color(0, 128, 0));
		label.setBounds(12, 10, 226, 35);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(12, 51, 179, 21);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 82, 377, 403);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_2 = new JLabel("▶ 기기 정보");
		label_2.setFont(new Font("굴림", Font.PLAIN, 14));
		label_2.setForeground(new Color(0, 128, 0));
		label_2.setBounds(12, 10, 195, 22);
		panel.add(label_2);
		
		label_3 = new JLabel("기종");
		label_3.setBounds(12, 42, 84, 15);
		panel.add(label_3);
		
		label_4 = new JLabel("선택 용량");
		label_4.setBounds(12, 67, 84, 15);
		panel.add(label_4);
		
		label_5 = new JLabel("통신사");
		label_5.setBounds(12, 92, 84, 15);
		panel.add(label_5);
		
		label_6 = new JLabel("할부 기간");
		label_6.setBounds(12, 117, 84, 15);
		panel.add(label_6);
		
		label_7 = new JLabel("개봉일");
		label_7.setBounds(12, 139, 84, 15);
		panel.add(label_7);
		
		label_8 = new JLabel("약정 종료일");
		label_8.setBounds(12, 164, 84, 15);
		panel.add(label_8);
		
		separator = new JSeparator();
		separator.setBounds(12, 189, 353, 2);
		panel.add(separator);
		
		label_9 = new JLabel("▶ 기기 정보");
		label_9.setForeground(new Color(0, 128, 0));
		label_9.setFont(new Font("굴림", Font.PLAIN, 14));
		label_9.setBounds(12, 201, 195, 22);
		panel.add(label_9);
		
		label_10 = new JLabel("선택 요금제");
		label_10.setBounds(12, 233, 84, 15);
		panel.add(label_10);
		
		label_11 = new JLabel("요금제 금액");
		label_11.setBounds(12, 258, 84, 15);
		panel.add(label_11);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(12, 283, 353, 2);
		panel.add(separator_1);
		
		label_12 = new JLabel("▶ 기기 정보");
		label_12.setForeground(new Color(0, 128, 0));
		label_12.setFont(new Font("굴림", Font.PLAIN, 14));
		label_12.setBounds(12, 295, 195, 22);
		panel.add(label_12);
		
		label_13 = new JLabel("용량 가격");
		label_13.setBounds(12, 327, 84, 15);
		panel.add(label_13);
		
		label_14 = new JLabel("통신사 가격");
		label_14.setBounds(12, 352, 84, 15);
		panel.add(label_14);
		
		label_15 = new JLabel("단말기 총 출고가");
		label_15.setFont(new Font("굴림", Font.BOLD, 12));
		label_15.setForeground(new Color(0, 128, 0));
		label_15.setBounds(12, 377, 208, 15);
		panel.add(label_15);
		
		label_18 = new JLabel("기종");
		label_18.setFont(new Font("Dialog", Font.BOLD, 13));
		label_18.setHorizontalAlignment(SwingConstants.RIGHT);
		label_18.setBounds(157, 42, 208, 15);
		panel.add(label_18);
		
		label_19 = new JLabel("선택 용량");
		label_19.setFont(new Font("Dialog", Font.BOLD, 13));
		label_19.setHorizontalAlignment(SwingConstants.RIGHT);
		label_19.setBounds(157, 67, 208, 15);
		panel.add(label_19);
		
		label_20 = new JLabel("통신사");
		label_20.setFont(new Font("Dialog", Font.BOLD, 13));
		label_20.setHorizontalAlignment(SwingConstants.RIGHT);
		label_20.setBounds(157, 92, 208, 15);
		panel.add(label_20);
		
		label_21 = new JLabel("할부 기간");
		label_21.setFont(new Font("Dialog", Font.BOLD, 13));
		label_21.setHorizontalAlignment(SwingConstants.RIGHT);
		label_21.setBounds(157, 117, 208, 15);
		panel.add(label_21);
		
		label_22 = new JLabel("개봉일");
		label_22.setFont(new Font("Dialog", Font.BOLD, 13));
		label_22.setHorizontalAlignment(SwingConstants.RIGHT);
		label_22.setBounds(157, 139, 208, 15);
		panel.add(label_22);
		
		label_23 = new JLabel("약정 종료일");
		label_23.setFont(new Font("Dialog", Font.BOLD, 13));
		label_23.setHorizontalAlignment(SwingConstants.RIGHT);
		label_23.setBounds(157, 164, 208, 15);
		panel.add(label_23);
		
		label_24 = new JLabel("선택 요금제");
		label_24.setFont(new Font("Dialog", Font.BOLD, 13));
		label_24.setHorizontalAlignment(SwingConstants.RIGHT);
		label_24.setBounds(157, 233, 208, 15);
		panel.add(label_24);
		
		label_25 = new JLabel("요금제 금액");
		label_25.setFont(new Font("Dialog", Font.BOLD, 13));
		label_25.setHorizontalAlignment(SwingConstants.RIGHT);
		label_25.setBounds(157, 258, 208, 15);
		panel.add(label_25);
		
		label_26 = new JLabel("용량 가격");
		label_26.setFont(new Font("Dialog", Font.BOLD, 13));
		label_26.setHorizontalAlignment(SwingConstants.RIGHT);
		label_26.setBounds(157, 327, 208, 15);
		panel.add(label_26);
		
		label_27 = new JLabel("통신사 가격");
		label_27.setFont(new Font("Dialog", Font.BOLD, 13));
		label_27.setHorizontalAlignment(SwingConstants.RIGHT);
		label_27.setBounds(157, 352, 208, 15);
		panel.add(label_27);
		
		label_28 = new JLabel("단말기 총 출고가");
		label_28.setHorizontalAlignment(SwingConstants.RIGHT);
		label_28.setForeground(new Color(0, 128, 0));
		label_28.setFont(new Font("Dialog", Font.BOLD, 13));
		label_28.setBounds(157, 377, 208, 15);
		panel.add(label_28);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 128, 0));
		panel_1.setBounds(12, 495, 377, 92);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_16 = new JLabel("월 납부금(단말 할부 + 요금제)");
		label_16.setForeground(Color.WHITE);
		label_16.setBounds(12, 10, 262, 15);
		panel_1.add(label_16);
		
		lblMonth = new JLabel("");
		lblMonth.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		lblMonth.setForeground(Color.WHITE);
		lblMonth.setBounds(12, 35, 262, 47);
		panel_1.add(lblMonth);
		
		button = new JButton("결제하기");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(12, 597, 377, 47);
		getContentPane().add(button);
		
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void load() throws Exception {
		label_1.setText(LocalDate.now()+" 기준");
		String name = DB.select("select pname from product where pno = ?", String.class, info.pno);
		label_18.setText(name);;
		label_19.setText(info.capName);
		label_20.setText(info.servieNmae);
		label_21.setText(info.install);
		int month = Integer.parseInt(info.install.trim());
		LocalDate baseTime = LocalDate.now();
		label_22.setText(baseTime.toString());
		LocalDate endTime =baseTime.plusMonths(month);
		label_23.setText(endTime.toString());
		var rname = DB.select("select rname from rateplan where rno = ?", String.class, info.rno);
		label_24.setText(rname);
		int price = DB.select("select price from rateplan where rno = ?", Integer.class, info.rno);
		label_25.setText(String.format("%,d원 / 월", price));
		label_26.setText(String.format("%,d원", info.cprice));
		label_27.setText(String.format("%,d원", info.sprice));
		int tot = info.sprice+info.cprice;
		label_28.setText(String.format("%,d원", tot));
		lblMonth.setText(String.format("%,d원 / 월", tot/month+price));
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try (var rs = DB.res("select *, price / mprice / 10  m from orders where uno = ? order by opening_date desc limit 1;", User.uno)) {
				int price = DB.select("select price from rateplan where rno = ?", Integer.class, info.rno);
				if(rs.next()) {
					LocalDate now = LocalDate.now();
					LocalDate open = rs.getDate("opening_date").toLocalDate().plusMonths(getMonths(rs.getInt("m")));
					if(now.isBefore(open)) {
						msgErr("현재약정일이 종료되고 난 후 다시 구매해주세요.");
					}
					else {
						DB.insert("orders", 0,info.sprice,info.cprice,info.sprice+info.cprice+price,(info.sprice+info.cprice)/Integer.parseInt(info.install)+price,LocalDate.now(),User.uno,info.pno,info.rno);
						msgInfo("결제 완료");
						showPage(A_메인.class);
					}
				}
				else {
					DB.insert("orders", 0,info.sprice,info.cprice,info.sprice+info.cprice+price,(info.sprice+info.cprice)/Integer.parseInt(info.install)+price,LocalDate.now(),User.uno,info.pno,info.rno);
					msgInfo("결제 완료");
					showPage(A_메인.class);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}

		private long getMonths(int mcode) {
			return mcode==1? 12 : mcode==2?  24: mcode == 3? 36 : 48;
		}
	}
}
