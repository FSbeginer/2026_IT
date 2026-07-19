import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;

public class C_경로검색 extends BF {
	public JLabel label_1;
	public JPanel panel;
	public JLabel label;

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
	public C_경로검색() {
		addWindowListener(new ThisWindowListener());
		setTitle("경로 검색");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		label_1 = new JLabel("역을 우클릭하여 출발역을 선택하세요.");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setOpaque(true);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_1.setForeground(Color.WHITE);
		label_1.setBackground(Color.BLACK);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setPreferredSize(new Dimension(51, 30));
		getContentPane().add(label_1, BorderLayout.SOUTH);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
				Image img = getIcon("metro.png", 932/2, 1256/2).getImage();
				g2.drawImage(img, 0, 0, null);
				
				
				if(points !=null) {
					drawRoute(g2);
				}
				
				g2.setFont(new Font("맑은 고딕",1, 12));
				g2.setStroke(new BasicStroke(2f));
				if(start!=null) {
					g2.setColor(Color.white);
					g2.fillOval(start.x-12, start.y-12, 24, 24);
					
					g2.setColor(new Color(55,125,225));
					g2.drawOval(start.x-12, start.y-12, 24, 24);
					String txt = "출";
					g2.drawString(txt, start.x-6, start.y+6);
				}
				if(end != null) {
					g2.setColor(Color.white);
					g2.fillOval(end.x-12, end.y-12, 24, 24);
					
					g2.setColor(new Color(255,0,0));
					g2.drawOval(end.x-12, end.y-12, 24, 24);
					String txt = "도";
					g2.drawString(txt, end.x-6, end.y+6);
				}
				if(points != null && angle != null && idx !=-1) {
					drawingTrain(g2);
				}
				g2.dispose();
			}

			private void drawingTrain(Graphics2D g2) {
				try {
					var af = g2.getTransform();
					Image train = Helper.getTransImage("icon/train.png", 30, 30);
					g2.rotate(angle.get(idx)+Math.PI/2, points.get(idx).x, points.get(idx).y);
					if(blink) g2.drawImage(train, points.get(idx).x-15, points.get(idx).y-15, this);
					g2.setTransform(af);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			private void drawRoute(Graphics2D g2) {
				g2.setColor(Color.red);
				g2.setStroke(new BasicStroke(3f));
				for (int i = 0; i < points.size()-1; i++) {
					var f = points.get(i);
					var t = points.get(i+1);
					g2.drawLine(f.x, f.y, t.x, t.y);
				}
			}
		};
		panel.setPreferredSize(new Dimension(932/2, 1256/2));
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
		
		label = new JLabel("초기화");
		label.addMouseListener(new LabelMouseListener());
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(391, 12, 65, 27);
		panel.add(label);
		
		pack();
	}

	Station start, end;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;
	public JMenuItem menuItem_1;
	
	public C_경로검색(Station start, Station end) {
		this();
		this.start = start;
		this.end = end;
		findRoute();
	}
	
	List<Point> points;
	List<Double> angle;
	private Timer timer;
	int idx = -1;
	boolean drawing = false;
	private void findRoute() {
		if(timer !=null && timer.isRunning()) timer.stop();
		reset();
		
		var routeInfo = RouteService.findRoute(start, end);
		double dist = routeInfo.dist * 0.1;
		int time =  (int) (Math.round(dist)/2.0*3);
		label_1.setText(String.format("출발: %s → 도착: %s ( %d구간 ) 약 %.1fkm | 약 %d분", start.name, end.name, routeInfo.cost, dist, time));
		
		points = new ArrayList<Point>();
		angle = new ArrayList<>();
			
		var route = routeInfo.route;
		for (int i = 0; i < route.size()-1; i++) {
			var f = route.get(i);
			var t = route.get(i+1);
			if(f.name.equals(t.name)) {
				for (int j = 0; j < 40; j++) {
					points.add(new Point(f.x, f.y));
					angle.add(Math.atan2(t.y-f.y, t.x-f.x));
				}
				continue;
			}
			int step = (int) Math.max(1, Math.hypot(f.x-t.x, f.y-t.y)/0.5);
			
			for (int j = 0; j <= step; j++) {
				double per = (double)j/step;
				int x = (int) (f.x + (t.x-f.x)*per);
				int y = (int) (f.y + (t.y-f.y)*per);
				points.add(new Point(x, y));
				angle.add(Math.atan2(t.y-f.y, t.x-f.x));
			}
		}
		
		timer = new Timer(20, e->{
			++idx;
			if(idx>=points.size()-1) {
				timer.stop();
				panel.repaint();
				startBlink();
			}
			panel.repaint();
		});
		timer.start();
	}
	
	boolean blink = true, moveAble = false;
	private void startBlink() {
		int[] cnt = {0};
		timer = new Timer(500, e->{
			blink = !blink;
			cnt[0]++;
			if(cnt[0]==5) {
				blink = true;
				label_1.setForeground(Color.yellow);
				panel.repaint();
				moveAble =true;
				timer.stop();
			}
			panel.repaint();
		});
		timer.start();
	}

	Station target;
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				target = RouteService.stations.stream().filter(x->Math.hypot(x.x-e.getX(), x.y-e.getY())<12).findFirst().orElse(null);
			}
			public void mouseReleased(MouseEvent e) {
				if (target!=null&&e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
	public void setStart(Station start) {
		this.start = start;
		if(start==end) end =null;
		if(start!=null && end != null) findRoute();
		panel.repaint();
	}
	public void setEnd(Station end) {
		this.end = end;
		if(start==end) start =null;
		if(start!=null && end != null) findRoute();
		panel.repaint();
	}
	private class MenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setStart(target);
		}
	}
	private class MenuItem_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			setEnd(target);
		}
	}
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if(timer!=null&&timer.isRunning()) timer.stop();
		}
	}
	
	private void reset() {
		idx = -1;
		angle = null;
		points = null;
		blink = true;
		moveAble =false;
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(timer!=null&&timer.isRunning()) timer.stop();
			reset();
			start = end = null;
			label_1.setText("역을 우클릭하여 출발역을 선택하세요.");
			panel.repaint();
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(moveAble) {
				showPage(new D_노선스케줄(start, end));
			}
		}
	}
}

