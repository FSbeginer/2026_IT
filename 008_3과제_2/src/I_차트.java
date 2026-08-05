import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class I_차트 extends BF {
	final int CW = 260;
	final int CH = 280;
	public JPanel panel;
	int page = 1;
	int sx;
	int bx;
	Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_차트 frame = new I_차트();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public I_차트() {
		setTitle("분석");
		setBounds(100, 100, CW, CH);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setLayout(null);
		panel.setBounds(-CW, 0, CW * 5, CH - 35);
		getContentPane().add(panel);
		
		var d1 = getDatas("select cname name, count(*) cnt from orders join product using(pno) join category using(cno) group by cname order by cnt desc");
		var d2 = getDatas("select pname name, count(*) cnt from orders join product using(pno) group by pname order by cnt desc limit 5");
		var d3 = getDatas("select pname name, count(*) cnt from star join product using(pno) group by pname order by cnt desc limit 5");
		var c0 = new ChartPanel("리뷰 분석", "3 / 3", d3);
		var c1 = new ChartPanel("카테고리별 판매 분석", "1 / 3", d1);
		var c2 = new ChartPanel("상품 판매 분석", "2 / 3", d2);
		var c3 = new ChartPanel("리뷰 분석", "3 / 3", d3);
		var c4 = new ChartPanel("카테고리별 판매 분석", "1 / 3", d1);
		
		c0.setBounds(0, 0, CW, CH - 35);
		c1.setBounds(CW, 0, CW, CH - 35);
		c2.setBounds(CW * 2, 0, CW, CH - 35);
		c3.setBounds(CW * 3, 0, CW, CH - 35);
		c4.setBounds(CW * 4, 0, CW, CH - 35);
		panel.add(c0);
		panel.add(c1);
		panel.add(c2);
		panel.add(c3);
		panel.add(c4);
		
		addSlideEvent(panel);
		addSlideEvent(c0);
		addSlideEvent(c1);
		addSlideEvent(c2);
		addSlideEvent(c3);
		addSlideEvent(c4);

	}
	
	private List<ChartData> getDatas(String sql) {
		var list = new ArrayList<ChartData>();
		try (var rs = DB.res(sql)) {
			while (rs.next()) {
				list.add(new ChartData(rs.getString("name"), rs.getInt("cnt")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return list;
	}
	
	private void addSlideEvent(Component comp) {
		var ma = new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				if(timer != null)
					timer.stop();
				sx = SwingUtilities.convertPoint((Component)e.getSource(), e.getPoint(), getContentPane()).x;
				bx = panel.getX();
			}
			@Override
			public void mouseDragged(MouseEvent e) {
				int x = SwingUtilities.convertPoint((Component)e.getSource(), e.getPoint(), getContentPane()).x;
				int nx = bx + x - sx;
				if(nx > 0)
					nx = 0;
				if(nx < -CW * 4)
					nx = -CW * 4;
				panel.setLocation(nx, 0);
			}
			@Override
			public void mouseReleased(MouseEvent e) {
				int x = SwingUtilities.convertPoint((Component)e.getSource(), e.getPoint(), getContentPane()).x;
				int gap = x - sx;
				if(gap < -40)
					page++;
				else if(gap > 40)
					page--;
				slide();
			}
		};
		comp.addMouseListener(ma);
		comp.addMouseMotionListener(ma);
	}
	
	private void slide() {
		int tx = -CW * page;
		if(timer != null)
			timer.stop();
		timer = new Timer(10, e->{
			int x = panel.getX();
			int move = (tx - x) / 4;
			if(move == 0)
				move = tx > x ? 1 : -1;
			if(Math.abs(tx - x) < 2) {
				panel.setLocation(tx, 0);
				if(page == 0) {
					page = 3;
					panel.setLocation(-CW * page, 0);
				}
				else if(page == 4) {
					page = 1;
					panel.setLocation(-CW * page, 0);
				}
				timer.stop();
			}
			else {
				panel.setLocation(x + move, 0);
			}
		});
		timer.start();
	}

}

class ChartData {
	String name;
	int cnt;
	
	public ChartData(String name, int cnt) {
		this.name = name;
		this.cnt = cnt;
	}
}

class ChartPanel extends JPanel {
	String title;
	String num;
	List<ChartData> datas;
	Color[] colors = {
			new Color(73, 174, 72), new Color(89, 36, 100), new Color(11, 160, 51),
			new Color(139, 14, 151), new Color(92, 133, 12), new Color(44, 125, 164),
			new Color(65, 48, 39)
	};
	
	public ChartPanel(String title, String num, List<ChartData> datas) {
		this.title = title;
		this.num = num;
		this.datas = datas;
		setBackground(Color.WHITE);
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D)g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2.setColor(Color.BLACK);
		g2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		g2.drawString(title, (getWidth() - g2.getFontMetrics().stringWidth(title)) / 2, 25);
		
		int total = datas.stream().mapToInt(x->x.cnt).sum();
		if(total == 0) {
			g2.drawString("데이터 없음", 95, 120);
			g2.dispose();
			return;
		}
		
		int size = 170;
		int x = (getWidth() - size) / 2;
		int y = 50;
		int start = 0;
		for (int i = 0; i < datas.size(); i++) {
			var data = datas.get(i);
			int angle = i == datas.size() - 1 ? 360 - start : (int)Math.round(data.cnt * 360.0 / total);
			g2.setColor(colors[i % colors.length]);
			g2.fillArc(x, y, size, size, start, angle);
			
			double mid = Math.toRadians(start + angle / 2.0);
			String txt = data.name + " (" + data.cnt + ")";
			g2.setColor(Color.BLACK);
			g2.setFont(new Font("맑은 고딕", Font.BOLD, 9));
			int tx = (int)(x + size / 2 + Math.cos(mid) * size * 0.27);
			int ty = (int)(y + size / 2 - Math.sin(mid) * size * 0.27);
			g2.drawString(txt, tx - g2.getFontMetrics().stringWidth(txt) / 2, ty);
			
			start += angle;
		}
		
		g2.setFont(new Font("맑은 고딕", Font.BOLD, 10));
		g2.drawString(num, (getWidth() - g2.getFontMetrics().stringWidth(num)) / 2, 235);
		g2.dispose();
	}
}
