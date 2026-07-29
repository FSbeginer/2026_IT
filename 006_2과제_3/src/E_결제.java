import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.sql.SQLException;

import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.WindowEvent;

public class E_결제 extends BF {

	/**
	 * Create the frame.
	 * 
	 * @param localDateTime
	 * @param end
	 * @param start
	 */
	Station start, end;
	LocalDate time;
	public JPanel panel;
	public JLabel label;
	public JSeparator separator;
	public JLabel label_1;
	public JLabel lblStart;
	public JLabel lblEnd;
	public JLabel label_4;
	public JLabel lblCost;
	public JSeparator separator_1;
	public JSeparator separator_2;
	public JSeparator separator_3;
	public JSeparator separator_4;
	public JSeparator separator_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel lblDate;
	public JLabel lblTime;
	public JLabel label_10;
	public JLabel lblPrice;
	public JLabel lblPoint;
	public JLabel label_13;
	public JLabel label_14;
	public JLabel lblLeft;
	public JPanel panel_1;
	public JPanel panel_2;
	LocalTime time2;
	public E_결제(Station start, Station end, LocalDate localDate, LocalTime localtime) {
		addWindowListener(new ThisWindowListener());
		setTitle("결제");
		this.start = start;
		this.end = end;
		time = localDate;
		time2 = localtime;
		setBounds(100, 100, 289, 557);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new RoundPanel(1f);
		panel.setBackground(new Color(34, 44, 215));
		panel.setBounds(12, 31, 249, 477);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("결제 정보");
		label.setFont(new Font("굴림", Font.PLAIN, 15));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(12, 10, 74, 28);
		panel.add(label);

		separator = new JSeparator();
		separator.setBounds(12, 47, 225, 7);
		panel.add(separator);

		label_1 = new JLabel("예매 구간");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("굴림", Font.PLAIN, 12));
		label_1.setBounds(12, 59, 74, 18);
		panel.add(label_1);

		lblStart = new JLabel("New label");
		lblStart.setForeground(Color.WHITE);
		lblStart.setFont(new Font("굴림", Font.PLAIN, 14));
		lblStart.setBounds(12, 86, 95, 18);
		panel.add(lblStart);

		lblEnd = new JLabel("New label");
		lblEnd.setForeground(Color.WHITE);
		lblEnd.setFont(new Font("굴림", Font.PLAIN, 14));
		lblEnd.setBounds(142, 86, 95, 18);
		panel.add(lblEnd);

