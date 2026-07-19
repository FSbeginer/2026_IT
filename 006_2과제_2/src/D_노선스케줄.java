import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JPanel;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;

public class D_노선스케줄 extends BF {

	Station start, end;
	public JLabel label;
	public JButton button;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JPanel panel;
	public JPanel panel_1;
	public JScrollPane scrollPane;
	public JPanel panel_2;
	public D_노선스케줄(Station start, Station end) {
		setTitle("노선 스케줄");
		this.start= start;
		this.end = end;
		setBounds(100, 100, 450, 628);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel(getIcon("logo.png",120,50));
		label.setBounds(10, 12, 123, 50);
		getContentPane().add(label);
		
		button = new RoundButton("시간 변경");
		button.setBackground(new Color(71, 94, 218));
		button.setForeground(Color.WHITE);
		button.setBounds(10, 71, 123, 23);
		getContentPane().add(button);
		
		label_1 = new JLabel(String.format("%s → %s", start.name, end.name));
		label_1.setBounds(167, 12, 218, 36);
		getContentPane().add(label_1);
		
		label_2 = new JLabel(LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm")));
		label_2.setBounds(155, 75, 143, 19);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setBounds(10, 106, 414, 36);
		getContentPane().add(label_3);
		
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(10, 154, 414, 1);
		getContentPane().add(panel);
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 167, 414, 422);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 440, 422);
		panel_1.add(scrollPane);
		
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		load();
	}
	LocalDate date = LocalDate.now();
	private void load() {
		LocalTime time = LocalTime.of(6, 00);
		var routeInfo = RouteService.findRoute(start, end);
		int i = 0, h = 108;
		while(!time.isAfter(LocalTime.of(23, 00))) {
			if(time.isBefore(LocalTime.now())) {
				time = time.plusMinutes(15);
				continue;
			}
			int spendTime = (int) (Math.round(routeInfo.dist*0.1)/2.0*3);
			String transfer =getTransfer(routeInfo);
			var pp = new D_패널(start.name, end.name, transfer, time, time.plusMinutes(spendTime), spendTime);
			pp.setLocation(10, 10+(118)*i);
			final LocalTime st = time;
			pp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					showPage(new E_결제(start, end, routeInfo, LocalDateTime.of(date, st)));
				}
			});
			panel_2.add(pp);
			i++;
			time = time.plusMinutes(15);
		}
		panel_2.setPreferredSize(new Dimension(0,10+(118)*i));
		
		String stationinfo = getStationInfo(routeInfo);
		label_3.setText(stationinfo);
	}
	private String getStationInfo(RouteInfo routeInfo) {
		var route = routeInfo.route;
		StringBuilder sb = new StringBuilder("<html>"+route.get(0).name);
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				sb.append(String.format(" [%s→%s환승] ", f.line.subSequence(0, 2),t.line.subSequence(0, 2)));
			}
			sb.append(" > "+t.name);
		}
		return sb.toString();
	}
	private String getTransfer(RouteInfo routeInfo) {
		StringBuilder sb =  new StringBuilder();
		var route = routeInfo.route;
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				if(sb.length()>0) sb.append("→");
				sb.append(String.format("[%s→%s]", f.line.substring(0,2), t.line.substring(0, 2)));
			}
		}
		return sb.toString().isBlank()? "환승 없음" : sb.toString();
	}
}
