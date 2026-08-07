import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.AlphaComposite;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.swing.border.LineBorder;

public class B_홈 extends BP {
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JLabel label;
	public JPanel panel_4;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JPanel panel_5;
	public JPanel panel_6;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JPanel panel_7;
	public JLabel label_8;
	private Timer timer;

	/**
	 * Create the panel.
	 */
	public B_홈() {
		setLayout(null);

		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(12, 10, 933, 192);
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 20, 0));

		panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(null);

		label = new JLabel("  광고");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label.setBounds(0, 0, 57, 22);
		panel_1.add(label);

		panel_4 = new AdvertisementPanel();
		panel_4.setBounds(31, 32, 240, 150);
		panel_1.add(panel_4);

		panel_2 = new JPanel();
		panel_2.addMouseListener(new Panel_2MouseListener());
		panel.add(panel_2);
		panel_2.setLayout(null);

		label_1 = new JLabel(getIcon("logo/cash.png", 50, 50));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(63, 23, 164, 96);
		panel_2.add(label_1);

		label_2 = new JLabel(
				"<html><div align = 'center'><font size = 4><b>충전소</b></font><br><font color = 'gray' size = 3'>충전하기");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(73, 129, 146, 39);
		panel_2.add(label_2);

		panel_3 = new JPanel();
		panel.add(panel_3);
		panel_3.setLayout(null);

		label_3 = new JLabel("  바로가기");
		label_3.setBounds(0, 0, 128, 22);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		panel_3.add(label_3);

		panel_5 = new JPanel();
		panel_5.addMouseListener(new Panel_5MouseListener());
		panel_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_5.setBounds(10, 32, 135, 150);
		panel_3.add(panel_5);
		panel_5.setLayout(null);

		label_4 = new JLabel(getIcon("logo/maze.png", 50, 50));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(0, 0, 135, 96);
		panel_5.add(label_4);

		label_5 = new JLabel(
				"<html><div align = 'center'><font size = 4><b>미로</b></font><br><font color = 'gray' size = 3'>포인트 적립");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_5.setBounds(0, 101, 135, 49);
		panel_5.add(label_5);

		panel_6 = new JPanel();
		panel_6.addMouseListener(new Panel_6MouseListener());
		panel_6.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_6.setBounds(150, 32, 135, 150);
		panel_3.add(panel_6);
		panel_6.setLayout(null);

		label_6 = new JLabel(
				"<html><div align = 'center'><font size = 4><b>경품</b></font><br><font color = 'gray' size = 3'>룰렛 뽑기");
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_6.setBounds(0, 101, 135, 49);
		panel_6.add(label_6);

		label_7 = new JLabel(getIcon("logo/roulette.png", 50, 50));
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setBounds(0, 0, 135, 96);
		panel_6.add(label_7);

		panel_7 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				var img = getBuf();
				g2.drawImage(img, 0, 0, null);
				g2.dispose();
			}
		};
		panel_7.addMouseListener(new Panel_7MouseListener());
		panel_7.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_7.setBounds(12, 212, 933, 279);
		add(panel_7);
		panel_7.setLayout(null);

		label_8 = new JLabel("  사람들이 많이 구매하는 카테고리");
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_8.setBounds(12, 10, 257, 22);
		panel_7.add(label_8);

		getData();
		
		timer = new Timer(10, e->{
			rot-=10;
			if(rot<=0) {
				timer.stop();
				selCno = maxCno;
			}
			panel_7.repaint();
		});
		timer.start();
	}
	int rot = 360;
	private BufferedImage getBuf() {
		int mw = panel_7.getWidth(), mh = panel_7.getHeight();
		BufferedImage bi = new BufferedImage(mw, mh, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		double ang = 90;
		arcsByCno = new HashMap<Integer, Arc2D>();
		int bx = mw / 2-50, by = mh / 2;
		int r = 100;
		g2.setFont(new Font("맑은 고딕", 1, 12));
		
		for (int i = 0; i < datas.size(); i++) {
			var data = datas.get(i);
			double deg = data.rate * 360;
			Arc2D arc;
			if(selCno==data.cno)
				arc = new Arc2D.Double(bx - r-10, by - r-10, r + r+20, r + r+20, ang,-deg, 2);
			else
				arc = new Arc2D.Double(bx - r, by - r, r + r, r + r, ang,-deg, 2);
			
			g2.setColor(data.c);
			g2.fill(arc);
			g2.setColor(Color.white);
			g2.draw(arc);
			
			g2.setColor(data.c);
			g2.fillRect(bx+100+30-5, by-r-5+(25*i), 10, 10);
			g2.setColor(selCno==data.cno? Color.BLUE.brighter() : Color.black);
			g2.drawString(data.cname,bx+100+30+10, by-r+5 + (25)*i);
			ang -= deg;
			
			arcsByCno.put(data.cno, arc);
		}
		g2.setColor(Color.white);
		g2.fillOval(bx-50, by-50, 100, 100);
		
		g2.fillArc(bx-r-20, by-r-20, r+r+40, r+r+40, 90, -rot);

		g2.dispose();
		return bi;
	}

	class ChartData {
		int cno;
		String cname;
		double rate;
		Color c;

		public ChartData(int cno, String cname, double rate) {
			this.cno = cno;
			this.cname = cname;
			this.rate = rate;
			c = Color.getHSBColor((float) Math.random(), 0.4f, 0.9f);
		}
	}

	List<ChartData> datas = new ArrayList<B_홈.ChartData>();
	Map<Integer, Arc2D> arcsByCno;
	int maxCno;
	private void getData() {
		int sum = 0;
		try {
			sum = DB.select(
					"select sum(`order`.quantity) cnt from `order` join product using(pno) right join category  using(cno) ;",
					Integer.class);
		} catch (SQLException e1) {
		}
		try (var rs = DB.res(
				"select category.*, sum(`order`.quantity) cnt from `order` join product using(pno) right join category  using(cno) group by cno;")) {
			while (rs.next()) {
				int cno = rs.getInt("cno");
				String cname = rs.getString("cname");
				double rate = rs.getDouble("cnt") / sum;
				datas.add(new ChartData(cno, cname, rate));
			}
			maxCno = DB.select(
					"select category.*, rank() over(order by count(quantity) desc , cno)  rno from `order` join product using(pno) right join category  using(cno) group by cno limit 1;",
					Integer.class);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Panel_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((MainFrame) SwingUtilities.getWindowAncestor(label)).showPage(new E_충전소());
		}
	}

	private class Panel_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((MainFrame) SwingUtilities.getWindowAncestor(label)).showPage(new H_경품());
		}
	}

	private class Panel_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((MainFrame) SwingUtilities.getWindowAncestor(label)).showPage(new I_미로());
		}
	}

	class AdvertisementPanel extends JPanel {
		float alpha = 0f;
		int hold, idx;
		Timer timer;
		boolean fade = false;

		public AdvertisementPanel() {
			timer = new Timer(30, e -> {
				if (!fade) {
					hold++;
					if (hold >= 70) {
						hold = 0;
						fade = true;
					}
				} else {
					alpha += 0.03f;
					if (alpha >= 0.7f) {
						alpha = 0f;
						fade = false;
						idx = ++idx % 5;
					}
				}
				repaint();
			});
			timer.start();
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			var img = getIcon("advertise/" + (idx + 1) + ".jpg", getWidth(), getHeight()).getImage();
			int nextIdx = (idx + 1) % 5;
			var nextImg = getIcon("advertise/" + (nextIdx + 1) + ".jpg", getWidth(), getHeight()).getImage();
			g2.drawImage(img, 0, 0, null);
			g2.setComposite(AlphaComposite.getInstance(3, alpha));
			g2.drawImage(nextImg, 0, 0, null);
			g2.setComposite(AlphaComposite.getInstance(3, 1f));

			int totw = (6 + 6) * 5;
			int bx = (getWidth() - totw) / 2;
			for (int i = 0; i < 5; i++) {
				g2.setColor(i == idx ? Color.BLUE : Color.gray);
				g2.fillOval(bx + (12 * i) - 3, getHeight() - 20, 6, 6);
			}
			g2.dispose();
		}
	}
	int selCno;
	private class Panel_7MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			var buf = getBuf();
			var data = datas.stream().filter(x->x.c.getRGB()==buf.getRGB(e.getX(), e.getY())).findFirst().orElse(null);
			if(data != null) {
				selCno =data.cno;
			}
			panel_7.repaint();
		}
	}
}