		label_4 = new JLabel("→");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.WHITE);
		label_4.setBounds(103, 88, 27, 16);
		panel.add(label_4);

		lblCost = new JLabel("New label");
		lblCost.setForeground(Color.WHITE);
		lblCost.setFont(new Font("굴림", Font.PLAIN, 12));
		lblCost.setBounds(12, 106, 74, 18);
		panel.add(lblCost);

		separator_1 = new JSeparator();
		separator_1.setBounds(12, 134, 225, 7);
		panel.add(separator_1);

		separator_2 = new JSeparator();
		separator_2.setBounds(12, 214, 225, 7);
		panel.add(separator_2);

		separator_3 = new JSeparator();
		separator_3.setBounds(12, 300, 225, 7);
		panel.add(separator_3);

		separator_4 = new JSeparator();
		separator_4.setBounds(12, 353, 225, 7);
		panel.add(separator_4);

		separator_5 = new JSeparator();
		separator_5.setBounds(12, 408, 225, 7);
		panel.add(separator_5);

		label_6 = new JLabel(getIcon("icon/trains.png", 115, 40));
		label_6.setBounds(12, 425, 118, 42);
		panel.add(label_6);

		label_7 = new JLabel("탑승 시간");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("굴림", Font.PLAIN, 12));
		label_7.setBounds(12, 142, 74, 18);
		panel.add(label_7);

		lblDate = new JLabel("New label");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("굴림", Font.PLAIN, 12));
		lblDate.setBounds(13, 167, 117, 18);
		panel.add(lblDate);

		lblTime = new JLabel("New label");
		lblTime.setForeground(Color.WHITE);
		lblTime.setFont(new Font("굴림", Font.PLAIN, 12));
		lblTime.setBounds(12, 192, 117, 18);
		panel.add(lblTime);

		label_10 = new JLabel("총 결제 금액");
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("굴림", Font.PLAIN, 12));
		label_10.setBounds(13, 226, 117, 18);
		panel.add(label_10);

		lblPrice = new JLabel("New label");
		lblPrice.setForeground(Color.WHITE);
		lblPrice.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblPrice.setBounds(14, 253, 141, 31);
		panel.add(lblPrice);

		lblPoint = new JLabel("New label");
		lblPoint.setForeground(Color.WHITE);
		lblPoint.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblPoint.setBounds(18, 325, 141, 31);
		panel.add(lblPoint);

		label_13 = new JLabel("보유 금액");
		label_13.setForeground(Color.WHITE);
		label_13.setFont(new Font("굴림", Font.PLAIN, 12));
		label_13.setBounds(17, 307, 117, 18);
		panel.add(label_13);

		label_14 = new JLabel("결제 후 잔액");
		label_14.setForeground(Color.WHITE);
		label_14.setFont(new Font("굴림", Font.PLAIN, 12));
		label_14.setBounds(13, 358, 117, 18);
		panel.add(label_14);

		lblLeft = new JLabel("New label");
		lblLeft.setForeground(Color.WHITE);
		lblLeft.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		lblLeft.setBounds(14, 376, 141, 31);
		panel.add(lblLeft);

		panel_1 = new JPanel();
		panel_1.setBackground(Color.BLACK);
		panel_1.setBounds(175, 10, 24, 5);
		panel.add(panel_1);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(175, 17, 24, 10);
		panel.add(panel_2);

		load();
	}

	int price;
	private Timer timer;

	private void load() {
		var routeInfo = RouteService.getRouteInfo(start, end);
		lblStart.setText(start.name);
		lblEnd.setText(end.name);
		lblCost.setText(routeInfo.cost + "개 구간");
		lblDate.setText(time.toString());
		lblTime.setText(time2.format(DateTimeFormatter.ofPattern("HH:mm")));
		price = routeInfo.cost * 500;
		try {
			User user = DB.getUser(User.uno);
			int age = LocalDate.now().getYear() - user.birth.getYear();
			if (LocalDate.now().isBefore(user.birth.plusYears(age))) {
				age--;
			}
			if (age >= 12 && age <= 19) {
				price /= 2;
			} else if (age >= 65)
				price = (int) (price * 0.8);
			if (LocalDate.now().equals(user.birth.plusYears(age))) {
				price = (int) (price * 0.75);
			}
			lblPoint.setText(String.format("%,d원", user.price));
			lblLeft.setText(String.format("%,d원", user.price - price));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		lblPrice.setText(String.format("%,d원", price));
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					timer.stop();
					User user = DB.getUser(User.uno);
					if(user.price < price){
						int r = JOptionPane.showConfirmDialog(null, "결제 금액이 부족합니다.\n충전하러 가시겠습니까?", "잔액 부족", 0, 0);
						if(r==0) {
							var g  = new G_충전();
							g.addWindowListener(new WindowAdapter() {
								public void windowClosed(java.awt.event.WindowEvent e) {
									load();
									timer.start();
								};
							});
							showpage(g);
						}
						else {
							dispose();
							showPage(A_메인.class);
						}
					}
					else {
						DB.update("user", "price=price-?", "uno = ?", price,User.uno);
						DB.insert("reservation", 0,start.sno,end.sno,time,User.uno);
						msgInfo("결제 되었습니다.");
						dispose();
						showPage(A_메인.class);
					}
				} catch (SQLException e2) {
					e2.printStackTrace();
				}
			}
		});
		timer.start();
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if(timer!=null&&timer.isRunning()) timer.stop();
			showPage(A_메인.class);
		}
	}
}
