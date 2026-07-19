import java.awt.EventQueue;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSeparator;

import com.mysql.cj.xdevapi.Result;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class D_노선스케줄 extends BF {

	Station start, end;
	public JLabel label;
	public JLabel label_1;
	public JButton button;
	public JLabel label_2;
	public JLabel label_3;
	public JSeparator separator;
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	
	public D_노선스케줄(Station start, Station end) {
		this.start = start;
		this.end = end;
		setTitle("노선 스케줄");
		setBounds(100, 100, 463, 662);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel(getIcon("logo.png",140,45));
		label.setBounds(12, 10, 145, 49);
		getContentPane().add(label);
		
		label_1 = new JLabel(start.name+" "+end.name);
		label_1.setBounds(169, 18, 266, 32);
		getContentPane().add(label_1);
		
		button = new RoundButton("시간 변경");
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(33, 107, 245));
		button.setBounds(12, 64, 140, 23);
		getContentPane().add(button);
		
		label_2 = new JLabel("New label");
		label_2.setBounds(164, 64, 185, 23);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setBounds(12, 97, 423, 39);
		getContentPane().add(label_3);
		
		separator = new JSeparator();
		separator.setBounds(12, 142, 423, 14);
		getContentPane().add(separator);
		
		panel = new JPanel();
		panel.setBounds(12, 146, 423, 467);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 1, 452, 466);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		load();
		load2();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	private void load2() {
		panel_1.removeAll();
		var time = LocalTime.of(6, 00);
		int i = 0;
		while(time.isBefore(LocalTime.of(23, 00))) {
			if(time.isBefore(LocalTime.now())) {
				time = time.plusMinutes(15);
				continue;
			}
			var pp = new D_패널(start, end, time);
			final LocalTime current = time;
			pp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					showPage(new E_결제(start, end, target.toLocalDate().atTime(current)));
				}
			});
			pp.setLocation(0, 160*i);
			panel_1.add(pp);
			time = time.plusMinutes(15);
			i++;
		}
		panel_1.setPreferredSize(new Dimension(0, 160*i));
		panel_1.revalidate();
		panel_1.repaint();
	}
	LocalDateTime target = LocalDateTime.now();
	
	private void load() {
		label_2.setText(target.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm")));
		label_3.setText(getRouteText(start, end));
	}

	private String getRouteText(Station start, Station end) {
		var route = RouteService.getRouteInfo(start, end).route;
		StringBuilder sb = new StringBuilder("<html>"+route.get(0).name);
		for (int i = 0; i < route.size()-1; i++) {
			sb.append(" > ");
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				sb.append(String.format(" [%s→%s환승] >", f.line.substring(0,2), t.line.substring(0,2)));
			}
			sb.append(t.name);
		}
		return sb.toString();
	}
}
