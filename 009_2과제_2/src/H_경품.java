import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class H_경품 extends BF {
	public JPanel panel;
	public JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					H_경품 frame = new H_경품();
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
	public H_경품() {
		addWindowListener(new ThisWindowListener());
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		
		panel = new JPanel() {
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
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 611, 410);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("▼");
		label.setFont(new Font("굴림", Font.BOLD, 31));
		label.setForeground(Color.RED);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(266, 22, 84, 56);
		panel.add(label);
		
		button = new JButton("경품 뽑기!");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		button.setBounds(11, 425, 611, 31);
		getContentPane().add(button);
		setTitle("경품");
		setBounds(100, 100, 651, 507);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	
		getdata();
		load();
	}

	Arc2D[] arcs;
	private BufferedImage getBuf() {
		var bi = new BufferedImage(panel.getWidth(), panel.getHeight(), 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setStroke(new BasicStroke(2f));
		g2.setFont(new Font("맑은 고딕",1,12));
		int bx = panel.getWidth()/2, by = panel.getHeight()/2, r = 160;
		double ang = 90+baseAng;
		arcs = new Arc2D.Double[chances.size()];
		for (int i = 0; i < chances.size(); i++) {
			double deg = rates.get(i)*360;
			arcs[i] = new Arc2D.Double(bx-r, by-r, r+r, r+r, ang, -deg, 2);
			g2.setColor(c[i]);
			g2.fill(arcs[i]);
			g2.setColor(Color.gray);
			g2.draw(arcs[i]);
			ang -= deg;
		}
		ang = 90+baseAng;
		var ori =g2.getTransform();
		for (int i = 0; i < arcs.length; i++) {
			double deg = rates.get(i)*360;
			g2.setColor(Color.white);
			var fm = g2.getFontMetrics();
			g2.rotate(-Math.toRadians(ang-deg/2),bx,by);
			g2.drawString(names.get(i), bx+r/2-fm.stringWidth(names.get(i))/2, by+6);
			g2.setTransform(ori);
			ang -= deg;
		}
		
		g2.dispose();
		return bi;
	}
	private void load() {
		try {
			int chance = DB.select("select chance from user where uno =?",Integer.class, User.uno);
			button.setText(String.format("경품 뽑기! (%d회 남음)", chance));
			if(chance==0)
				button.setEnabled(false);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	List<Double> rates = new ArrayList<Double>();
	List<String> names = new ArrayList<String>();
	List<Integer> chances = new ArrayList<Integer>();
	Color[] c;
	private void getdata() {
		try (var rs = DB.res("select * from chanceitem;")) {
			while(rs.next()) {
				int chance = Integer.parseInt(rs.getString("ciname").replaceAll("\\D", ""));
				chances.add(chance);
				names.add(rs.getString("ciname"));
				rates.add(rs.getDouble("chance"));
			}
			c = new Color[names.size()];
			for (int i = 0; i < names.size(); i++) {
				c[i] = Color.getHSBColor((float) Math.random(), 0.3f, 0.9f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			startRoulette();
		}

	}
	Timer timer;
	double speed;
	Random r= new Random();
	double baseAng= r.nextInt(360);
	public JLabel label;
	private void startRoulette() {
		button.setEnabled(false);
		speed = r.nextInt(360);
		Point targetPoint = new Point(panel.getWidth()/2,panel.getHeight()/2-140);
		timer = new Timer(20, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if(speed>0.1) {
					baseAng += speed;
					speed *= 0.97;
				}
				else {
					timer.stop();
					int i = 0;
					for (var arc: arcs) {
						if(arc.contains(targetPoint)) {
							msgInfo(String.format("<html>축하합니다!<br>%s에 당첨되셨습니다!", names.get(i)));
							try {
								DB.update("user", "point = point + ?,chance = chance -1", "uno = ?",  chances.get(i), User.uno);
							} catch (SQLException e1) {
								e1.printStackTrace();
							}
							break;
						}
						i++;
					}
					load();
				}
				panel.repaint();
			}
		});
		timer.start();
	}
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if(timer!=null&&timer.isRunning())timer.stop();
		}
	}
}

