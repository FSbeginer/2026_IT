import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.Queue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class G_배송 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					G_배송 frame = new G_배송(1);
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
	int pno;
	public JPanel panel;
	public JPanel panel_1;

	public G_배송(int pno) {
		addWindowListener(new ThisWindowListener());
		setTitle("배송");
		this.pno = pno;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(820, 820));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setBackground(new Color(240, 240, 240));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

				BufferedImage map = getMapImage();
				BufferedImage route = getRouteImage();
				
				if(zoom) {
					g2.translate(400,400);
					g2.scale(2, 2);
					g2.translate(-areas.get(G_배송.this.route.get(idx).ano-1).x, -areas.get(G_배송.this.route.get(idx).ano-1).y);
				}
				
				g2.drawImage(map, 0, 0, null);
				g2.drawImage(route, 0, 0, null);
				
				g2.dispose();
			}

			private BufferedImage getRouteImage() {
				var bi = new BufferedImage(800, 800, 2);
				var g2 = bi.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g2.setColor(Color.blue);
				for (var sub : subareas) {
					for (var to : graph.get(sub)) {
						g2.drawLine(sub.x, sub.y, to.sb.x, to.sb.y);
					}
				}
				g2.setColor(Color.green);
				g2.setStroke(new BasicStroke(2f));
				for (int i = 0; i < idx*10+tidx; i++) {
					var f = points.get(i);
					var t = points.get(i+1);
					g2.drawLine(f.x, f.y, t.x, t.y);
				}
				
				g2.setColor(Color.red);
				for (var sb : subareas) {
					g2.fillOval(sb.x-3, sb.y-3, 6, 6);
				}
				
				g2.dispose();
				return bi;
			}

			private BufferedImage getMapImage() {
				var bi = new BufferedImage(800, 800, 2);
				var g2 = bi.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				var src = getIcon("map.png",800,800).getImage();
				g2.drawImage(src, 0, 0, null);
				
				for (var area : areas) {
					if(route.get(idx).ano==area.ano) {
						fillColor(bi,g2,new Point(area.x, area.y),Color.yellow);
					}
					else {
						fillColor(bi,g2,new Point(area.x, area.y),Color.darkGray);
					}
				}
				
				g2.dispose();
				return bi;
			}

			private void fillColor(BufferedImage bi, Graphics2D g2, Point start, Color darkgray) {
				Queue<Point> queue = new LinkedList<Point>();
				boolean[][] visited = new boolean[801][801];
				int[][] dir = {{1,0},{0,-1},{-1,0},{0,1}};
				
				queue.add(start);
				visited[start.x][start.y] = true;
				bi.setRGB(start.x, start.y, darkgray.getRGB());
				while(!queue.isEmpty()) {
					var c = queue.poll();
					for (int[] is : dir) {
						var np = new Point(c.x+is[0], c.y+is[1]);
						if(np.x<0||np.x>800||np.y<0||np.y>800||visited[np.x][np.y]||(bi.getRGB(np.x, np.y)>>24&0xff)>40) {
							continue;
						}
						visited[np.x][np.y] = true;
						bi.setRGB(np.x, np.y, darkgray.getRGB());
						queue.add(np);
					}
				}
			}
		};
		panel_1.addMouseListener(new Panel_1MouseListener());
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_1, BorderLayout.CENTER);

		pack();

		getData();
		panel_1.repaint();
	}

	List<Area> areas = new ArrayList<Area>();
	List<SubArea> subareas = new ArrayList<>();
	Map<SubArea, List<Edge>> graph = new HashMap<SubArea, List<Edge>>();
	SubArea start, end;

	private void getData() {
		try (var rs = DB.res("select * from area ")) {
			while (rs.next())
				areas.add(new Area(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from sub_area ")) {
			while (rs.next())
				subareas.add(new SubArea(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		for (var sub : subareas) {
			graph.put(sub, new ArrayList<Edge>());
		}
		try (var rs = DB.res("select * from linelist")) {
			while (rs.next()) {
				var f = subareas.get(rs.getInt(2) - 1);
				var t = subareas.get(rs.getInt(3) - 1);
				addEdge(f, t, Math.hypot(f.x - t.x, f.y - t.y));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select sub_area.* from user join sub_area using(sno) join area using(ano) where uno = ?",
				User.uno)) {
			rs.next();
			end = subareas.get(rs.getInt("sno")-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res(
				"select sub_area.* from product join sub_area using(sno) join area using(ano) where pno = ?", pno)) {
			rs.next();
			start = subareas.get(rs.getInt("sno")-1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		findroute();
	}

	List<SubArea> route;
	List<Point> points;

	private void findroute() {
		PriorityQueue<Edge> queue = new PriorityQueue<>(Comparator.comparingDouble(x -> x.dist));
		Map<SubArea, Edge> info = new HashMap<SubArea, Edge>();
		Map<SubArea, SubArea> prev = new HashMap<SubArea, SubArea>();

		var ed = new Edge(start, 0);
		queue.add(ed);
		info.put(start, ed);
		while (!queue.isEmpty()) {
			var current = queue.poll();
			if (current.dist > info.get(current.sb).dist)
				continue;

			for (var next : graph.get(current.sb)) {
				var nd = current.dist + next.dist;
				if (info.get(next.sb) == null || nd < info.get(next.sb).dist) {
					var ned = new Edge(next.sb, nd);
					queue.add(ned);
					info.put(next.sb, ned);
					prev.put(next.sb, current.sb);
				}
			}
		}

		route = new ArrayList<>();
		var p = end;
		while (p != null) {
			route.add(p);
			p = prev.get(p);
		}
		Collections.reverse(route);

		points = new ArrayList<Point>();
		for (int i = 0; i < route.size() - 1; i++) {
			var f = route.get(i);
			var t = route.get(i + 1);
			for (int j = 0; j < 10; j++) {
				int x = (int) (f.x + (t.x - f.x) * (j / 9.0));
				int y = (int) (f.y + (t.y - f.y) * (j / 9.0));
				points.add(new Point(x, y));
			}
		}

		idx = tidx = 0;
		timer = new Timer(20, e -> {
			tidx = ++tidx % 10;
			if (tidx == 0)
				idx++;
			if (idx == route.size() - 1) {
				timer.stop();
				msgInfo("배송이 완료되었습니다.");
				dispose();
				while (!BF.prev.isEmpty()) {
					var pp = BF.prev.pop();
					if(pp instanceof MainFrame) {
						pp.updateForm();
						pp.setVisible(true);
						break;
					}
					else {
						dispose();
					}
				}
			}
			panel_1.repaint();
		});
		timer.start();
	}

	Timer timer;
	int idx, tidx;

	private void addEdge(SubArea f, SubArea t, double hypot) {
		graph.get(f).add(new Edge(t, hypot));
		graph.get(t).add(new Edge(f, hypot));
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (timer != null)
				timer.stop();
		}
	}
	boolean zoom;
	private class Panel_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			zoom = !zoom;
			panel_1.repaint();
		}
	}
}

class Area {
	int ano;
	String aname;
	int x, y;

	public Area(int ano, String aname, int x, int y) {
		this.ano = ano;
		this.aname = aname;
		this.x = x;
		this.y = y;
	}
}

class SubArea {
	int sno;
	String sname;
	int x, y, ano;

	public SubArea(int sno, String sname, int x, int y, int ano) {
		this.sno = sno;
		this.sname = sname;
		this.x = x;
		this.y = y;
		this.ano = ano;
	}
}

class Edge {
	SubArea sb;
	double dist;

	public Edge(SubArea sb, double dist) {
		this.sb = sb;
		this.dist = dist;
	}
}
