import java.awt.Color;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class E_결제 extends BF {

	public JPanel panel;
	public JLabel label;
	public JSeparator separator;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label_1;
	public JLabel lblStart;
	public JLabel lblend;
	public JLabel label_4;
	public JLabel lblCost;
	public JSeparator separator_1;
	public JSeparator separator_2;
	public JSeparator separator_3;
	public JSeparator separator_4;
	public JSeparator separator_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JLabel label_10;
	public JLabel lblDAte;
	public JLabel lblhour;
	public JLabel lblTOt;
	public JLabel lblHaving;
	public JLabel lblleft;
	Station start, end;
	LocalDateTime time;
	private Timer timer;
	
	public E_결제(Station start, Station end, LocalDateTime time) {
		setTitle("결제");
		this.start = start;
		this.end = end;
		this.time = time;
		setBounds(100, 100, 324, 588);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new RoundPanel(1f);
		panel.setBackground(new Color(33, 107, 245));
		panel.setBounds(12, 47, 284, 492);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("결제 정보");
		label.setFont(new Font("굴림", Font.BOLD, 18));
		label.setForeground(new Color(255, 255, 255));
		label.setBounds(12, 10, 102, 27);
		panel.add(label);
		
		separator = new JSeparator();
		separator.setBounds(13, 40, 260, 9);
		panel.add(separator);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(0, 0, 0));
		panel_1.setBounds(230, 13, 31, 5);
		panel.add(panel_1);
		
		panel_2 = new JPanel();
		panel_2.setBackground(Color.BLACK);
		panel_2.setBounds(230, 21, 31, 13);
		panel.add(panel_2);
		
		label_1 = new JLabel("예매 구간");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("굴림", Font.PLAIN, 15));
		label_1.setBounds(10, 48, 79, 27);
		panel.add(label_1);
		
		lblStart = new JLabel("New label");
		lblStart.setForeground(Color.WHITE);
		lblStart.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblStart.setBounds(9, 72, 104, 27);
		panel.add(lblStart);
		
		lblend = new JLabel("New label");
		lblend.setForeground(Color.WHITE);
		lblend.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblend.setBounds(168, 72, 104, 27);
		panel.add(lblend);
		
		label_4 = new JLabel("→");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("굴림", Font.BOLD, 15));
		label_4.setBounds(111, 73, 48, 27);
		panel.add(label_4);
		
		lblCost = new JLabel("New label");
		lblCost.setForeground(Color.WHITE);
		lblCost.setFont(new Font("굴림", Font.PLAIN, 15));
		lblCost.setBounds(9, 96, 135, 27);
		panel.add(lblCost);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(11, 121, 260, 9);
		panel.add(separator_1);
		
		separator_2 = new JSeparator();
		separator_2.setBounds(10, 198, 260, 9);
		panel.add(separator_2);
		
		separator_3 = new JSeparator();
		separator_3.setBounds(10, 288, 260, 9);
		panel.add(separator_3);
		
		separator_4 = new JSeparator();
		separator_4.setBounds(13, 352, 260, 9);
		panel.add(separator_4);
		
		separator_5 = new JSeparator();
		separator_5.setBounds(13, 417, 260, 9);
		panel.add(separator_5);
		
		label_6 = new JLabel(getIcon("icon/trains.png",95,35));
		label_6.setBounds(17, 438, 99, 38);
		panel.add(label_6);
		
		label_7 = new JLabel("탑승 시간");
		label_7.setForeground(Color.WHITE);
		label_7.setFont(new Font("굴림", Font.PLAIN, 15));
		label_7.setBounds(13, 126, 79, 27);
		panel.add(label_7);
		
		label_8 = new JLabel("총 결제 금액");
		label_8.setForeground(Color.WHITE);
		label_8.setFont(new Font("굴림", Font.PLAIN, 15));
		label_8.setBounds(12, 208, 187, 27);
		panel.add(label_8);
		
		label_9 = new JLabel("보유 금액");
		label_9.setForeground(Color.WHITE);
		label_9.setFont(new Font("굴림", Font.PLAIN, 15));
		label_9.setBounds(13, 296, 95, 27);
		panel.add(label_9);
		
		label_10 = new JLabel("결제 후 잔액");
		label_10.setForeground(Color.WHITE);
		label_10.setFont(new Font("굴림", Font.PLAIN, 15));
		label_10.setBounds(13, 358, 104, 27);
		panel.add(label_10);
		
		lblDAte = new JLabel("탑승 시간");
		lblDAte.setForeground(Color.WHITE);
		lblDAte.setFont(new Font("굴림", Font.BOLD, 15));
		lblDAte.setBounds(14, 152, 136, 27);
		panel.add(lblDAte);
		
		lblhour = new JLabel("탑승 시간");
		lblhour.setForeground(Color.WHITE);
		lblhour.setFont(new Font("굴림", Font.BOLD, 15));
		lblhour.setBounds(14, 177, 138, 27);
		panel.add(lblhour);
		
		lblTOt = new JLabel("New label");
		lblTOt.setForeground(Color.WHITE);
		lblTOt.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		lblTOt.setBounds(15, 240, 193, 27);
		panel.add(lblTOt);
		
		lblHaving = new JLabel("New label");
		lblHaving.setForeground(Color.WHITE);
		lblHaving.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblHaving.setBounds(14, 325, 194, 27);
		panel.add(lblHaving);
		
		lblleft = new JLabel("New label");
		lblleft.setForeground(Color.WHITE);
		lblleft.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		lblleft.setBounds(13, 389, 195, 27);
		panel.add(lblleft);
		
		
		try {
			load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void load() throws SQLException {
		lblStart.setText(start.name);
		lblend.setText(end.name);
		var routeInfo = RouteService.getRouteInfo(start, end);
		lblCost.setText(routeInfo.cost+"개 구간");
		lblDAte.setText(time.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
		lblhour.setText(time.format(DateTimeFormatter.ofPattern("HH:mm")));
		int price = routeInfo.cost*500;
		int age =  User.getAge();
		var birth = User.getBirth();
		if(age>=12&&age<19) price = price/2;
		else if(age>=65) price = (int) (price*0.8);
		
		if(birth.plusYears(age).equals(LocalDate.now())) {
			price = (int) (price *0.75);
		}
		lblTOt.setText(String.format("%,d원", price));
		
		int having = User.getPrice();
		lblHaving.setText(String.format("%,d원", having));
		lblleft.setText(String.format("%,d원", having-price));
		final int fprice = price;
		timer = new Timer(1000, e->{
			timer.stop();
			if(having<fprice) {
				var r = JOptionPane.showConfirmDialog(null, "결제금액이 부족합니다.\n충전하러 가시겠습니까?", "잔액부족", JOptionPane.YES_NO_OPTION);
				if(r==JOptionPane.YES_OPTION) {
					showPage(new G_충전(()->{
						timer.start();
					}));
				}
				else {
					showPage(A_메인.class);
				}
				return;
			}
			paying(fprice);
		});
		timer.start();
	}

	private void paying(int price) {
		try {
			DB.update("user", "price = price - ?", "uno = ?", price, User.uno);
			DB.insert("reservation", 0, start.sno, end.sno, LocalDate.now(), User.uno);
			msgInfo("결제 되었습니다.");
			showPage(A_메인.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
