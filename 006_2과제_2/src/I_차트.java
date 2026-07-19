import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class I_차트 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_차트 frame = new I_차트(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param uno 
	 */
	int uno;
	public JLabel label;
	public JPanel panel;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public I_차트(Integer uno) {
		setTitle("차트");
		this.uno = uno;
		setBounds(100, 100, 722, 652);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("New label");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(27, 12, 654, 47);
		getContentPane().add(label);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setFont(new Font("맑은 고딕", 1,15));
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				double ang = 0;
				var ori =g2.getTransform();
				int bx = getWidth()/2,by=getHeight()/2, r = getHeight()/2;
				for (int i = 0; i < cnt.length; i++) {
					double deg = (double)cnt[i]/sum*360;
					var arc =new Arc2D.Double(bx-r, by-r, r+r, r+r, ang, deg,2);
					g2.setColor(col[i]);
					g2.fill(arc);
					g2.rotate(-Math.toRadians(ang+deg/2), bx, by);
					g2.rotate(Math.toRadians(ang+deg/2), bx+r*2/3,by);
					g2.setColor(Color.white);
					var str = String.format("%.1f%%", (double)cnt[i]/sum*100);
					g2.drawString(str, bx+r*2/3-(g2.getFontMetrics().stringWidth(str)/2), by+6);
					g2.setTransform(ori);
					ang += deg;
				}
				g2.setColor(Color.white);
				g2.fillOval(bx-100, by-100, 200, 200);
				g2.setColor(Color.black);
				var str = String.format("총 %d 건", sum);
				g2.drawString(str, bx-g2.getFontMetrics().stringWidth(str)/2, by+6);
				g2.dispose();
			}
		};
		panel.setBounds(27, 106, 654, 453);
		getContentPane().add(panel);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(24, 571, 203, 29);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(251, 571, 203, 29);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(478, 571, 203, 29);
		getContentPane().add(label_3);
		
		getData();
		load();
	}
	private void load() {
		try {
			var name = DB.select("select name from user where uno = ?", String.class, uno);
			label.setText(name+"님의 예약 현황");
			label_1.setText(String.format("%d호선 %d건 (%.1f%%)", 1, cnt[0], (double)cnt[0]/sum*100));
			label_2.setText(String.format("%d호선 %d건 (%.1f%%)", 2, cnt[1], (double)cnt[1]/sum*100));
			label_3.setText(String.format("%d호선 %d건 (%.1f%%)", 7, cnt[2], (double)cnt[2]/sum*100));
			label_1.setIcon(getBoxIcon(col[0]));
			label_2.setIcon(getBoxIcon(col[1]));
			label_3.setIcon(getBoxIcon(col[2]));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private Icon getBoxIcon(Color color) {
		BufferedImage bi = new BufferedImage(25, 25, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(color);
		g2.fillRect(0, 0, 25, 25);
		g2.dispose();
		return new ImageIcon(bi);
	}

	int[] cnt;
	int sum;
	Color[] col = {Color.blue.darker(), Color.green.darker(), new Color(100,0,128)};
	private void getData() {
		try (var rs = DB.res("select * from reservation where uno = ?",uno)) {
			cnt = new int[3];
			while(rs.next()) {
				 var start = RouteService.stations.get(rs.getInt("start_sno")-1);
				 var end = RouteService.stations.get(rs.getInt("end_sno")-1);
				 var routeInfo = RouteService.findRoute(start, end);
				 counting(routeInfo.route);
			}
			sum = Arrays.stream(cnt).sum();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void counting(List<Station> route) {
		Set<String> cnt = new HashSet<>();
		for (var r : route) {
			cnt.add(r.line.substring(0,2));
		}
		if(cnt.contains("I1"))
			this.cnt[0]++;
		if(cnt.contains("I2"))
			this.cnt[1]++;
		if(cnt.contains("I7"))
			this.cnt[2]++;
	}
	
}
