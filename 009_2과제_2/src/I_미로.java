import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class I_미로 extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JLabel label_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_미로 frame = new I_미로();
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
	public I_미로() {
		addKeyListener(new ThisKeyListener());
		addWindowListener(new ThisWindowListener());
		getContentPane().setBackground(new Color(240, 240, 240));
		setTitle("미로");
		setBounds(100, 100, 603, 634);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 563, 85);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("방향키로 이동");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label.setBounds(12, 10, 98, 15);
		panel.add(label);

		label_1 = new JLabel("미로 탈출");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(201, 27, 161, 48);
		panel.add(label_1);

		panel_1 = new JPanel();
		panel_1.setBounds(12, 105, 563, 480);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(0, 15, 0, 0));

		addpanel();
		visited[1][1] = true;
		pps[1][1].setBackground(Color.white);
		pps[1][1].start = true;
		pps[13][13].end = true;
		makeMaze(1, 1);
		solveMaze();
	}

	Timer timer;

	private void solveMaze() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				timer.stop();
				var route = getRoute();
				int sum = 0;
				for (var r : route) {
					pps[r.x][r.y].cnt = 5;
					pps[r.x][r.y].timer.setInitialDelay(sum);
					pps[r.x][r.y].timer.start();
					
					sum += 20;
					if(sum/20==route.size())pps[r.x][r.y].outside = timer;
					else pps[r.x][r.y].outside = null;
				}
			}
		});
		timer.start();
	}

	private List<Point> getRoute() {
		Queue<Point> queue = new LinkedList<>();

		int[][] dirs = { { 0, 1 }, { 1, 0 }, { 0, -1 }, { -1, 0 } };
		boolean[][] visited = new boolean[size][size];
		Point[][] prev = new Point[size][size];
		visited[myPoint.x][myPoint.y] = true;
		queue.add(myPoint);
		while (!queue.isEmpty()) {
			var current = queue.poll();
			for (var dir : dirs) {
				Point np = new Point(current.x + dir[0], current.y + dir[1]);
				if (np.x < 0 || np.x >= size || np.y < 0 || np.y >= size || visited[np.x][np.y]
						|| pps[np.x][np.y].getBackground().equals(Color.black)) {
					continue;
				}
				visited[np.x][np.y] = true;
				queue.add(np);
				prev[np.x][np.y] = current;
			}
		}

		List<Point> route = new ArrayList<Point>();
		var p = endPoint;
		while (p != null) {
			route.add(p);
			p = prev[p.x][p.y];
		}
		Collections.reverse(route);
		return route;
	}

	private void resetMaze() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
		myPoint = new Point(1, 1);
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				pps[i][j].setBackground(Color.black);
			}
		}
		visited = new boolean[size][size];
		solveMaze();
	}

	int size = 15;
	MyPanel[][] pps = new MyPanel[size][size];
	boolean[][] visited = new boolean[size][size];
	Point myPoint = new Point(1, 1);
	Point endPoint = new Point(13, 13);

	private void makeMaze(int x, int y) {
		int[][] dirs = { { 0, 2 }, { 2, 0 }, { 0, -2 }, { -2, 0 } };
		var rdirs = Arrays.stream(dirs).collect(Collectors.toList());
		Collections.shuffle(rdirs);

		for (var dir : rdirs) {
			int nx = x + dir[0];
			int ny = y + dir[1];
			if (nx < 0 || nx >= size || ny < 0 || ny >= size || visited[nx][ny])
				continue;
			visited[nx][ny] = true;
			pps[nx][ny].setBackground(Color.white);
			visited[x + dir[0] / 2][y + dir[1] / 2] = true;
			pps[x + dir[0] / 2][y + dir[1] / 2].setBackground(Color.white);
			makeMaze(nx, ny);
		}
	}

	private void addpanel() {
		for (int i = 0; i < size; i++) {
			for (int j = 0; j < size; j++) {
				pps[i][j] = new MyPanel();
				pps[i][j].setBackground(Color.black);
				panel_1.add(pps[i][j]);
			}
		}
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (timer != null && timer.isRunning()) {
				timer.stop();
			}
		}
	}

	class MyPanel extends JPanel {
		int cnt;
		Timer outside;
		Timer timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cnt--;
				repaint();
				if(cnt==0) timer.stop();
				if(outside!=null) outside.start();
			}
		});
		boolean start,end;
		
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			var comp = g2.getComposite();
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f * cnt));
			g2.setColor(Color.cyan);
			g2.fillRect(0, 0, getWidth(), getHeight());
			g2.setComposite(comp);
			if(start||end) {
				g2.setColor(start? Color.red : Color.green.darker());
				g2.fillOval(getWidth()/2-8, getHeight()/2-8, 16, 16);
			}
			g2.dispose();
		}
	}
	private class ThisKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==e.VK_UP) {
				Point next = new Point(myPoint.x-1, myPoint.y);
				if(next.x<0||next.x>=size||next.y<0||next.y>=15||pps[next.x][next.y].getBackground().equals(Color.black)) return;
				pps[myPoint.x][myPoint.y].start =false;
				myPoint = next;
				pps[myPoint.x][myPoint.y].start =true;
				repaint();
			}
			else if(e.getKeyCode()==e.VK_DOWN) {
				Point next = new Point(myPoint.x+1, myPoint.y);
				if(next.x<0||next.x>=size||next.y<0||next.y>=15||pps[next.x][next.y].getBackground().equals(Color.black)) return;
				pps[myPoint.x][myPoint.y].start =false;
				myPoint = next;
				pps[myPoint.x][myPoint.y].start =true;
				repaint();
			}
			else if(e.getKeyCode()==e.VK_LEFT) {
				Point next = new Point(myPoint.x, myPoint.y-1);
				if(next.x<0||next.x>=size||next.y<0||next.y>=15||pps[next.x][next.y].getBackground().equals(Color.black)) return;
				pps[myPoint.x][myPoint.y].start =false;
				myPoint = next;
				pps[myPoint.x][myPoint.y].start =true;
				repaint();
			}
			else if(e.getKeyCode()==e.VK_RIGHT) {
				Point next = new Point(myPoint.x, myPoint.y+1);
				if(next.x<0||next.x>=size||next.y<0||next.y>=15||pps[next.x][next.y].getBackground().equals(Color.black)) return;
				pps[myPoint.x][myPoint.y].start =false;
				myPoint = next;
				pps[myPoint.x][myPoint.y].start =true;
				repaint();
			}
			
			if(myPoint.equals(endPoint)) {
				Random rand = new Random();
				int n = rand.nextInt(99)+1;
				msgInfo(String.format("<html>탈출 성공!<br>%d원이 적립되었습니다.", n));
				try {
					DB.update("user", "point = point + ?", "uno = ?", n,User.uno);
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				dispose();
				previous();
			}
		}
	}
}
