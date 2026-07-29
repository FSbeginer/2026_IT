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
import java.util.Set;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class I_차트 extends BF {

	private JPanel contentPane;

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
	public I_차트(int uno) {
		setTitle("차트");
		this.uno = uno;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 661, 620);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("ㅁㄴㅇㅁㄴㅇㅁ");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 621, 46);
		contentPane.add(label);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("맑은 고딕",1,12));
				var ori = g2.getTransform();
				double ang = 0;
				int bx = getWidth()/2, by = getHeight()/2, r = 180;
				for (int i = 0; i < 3; i++) {
					double deg = (double)cnt[i]/sum * 360;
					var arc = new Arc2D.Double(bx-r, by-r, r+r, r+r, ang, deg, 2);
					g2.setColor(c[i]);
					g2.fill(arc);
					ang +=deg;
				}
				ang = 0;
				for (int i = 0; i < 3; i++) {
					double deg = (double)cnt[i]/sum * 360;
					double per = (double)cnt[i]/sum * 100;
					g2.setColor(Color.white);
					String txt = String.format("%.1f%%", per);
					System.out.println(ang+deg);
					g2.rotate(-Math.toRadians(ang+deg/2),bx,by);
					g2.rotate(Math.toRadians(ang+deg/2),bx+r/2+50,by);
					g2.drawString(txt, bx+r/2+50, by+6);
					g2.setTransform(ori);
					ang += deg;
				}
				g2.setColor(Color.white);
				g2.fillOval(bx-80, by-80, 160, 160);
				g2.setColor(Color.black);
				var fm = g2.getFontMetrics();
				String ss = "총 "+sum+"건";
				g2.drawString(ss, bx-fm.stringWidth(ss)/2, by+6);
				
			}
		};
		panel.setBounds(72, 66, 477, 412);
		contentPane.add(panel);
		
		label_1 = new JLabel("New label");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(24, 491, 182, 25);
		contentPane.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(230, 491, 182, 25);
		contentPane.add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(436, 491, 182, 25);
		contentPane.add(label_3);
		
		getDAta();
	}
	int[] cnt = new int[3];
	Color[] c = {Color.blue.darker(), Color.green.darker(), new Color(155,0,155)};
	int sum;
	private void getDAta() {
		try {
			var u = DB.getUser(uno);
			label.setText(u.name+"님의 예약 현황");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try (var rs = DB.res("select * from reservation where uno = ?",uno)) {
			while(rs.next()) {
				var start = RouteService.stations.get(rs.getInt("start_sno")-1);
				var end = RouteService.stations.get(rs.getInt("end_sno")-1);
				var routeInfo = RouteService.getRouteInfo(start, end);
				couting(routeInfo);
			}
			sum = Arrays.stream(cnt).sum();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		label_1.setText(String.format("%d호선 %d건 (%.1f%%)", 1, cnt[0], (double)cnt[0]/sum*100));
		label_2.setText(String.format("%d호선 %d건 (%.1f%%)", 2, cnt[1], (double)cnt[1]/sum*100));
		label_3.setText(String.format("%d호선 %d건 (%.1f%%)", 7, cnt[2], (double)cnt[2]/sum*100));
		label_1.setIcon(new ImageIcon(rectImg(c[0])));
		label_2.setIcon(new ImageIcon(rectImg(c[1])));
		label_3.setIcon(new ImageIcon(rectImg(c[2])));
	}
	public static BufferedImage rectImg(Color c) {
		var bi = new BufferedImage(15, 15, 2);
		var g2 = bi.createGraphics();
		g2.setColor(c);
		g2.fillRect(0, 0, 15, 15);
		return bi;
	}

	private void couting(RouteInfo routeInfo) {
		Set<String> set = new HashSet<>();
		var route = routeInfo.route;
		for (Station station : route) {
			set.add(station.line.substring(1,1+1));
		}
		
		if(set.contains("1"))
			cnt[0]++;
		if(set.contains("2"))
			cnt[1]++;
		if(set.contains("7"))
			cnt[2]++;
	}

}
