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

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import jdk.dynalink.linker.support.CompositeGuardingDynamicLinker;

import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseMotionAdapter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
	Station start,end;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;
	public JMenuItem menuItem_1;
	private Timer timer;
	public C_경로검색(Station start, Station end) {
		this();
		this.start = start;
		this.end = end;
		findRoute();
	}




	/**
	 * Create the frame.
	 */
	public C_경로검색() {
		setTitle("경로 검색");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				g2.drawImage(getIcon("metro.png",932/2,1256/2).getImage(), 0, 0, null);
				g2.setFont(new Font("맑은 고딕",1,12));
				
				if(drawing) {
					g2.setStroke(new BasicStroke(4f));
					g2.setColor(Color.red);
					for (int i = 0; i < points.size()-1; i++) {
						var f = points.get(i);
						var t = points.get(i+1);
						g2.drawLine(f.x, f.y, t.x, t.y);
					}
				}
				
				if(start!=null) {
					g2.setStroke(new BasicStroke(1.5f));
					g2.setColor(Color.white);
					g2.fillOval(start.x-11, start.y-11, 22, 22);
					g2.setColor(new Color(55,125,255));
					g2.drawOval(start.x-11, start.y-11, 22, 22);
					g2.drawString("출", start.x-6, start.y+5);
				}
				if(end!=null) {
					g2.setStroke(new BasicStroke(1.5f));
					g2.setColor(Color.white);
					g2.fillOval(end.x-11, end.y-11, 22, 22);
					g2.setColor(Color.red);
					g2.drawOval(end.x-11, end.y-11, 22, 22);
					g2.drawString("도", end.x-6, end.y+5);
				}
				if(drawing&&blink) {
					try {
						var train = Helper.getTransferImage("icon/train.png", 30, 30);
						var p = points.get(idx);
						var ori = g2.getTransform();
						g2.rotate(angle.get(idx),p.x,p.y);
						g2.drawImage(train, p.x-15, p.y-15, null);
						g2.setTransform(ori);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
				g2.dispose();
			}
		};
		panel.addMouseMotionListener(new PanelMouseMotionListener());
		panel.setPreferredSize(new Dimension(932/2,1256/2));
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);
		
		popupMenu = new JPopupMenu();
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
		label_1.setBounds(399, 10, 55, 27);
		panel.add(label_1);
		
		label = new JLabel("New label");
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label.setOpaque(true);
		label.setBackground(Color.BLACK);
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setPreferredSize(new Dimension(57, 20));
		getContentPane().add(label, BorderLayout.SOUTH);
		pack();
		reset();
	}
	boolean blink = true;
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
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
			if(start==end)end = null;
			if(start!=null&&end!=null) findRoute();
			repaint();
		}
	}
	private class MenuItem_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			end = target;
			if(start==end)start= null;
			if(start!=null&&end!=null) findRoute();
			repaint();
		}
	}
	private void findRoute() {
		if(timer!=null&&timer.isRunning()) {
			timer.stop();
		}
		var routeInfo = RouteService.getRouteInfo(start, end);
		int cost = routeInfo.cost;
		double dist = routeInfo.dist *0.1;
		int time = (int) (Math.round(dist)/2.0*3);
		label.setText(String.format("출발: %s → 도착: %s ( %d구간 ) 약 %.1fkm | 약 %d분", start.name, end.name, cost, dist, time));
		
		points = new ArrayList<Point>();
		angle=  new ArrayList<>();
		var route = routeInfo.route;
		for (int i = 0; i < route.size()-1; i++) {
			var f= route.get(i);
			var t= route.get(i+1);
			if(f==t) {
				for (int j = 0; j < 250; j++) {
					points.add(new Point(f.x,f.y));
					angle.add(Math.PI/2+Math.atan2(t.y-f.y, t.x-f.x));
				}
			}
			else {
				int step = (int) Math.max(1, Math.hypot(f.x-t.x, f.y-t.y)/0.4);
				for (int j = 0; j <= step; j++) {
					double per = (double)j / step;
					int x = (int) (f.x + (t.x-f.x)*per);
					int y = (int) (f.y + (t.y-f.y)*per);
					points.add(new Point(x,y));
					angle.add(Math.PI/2+Math.atan2(t.y-f.y, t.x-f.x));
				}
			}
		}
		
		drawing = true;
		idx++;
		panel.repaint();
		timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				idx++;
				panel.repaint();
				if(idx==points.size()-1) {
					timer.stop();
					int[] cnt = {0};
					timer = new Timer(250, new ActionListener() {
						@Override
						public void actionPerformed(ActionEvent e) {
							if(cnt[0]%2==0) {
								blink = false;
							}
							else {
								blink = true;
							}
							repaint();
							cnt[0]++;
							if(cnt[0]==4) {
								timer.stop();
								label.setForeground(Color.yellow);
								clickable = true;
							}
						}
					});
					timer.start();
				}
			}	
		});
		timer.start();
	}
	int idx = -1;
	boolean clickable = false;
	private void reset() {
		points = null;
		angle = null;
		drawing =false;
		target = null;
		start = end = null;
		blink = true;
		clickable = false;
		if(timer!=null&&timer.isRunning())timer.stop();
		label.setText("역을 우클릭하여 출발역을 선택하세요.");
		idx = -1;
		repaint();
	}
	List<Point> points;
	List<Double> angle;
	boolean drawing = false;
	Station target;
	private class PanelMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			target = RouteService.stations.stream().filter(x->Math.hypot(x.x-e.getX(), x.y-e.getY())<=12).findFirst().orElse(null);
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			reset();
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
//			if(clickable) {
				showpage(new D_노선스케줄(start,end));
//			}
		}
	}
}
