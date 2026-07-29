import java.awt.EventQueue;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Iterator;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSeparator;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class D_노선스케줄 extends BF {

	/**
	 * Create the frame.
	 * @param end 
	 * @param start 
	 */
	Station start,end;
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
		setTitle("노선 스케줄");
		this.start = start;
		this.end = end;
		setBounds(100, 100, 450, 644);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel();
		label.setIcon(getIcon("logo.png",130,40));
		label.setBounds(12, 10, 134, 40);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("굴림", Font.BOLD, 15));
		label_1.setBounds(160, 10, 255, 40);
		getContentPane().add(label_1);
		
		button = new RoundButton("시간 변경");
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(34, 44, 215));
		button.setBounds(12, 55, 97, 23);
		getContentPane().add(button);
		
		label_2 = new JLabel("New label");
		label_2.setBounds(121, 55, 189, 18);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setBounds(17, 85, 398, 44);
		getContentPane().add(label_3);
		
		separator = new JSeparator();
		separator.setBounds(10, 136, 410, 4);
		getContentPane().add(separator);
		
		panel = new JPanel();
		panel.setBounds(9, 148, 415, 445);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 442, 445);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	LocalDateTime basetime = LocalDateTime.now();
	private void load() {
		label_2.setText(basetime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd/HH:mm")));
		label_1.setText(start.name+ " → "+end.name);
		RouteInfo routeInfo = RouteService.getRouteInfo(start, end);
		label_3.setText(getRoute(routeInfo.route));
		
		addPanel(routeInfo);
	}
	private void addPanel(RouteInfo routeinfo) {
		panel_1.removeAll();
		var time = LocalTime.of(06, 00);
		int w = 410, h =116, i= 0;
		while(!time.isAfter(LocalTime.of(23, 00))) {
			if(time.isBefore(basetime.toLocalTime())) {
				time = time.plusMinutes(30);
				continue;
			}
			int spanTime = (int) (Math.round(routeinfo.dist*0.1)/2.0*3);
			String data = getData(routeinfo.route);
			var pp = new D_패널(start.name, end.name, time, spanTime, data);
			pp.setLocation(0, (h+12)*i);
			final LocalTime ft = time;
			pp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					showpage(new E_결제(start, end, basetime.toLocalDate(), ft));
				}
			});
			panel_1.add(pp);
			i++;
			time = time.plusMinutes(30);
		}
		panel_1.setPreferredSize(new Dimension(0, 128*i));
		panel_1.revalidate();
		panel_1.repaint();
	}
	
	private String getData(List<Station> route) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				if(sb.toString().length()>0)sb.append("→");
				sb.append(String.format("[%s→%s]", f.line.substring(0,2),t.line.substring(0, 2)));
			}
		}
		return sb.toString();
	}
	private String getRoute(List<Station> route) {
		StringBuilder sb = new StringBuilder("<html>"+route.get(0).name);
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				sb.append(String.format(" > [%s→%s환승] ", f.line.substring(0, 2), t.line.substring(0,2)));
			}
			sb.append(String.format(" > %s", t.name));
		}
		
		return sb.toString();
	}
}
