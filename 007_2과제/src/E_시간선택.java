import java.awt.EventQueue;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class E_시간선택 extends BF {
	
	int dno;
	LocalDate target;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JPanel panel_1;
	public JButton button;
	
	/**
	 * @wbp.parser.constructor
	 */
	public E_시간선택(int dno, LocalDate target) {
		setTitle("시간선택");
		this.dno = dno;
		this.target = target;
		
		setBounds(100, 100, 520, 530);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(66, 120, 210));
		panel.setBounds(0, 0, 504, 92);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("예약시간을 선택해주세요");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(116, 10, 271, 30);
		panel.add(label);
		
		label_1 = new JLabel("ㅎ");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(116, 40, 271, 30);
		panel.add(label_1);
		
		label_2 = new JLabel("");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_2.setForeground(Color.WHITE);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(158, 65, 184, 27);
		panel.add(label_2);
		
		panel_1 = new JPanel();
		panel_1.setBounds(27, 124, 448, 286);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(5, 4, 8, 8));
		
		button = new JButton("예약");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		button.setBackground(Color.ORANGE);
		button.setForeground(Color.WHITE);
		button.setBounds(130, 432, 225, 49);
		getContentPane().add(button);
		
		label_1.setText(String.format("선택한 날짜 : %s", target.toString()));
		addTime();
	}
	
	boolean change = false;
	int ono;
	public E_시간선택(int dno,int ono, LocalDate target, boolean b) {
		this(dno, target);
		this.ono = ono;
		change =b;
	}

	Map<RoundButton , LocalTime> times;
	private void addTime() {
		times = new HashMap<RoundButton, LocalTime>();
		var time = LocalTime.of(9, 00);
		while(!time.isAfter(LocalTime.of(18, 00))) {
			var pp = new RoundButton(time.toString());
			pp.addActionListener(e->{
				selectBtn(pp);
			});
			pp.setBackground(new Color(180,210,255));
			pp.setForeground(panel.getBackground());
			if(target.equals(LocalDate.now())&&time.isBefore(LocalTime.now())) {
				pp.setEnabled(false);
				pp.setBackground(Color.lightGray);
			}
			else {
				times.put(pp, time);
			}
			panel_1.add(pp);
			time = time.plusMinutes(30);
		}
	}
	LocalTime selTime;
	private void selectBtn(RoundButton pp) {
		for (var btn : times.keySet()) {
			btn.setBackground(new Color(180,210,255));
			btn.setForeground(panel.getBackground());
		}
		pp.setBackground(panel.getBackground());
		pp.setForeground(Color.red);
		selTime = times.get(pp);
		label_2.setText("선택 시간 : "+selTime);
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(selTime==null) {
				msgErr("시간을 선택해주세요.");
				return;
			}
			try {
				if(change) {
					DB.update("orders", "orderdate = ?, ordertime = ?", "ono = ?", target, selTime.format(DateTimeFormatter.ofPattern("H:mm")), ono);
					msgInfo("변경이 완료되었습니다.");
					showPage(F_마이페이지.class);
				}
				else {
					int cno = DB.select("select cno from doctor where dno =?", Integer.class, dno);
					DB.insert("orders", target,"0000-00-00",User.uno,selTime.format(DateTimeFormatter.ofPattern("H:mm")),cno,dno);
					msgInfo("예약이 완료되었습니다.");
					showPage(new F_마이페이지());
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
