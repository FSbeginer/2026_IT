import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class D_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;

	Station start, end;
	LocalTime basetime;
	public D_패널(Station start, Station end, LocalTime basetime) {
		this.start = start;
		this.end = end;
		this.basetime = basetime;
		setSize(420, 150);
		setLayout(null);
		setBorder(new RoundBorder(new Color(55, 128, 150)));
		
		label = new JLabel("New label");
		label.setBounds(46, 10, 126, 24);
		add(label);
		
		label_1 = new JLabel("New label");
		label_1.setBounds(268, 10, 118, 24);
		add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setForeground(new Color(33, 107, 245));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBounds(46, 46, 126, 24);
		add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setForeground(new Color(33, 107, 245));
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_3.setBounds(268, 46, 118, 24);
		add(label_3);
		
		label_4 = new JLabel(BF.getIcon("icon/trains.png",80,40));
		label_4.setBounds(46, 93, 87, 47);
		add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(172, 93, 76, 24);
		add(label_5);
		
		label_6 = new JLabel("New label");
		label_6.setForeground(Color.WHITE);
		label_6.setOpaque(true);
		label_6.setBackground(Color.ORANGE);
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(172, 13, 76, 19);
		add(label_6);
		
		label_7 = new JLabel("→");
		label_7.setFont(new Font("맑은 고딕", Font.PLAIN, 20));
		label_7.setForeground(new Color(33, 107, 245));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(177, 54, 71, 16);
		add(label_7);
		load();
	}
	
	private void load() {
		label.setText(start.name);
		label_1.setText(end.name);
		var routeinfo = RouteService.getRouteInfo(start, end);
		var route = routeinfo.route;
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				if(sb.length()!=0) sb.append("→");
				sb.append(String.format("[%s→%s]", f.line.substring(0,2), t.line.substring(0,2)));
			}
		}
		label_6.setText(sb.toString());
		int time = (int) (Math.round(routeinfo.dist*0.1)/2.0*3.0);
		
		label_5.setText(time+"분 소요");
		
		label_2.setText(basetime.format(DateTimeFormatter.ofPattern("HH:mm")));
		label_3.setText(basetime.plusMinutes(time).format(DateTimeFormatter.ofPattern("HH:mm")));
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.blue);
		g2.fillRoundRect(0, 0, 50, getHeight(), 20, 20);
		g2.setColor(Color.white);
		g2.fillRect(10, 0, 50, getHeight());
	}
}
