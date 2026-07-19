import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import java.awt.Component;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class C_경로검색 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					C_경로검색 frame = new C_경로검색();
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
	SearchDTO dto;
	public JPanel panel;
	public JLabel label;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;
	public JMenuItem menuItem_1;
	
	public C_경로검색(SearchDTO dto) {
		this();
		this.dto = dto;
		try (var rs = DB.res("select * from station where name in(%s, %s) order by name != %s", dto.start, dto.end, dto.start)) {
			rs.next();
			start = new Station(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)/2, rs.getInt(5)/2);
			rs.next();
			end = new Station(rs.getInt(1), rs.getString(2), rs.getString(3), rs.getInt(4)/2, rs.getInt(5)/2);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	int idx = -1;
	public C_경로검색() {
		setTitle("경로 검색");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(getIcon("metro.png",932/2, 1256/2).getImage(), 0, 0, null);
				g2.setStroke(new BasicStroke(1.5f));
				if(idx>=0) {
					g2.setColor(Color.red);
					g2.setStroke(new BasicStroke(3f));
					for (int i = 0; i < points.size()-1; i++) {
						var f = points.get(i);
						var t = points.get(i+1);
						g2.drawLine(f.x, f.y, t.x, t.y);
					}
				}
				if(start!=null) {
					g2.setColor(Color.white);
					g2.fillOval(start.x-11, start.y-11, 22, 22);
					g2.setColor(new Color(55,128,255));
					g2.drawOval(start.x-11, start.y-11, 22, 22);
					g2.drawString("출", start.x-6, start.y+4);
				}
				if(end!=null) {
					g2.setColor(Color.white);
					g2.fillOval(end.x-11, end.y-11, 22, 22);
					g2.setColor(Color.red);
					g2.drawOval(end.x-11, end.y-11, 22, 22);
					g2.drawString("도", end.x-6, end.y+4);
				}
				if(drawTrain&&idx>=0) {
					var train = getTransferImage("icon/train.png", 30,30).getImage();
					var ori = g2.getTransform();
					var point = points.get(idx);
					g2.rotate(angle.get(idx)+Math.PI/2, point.x, point.y);
					g2.drawImage(train, point.x-15, point.y-15, null);
					g2.setTransform(ori);
				}
			}
		};
		panel.setPreferredSize(new Dimension(932/2, 1256/2));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		popupMenu = new JPopupMenu();
		popupMenu.setBounds(-10008, -10031, 81, 58);
		addPopup(panel, popupMenu);
		
		menuItem = new JMenuItem("출발");
		menuItem.addActionListener(new MenuItemActionListener());
		popupMenu.add(menuItem);
		
		menuItem_1 = new JMenuItem("도착");
		menuItem_1.addActionListener(new MenuItem_1ActionListener());
		popupMenu.add(menuItem_1);
		
		label_1 = new JLabel("초기화");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setForeground(Color.RED);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(397, 0, 69, 25);
		panel.add(label_1);
		
		label = new JLabel("New label");
		label.addMouseListener(new LabelMouseListener());
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(Color.BLACK);
		label.setPreferredSize(new Dimension(57, 30));
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		getContentPane().add(label, BorderLayout.SOUTH);
		
		pack();
		
		label.setText("역을 우클릭하여 출발역을 선택하세요.");
	}
	
	Station target, start, end;
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				target = RouteService.stations.stream().filter(x->Math.hypot(e.getX()-x.x, e.getY()-x.y)<=14).findFirst().orElse(null);
				if (e.isPopupTrigger()&&target!=null) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	private class MenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			start = target;
			if(start==end) end = null;
			if(start!=null&&end!=null) routeLoad();
			repaint();
		}
	}
	private class MenuItem_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			end = target;
			if(start==end) start = null;
			if(start!=null&&end!=null) routeLoad();
			repaint();
		}

	}
	
	boolean drawTrain = false;
	List<Point> points = new ArrayList<>();
	List<Double> angle = new ArrayList<Double>();
	public JLabel label_1;
	private void routeLoad() {
		RouteInfo info = RouteService.getRouteInfo(start, end);
		double dist = info.dist * 0.1;
		int time = (int) (Math.round(dist)/2*3);
		label.setText(String.format("출발: %s → 도착: %s ( %d구간 ) 약 %.1f km | 약 %d분", start.name, end.name, info.cost, dist, time));
		
		points = new ArrayList<Point>();
		angle = new ArrayList<>();
		var route = info.route;
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			double d = Math.hypot(f.x-t.x, f.y-t.y) * 0.1;
			int step = (int) Math.max(1, Math.round(dist*2/3/0.2));
					
			if(d==0) {
				for (int j = 0; j < 5; j++) {
					points.add(new Point(f.x, f.y));
					angle.add(Math.atan2(t.y-f.y, t.x-f.x));
				}
				continue;
			}
			for (int j = 0; j <= step; j++) {
				double per = (double)j/step;
				int x = (int) (f.x+(t.x-f.x)*per);
				int y = (int) (f.y+(t.y-f.y)*per);
				angle.add(Math.atan2(t.y-f.y, t.x-f.x));
				points.add(new Point(x, y));
			}
		}
		idx = -1;
		drawTrain = true;
		new Timer(200, e->{
			idx++;
			repaint();
			if(idx>=points.size()-1||stop) {
				var timer = (Timer)e.getSource();
				timer.stop();
				stop = false;
			}
		}).start();
	}
	boolean stop = false;
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			stop = true;
			start = end = null;
			idx =-1;
			drawTrain = false;
			label.setText("역을 우클릭하여 출발역을 선택하세요");
			repaint();
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(start!=null && end != null) {
				showPage(new D_노선스케줄(start, end));
			}
		}
	}
}

