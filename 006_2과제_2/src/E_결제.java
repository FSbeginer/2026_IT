import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.Font;
import java.sql.Date;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class E_결제 extends BF {

	/**
	 * Create the frame.
	 * 
	 * @param time
	 * @param routeInfo
	 * @param end
	 * @param start
	 */
	Station start;
	Station end;
	RouteInfo routeInfo;
	LocalDateTime time;
	public JPanel panel;
	public JLabel label;
	public JPanel panel_1;
	public JPanel panel_2;
	public JSeparator separator;
	public JLabel label_1;
	public JLabel lblstart;
	public JLabel lblEnd;
	public JLabel label_4;
	public JLabel lblCOat;
	public JSeparator separator_1;
	public JLabel label_6;
	public JLabel lblDate;
	public JLabel lblTime;
	public JSeparator separator_2;
	public JLabel label_9;
	public JLabel lblTotal;
	public JSeparator separator_3;
	public JLabel label_11;
	public JLabel lblPoint;
	public JSeparator separator_4;
	public JLabel lblLeft;
	public JLabel label_14;
	public JLabel label_15;
	public JSeparator separator_5;
	private Timer timer;
	private int price;

	public E_결제(Station start, Station end, RouteInfo routeInfo, LocalDateTime time) {
		addWindowListener(new ThisWindowListener());
		setTitle("결제");
		this.start = start;
		this.end = end;
		this.routeInfo = routeInfo;
		this.time = time;
		setBounds(100, 100, 288, 575);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new RoundPanel(1f);
		panel.setBackground(new Color(71, 94, 218));
		panel.setBounds(10, 30, 251, 496);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("결제 정보");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(17, 16, 104, 35);
		panel.add(label);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(182, 16, 24, 6);
		panel.add(panel_1);

		panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(182, 21, 24, 11);
		panel.add(panel_2);

		separator = new JSeparator();
		separator.setBounds(10, 59, 231, 6);
		panel.add(separator);

		label_1 = new JLabel("예매 구간");
		label_1.setForeground(new Color(255, 255, 255));
		label_1.setBounds(19, 71, 57, 16);
		panel.add(label_1);

		lblstart = new JLabel("예매 구간");
		lblstart.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		lblstart.setForeground(Color.WHITE);
		lblstart.setBounds(19, 92, 82, 25);
		panel.add(lblstart);

		lblEnd = new JLabel("예매 구간");
		lblEnd.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		lblEnd.setForeground(Color.WHITE);
		lblEnd.setBounds(138, 91, 104, 25);
		panel.add(lblEnd);

		label_4 = new JLabel("→");
		label_4.setForeground(new Color(255, 255, 255));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(97, 93, 40, 23);
		panel.add(label_4);

		lblCOat = new JLabel("New label");
		lblCOat.setForeground(new Color(255, 255, 255));
		lblCOat.setBounds(17, 127, 124, 16);
		panel.add(lblCOat);

		separator_1 = new JSeparator();
		separator_1.setBounds(17, 159, 224, 6);
		panel.add(separator_1);

		label_6 = new JLabel("탑승 시간");
		label_6.setForeground(Color.WHITE);
		label_6.setBounds(20, 168, 57, 16);
		panel.add(label_6);

		lblDate = new JLabel("예매 구간");
		lblDate.setForeground(Color.WHITE);
		lblDate.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		lblDate.setBounds(20, 189, 136, 25);
		panel.add(lblDate);

		lblTime = new JLabel("New label");
		lblTime.setForeground(Color.WHITE);
		lblTime.setBounds(23, 216, 72, 16);
		panel.add(lblTime);

		separator_2 = new JSeparator();
		separator_2.setBounds(15, 239, 224, 6);
		panel.add(separator_2);

		label_9 = new JLabel("총 결제 금액");
		label_9.setForeground(Color.WHITE);
		label_9.setBounds(23, 248, 78, 16);
		panel.add(label_9);

		lblTotal = new JLabel("예매 구간");
		lblTotal.setForeground(Color.WHITE);
		lblTotal.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		lblTotal.setBounds(19, 274, 187, 25);
		panel.add(lblTotal);

		separator_3 = new JSeparator();
		separator_3.setBounds(16, 315, 224, 6);
		panel.add(separator_3);

		label_11 = new JLabel("보유 금액");
		label_11.setForeground(Color.WHITE);
		label_11.setBounds(20, 324, 57, 16);
		panel.add(label_11);

		lblPoint = new JLabel("예매 구간");
		lblPoint.setForeground(Color.WHITE);
		lblPoint.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		lblPoint.setBounds(16, 350, 190, 25);
		panel.add(lblPoint);

		separator_4 = new JSeparator();
		separator_4.setBounds(16, 386, 224, 6);
		panel.add(separator_4);

		lblLeft = new JLabel("예매 구간");
		lblLeft.setForeground(Color.WHITE);
		lblLeft.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		lblLeft.setBounds(18, 420, 188, 25);
		panel.add(lblLeft);

		label_14 = new JLabel("결제 후 잔액");
		label_14.setForeground(Color.WHITE);
		label_14.setBounds(22, 394, 184, 16);
		panel.add(label_14);

		label_15 = new JLabel(getIcon("icon/trains.png", 90, 35));
		label_15.setBounds(16, 448, 96, 36);
		panel.add(label_15);

		separator_5 = new JSeparator();
		separator_5.setBounds(12, 443, 224, 6);
		panel.add(separator_5);

		try {
			load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	User u;

	private void load() throws SQLException {
		lblstart.setText(start.name);
		lblEnd.setText(end.name);
		lblCOat.setText(routeInfo.cost + "구간");
		lblDate.setText(time.toLocalDate().toString());
		lblTime.setText(time.toLocalTime().toString());
		price = routeInfo.cost * 500;
		u = DB.getUser(User.uno);
		int age = LocalDate.now().getYear() - u.birth.getYear();
		if (LocalDate.now().isBefore(u.birth.plusYears(age))) {
			age--;
		}
		if (age >= 12 && age < 19)
			price = price / 2;
		else if (age >= 65)
			price = (int) (price * 0.8);
		if (LocalDate.now().equals(u.birth.plusYears(age))) {
			price = (int) (price * 0.75);
		}
		lblTotal.setText(String.format("%,d원", price));
		lblPoint.setText(String.format("%,d원", u.price));
		lblLeft.setText(String.format("%,d원", u.price - price));
		timer = new Timer(1000, e -> {
			timer.stop();
			try {
				u = DB.getUser(User.uno);
				if (u.price < price) {
					var r = JOptionPane.showConfirmDialog(null, "결제금액이 부족합니다.\n충전하러 가시겠습니까?", "잔액부족", 0, 0);
					if (r == 0) {
						showPage(new G_충전());
					} else {
						dispose();
					}
				}
				else {
					DB.insert("reservation", 0,start.sno, end.sno,time.toLocalDate(),User.uno);
					DB.update("user", "price = price - ?", "uno = ?", price, User.uno);
					msgInfo("결제 되었습니다.");
					dispose();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		});
		timer.start();
	}

	public void updateForm() {
		try {
			u = DB.getUser(User.uno);
			lblPoint.setText(String.format("%,d원", u.price));
			lblLeft.setText(String.format("%,d원", u.price-price));
			timer.start();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			timer.stop();
			showPage(A_메인.class);
		}
	}
}
