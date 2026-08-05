import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
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
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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
	 * 
	 * @param pno
	 */
	int pno;
	public JPanel panel;
	public JPanel panel_1;

	public G_배송(int pno) {
		addWindowListener(new ThisWindowListener());
		setTitle("배송");
		this.pno = pno;
		setBounds(100, 100, 797, 836);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(820, 820));
		panel.setBorder(new EmptyBorder(10, 10, 10, 10));
		panel.setBackground(new Color(230, 230, 230));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new BorderLayout(0, 0));

		panel_1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				var map = getMapImg();
				var route = getRouteImg();
				if(zoom) {
					Area area = areas.get(G_배송.this.route.get(idx).ano-1);
					g2.translate(getWidth()/2, getHeight()/2);
					g2.scale(2,2);
					g2.translate(-area.x, -area.y);
				}
				g2.drawImage(map, 0, 0, null);
				g2.drawImage(route, 0, 0, null);
				g2.dispose();
			}
		};
		panel_1.addMouseListener(new Panel_1MouseListener());
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setLayout(null);
		panel.add(panel_1, BorderLayout.CENTER);
		pack();

		getData();
	}

	private BufferedImage getMapImg() {
		BufferedImage bi = new BufferedImage(800, 800, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		var src = getIcon("map.png", 800, 800).getImage();
		g2.drawImage(src, 0, 0, null);

		Area currentArea = areas.get(route.get(idx).ano - 1);

		for (Area area : areas) {
			if (currentArea.equals(area))
				fill(bi, area, Color.yellow);
			else
				fill(bi, area, Color.DARK_GRAY);
		}

		g2.dispose();
		return bi;
	}

	private void fill(BufferedImage bi, Area area, Color c) {
		int[][] dirs = { { 1, 0 }, { 0, -1 }, { -1, 0 }, { 0, 1 } };
		Queue<Point> queue = new LinkedList<Point>();
		boolean[][] visited = new boolean[801][801];

		queue.add(new Point(area.x, area.y));
		visited[area.x][area.y] = true;
		while (!queue.isEmpty()) {
			var p = queue.poll();
			bi.setRGB(p.x, p.y, c.getRGB());
			for (var dir : dirs) {
				var np = new Point(p.x + dir[0], p.y + dir[1]);
				if (!visited[np.x][np.y] && !isBorder(bi.getRGB(np.x, np.y))) {
					queue.add(np);
					visited[np.x][np.y] = true;
				}
			}
		}
	}

	private boolean isBorder(int rgb) {
		return !((rgb >> 24 & 0xff) < 40);
	}

	private BufferedImage getRouteImg() {
		BufferedImage bi = new BufferedImage(800, 800, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		g2.setColor(Color.blue);
		for (var sb : subAreas) {
			for (var to : graph.get(sb)) {
				g2.drawLine(sb.x, sb.y, to.sb.x, to.sb.y);
			}
		}
		
		g2.setStroke(new BasicStroke(2f));
		g2.setColor(Color.green);
		for (int i = 0; i < idx*10+tidx-1; i++) {
			var f = points.get(i);
			var t = points.get(i + 1);
			g2.drawLine(f.x,f.y,t.x,t.y);
		}

		g2.setStroke(new BasicStroke(1f));
		g2.setColor(Color.red);
		for (var sb : subAreas) {
			g2.fillOval(sb.x - 3, sb.y - 3, 6, 6);
		}
		
		g2.drawImage(getIcon("logo/start.png", 50,50).getImage(), start.x-25, start.y-50, null);
		g2.drawImage(getIcon("logo/destination.png", 60,60).getImage(), end.x-30, end.y-50, null);
		
		g2.dispose();
		return bi;
	}

	List<Area> areas = new ArrayList<>();
	List<SubArea> subAreas = new ArrayList<>();
	Map<SubArea, List<Edge>> graph = new HashMap<SubArea, List<Edge>>();
	SubArea end, start;
	List<SubArea> route;
	List<Point> points = new ArrayList<Point>();
	int idx, tidx;
	private Timer timer;

	private void getData() {
		try (var rs = DB.res("select * from area")) {
			while (rs.next())
				areas.add(new Area(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from sub_area")) {
			while (rs.next())
				subAreas.add(new SubArea(rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(5)));
		} catch (SQLException e) {
			e.printStackTrace();
		}

		for (var sub : subAreas) {
			graph.put(sub, new ArrayList<>());
		}

		try (var rs = DB.res("select * from linelist")) {
			while (rs.next()) {
				var f = subAreas.get(rs.getInt(2) - 1);
				var t = subAreas.get(rs.getInt(3) - 1);
				addEdge(f, t, Math.hypot(f.x - t.x, f.y - t.y));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select sub_area.* from user join sub_area using(sno) where uno = ?", User.uno)) {
			rs.next();
			end = subAreas.get(rs.getInt(1) - 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select sub_area.* from product join sub_area using(sno) where pno = ?", pno)) {
			rs.next();
			start = subAreas.get(rs.getInt(1) - 1);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		route = getRoute();
		
		points = new ArrayList<>();
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
		timer = new Timer(50, e -> {
			tidx = ++tidx % 10;
			if (tidx == 0)
				idx++;
			if (idx == route.size()-1) {
				timer.stop();
			}
			repaint();
		});
		timer.start();
	}

	private List<SubArea> getRoute() {
		PriorityQueue<Edge> queue = new PriorityQueue<Edge>(Comparator.comparingDouble(x -> x.dist));
		Map<SubArea, Edge> infos = new HashMap<SubArea, Edge>();
		Map<SubArea, SubArea> prev = new HashMap<SubArea, SubArea>();

		var node = new Edge(start, 0);
		queue.add(node);
		infos.put(node.sb, node);

		while (!queue.isEmpty()) {
			var current = queue.poll();
			if (current.dist > infos.get(current.sb).dist)
				continue;
			for (var next : graph.get(current.sb)) {
				double nd = current.dist + next.dist;

				var nextInfo = infos.get(next.sb);
				if (nextInfo == null || nextInfo.dist > nd) {
					var nInfo = new Edge(next.sb, nd);
					queue.add(nInfo);

					infos.put(next.sb, nInfo);
					prev.put(next.sb, current.sb);
				}
			}
		}

		var route = new ArrayList<SubArea>();
		var p = end;
		while (p != null) {
			route.add(p);
			p = prev.get(p);
		}
		Collections.reverse(route);

		return route;
	}

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
	boolean zoom = false;
	private class Panel_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			zoom = !zoom;
			repaint();
		}
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
