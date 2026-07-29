import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
public class B_홈 extends BP {
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JPanel panel_4;
	public JLabel label;
	public JLabel label_1;
	public JPanel panel_6;
	public JLabel label_2;
	public JLabel label_3;
	public JPanel panel_7;
	public JLabel label_4;
	public JLabel label_5;

	/**
	 * Create the panel.
	 */
	public B_홈(MainFrame bf) {
		super(bf);
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(12, 10, 898, 200);
		add(panel);
		panel.setLayout(new GridLayout(1, 0, 10, 0));
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel("광고");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_1.setBounds(12, 10, 57, 15);
		panel_1.add(label_1);
		
		panel_6 = new ADPanel();
		panel_6.setBounds(12, 35, 268, 155);
		panel_1.add(panel_6);
		
		panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		label_2 = new JLabel(getIcon("logo/cash.png",50,50));
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setText("<html><br><br><div align = 'center'><font size = '4'>충전소</font><br><font color = 'gray' size = '3'>포인트 충전하기");
		label_2.setHorizontalTextPosition(SwingConstants.CENTER);
		label_2.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_2.add(label_2, BorderLayout.CENTER);
		
		panel_3 = new JPanel();
		panel_3.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.add(panel_3);
		panel_3.setLayout(null);
		
		label_3 = new JLabel("바로가기");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_3.setBounds(12, 10, 237, 24);
		panel_3.add(label_3);
		
		panel_7 = new JPanel();
		panel_7.setBounds(12, 44, 268, 146);
		panel_3.add(panel_7);
		panel_7.setLayout(new GridLayout(0, 2, 10, 0));
		
		label_4 = new JLabel("<html><br><div align = 'center'><font size = '4'>미로</font><br><font color = 'gray' size = '3'>포인트 적립");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setIcon(getIcon("logo/maze.png",40,40));
		label_4.setHorizontalTextPosition(SwingConstants.CENTER);
		label_4.setVerticalTextPosition(SwingConstants.BOTTOM);
		label_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_7.add(label_4);
		
		label_5 = new JLabel("<html><br><div align = 'center'><font size = '4'>경품</font><br><font color = 'gray' size = '3'>룰렛 뽑기");
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setIcon(getIcon("logo/roulette.png",40,40));
		label_5.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_5.setHorizontalTextPosition(SwingConstants.CENTER);
		label_5.setVerticalTextPosition(SwingConstants.BOTTOM);
		panel_7.add(label_5);
		
		panel_4 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				
				Image buf = getBuf();
				g2.drawImage(buf, 0, 0,null);
				g2.dispose();
			}
		};
		panel_4.addMouseListener(new Panel_4MouseListener());
		panel_4.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_4.setBounds(12, 220, 898, 246);
		add(panel_4);
		panel_4.setLayout(null);
		
		label = new JLabel("사람들이 많이 구매하는 카테고리");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label.setBounds(12, 10, 237, 24);
		panel_4.add(label);
		
		getData();
		
		new Timer(10, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				over-=5;
				panel_4.repaint();
				if(over<=0) {
					double max = rate.stream().mapToDouble(x->x).max().getAsDouble();
					selIdx = rate.indexOf(max);
					((Timer)e.getSource()).stop();
				}
			}
		}).start(); 
	}
	List<Double> rate = new ArrayList<Double>();
	List<String> name = new ArrayList<>();
	Color[] c;
	Arc2D[] arcs;
	int selIdx = -1, over =  360;
	private BufferedImage getBuf() {
		var bi = new BufferedImage(panel_4.getWidth(), panel_4.getHeight(), 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		double ang = 90;
		arcs = new Arc2D.Double[rate.size()];
		int bx = bi.getWidth()/2-90, by = bi.getHeight()/2, r = 80;
		g2.setStroke(new BasicStroke(2f));
		g2.setFont(new Font("맑은 고딕",1,11));
		for (int i = 0; i < rate.size(); i++) {
			double deg = rate.get(i)*360;
			arcs[i] = new Arc2D.Double(bx-r, by-r, r+r, r+r, ang, -deg, 2);
			if(selIdx==i)
				arcs[i] = new Arc2D.Double(bx-r-10, by-r-10, r+r+20, r+r+20, ang, -deg, 2);
			g2.setColor(c[i]);
			g2.fill(arcs[i]);
			g2.setColor(Color.white);
			g2.draw(arcs[i]);
			ang -=deg;
			
			g2.setColor(c[i]);
			int byy= by-(20*rate.size())/2 + 20*i;
			g2.fillRect(bx+r+20, byy, 10, 10);
			g2.setColor(Color.black);
			if(selIdx==i)
				g2.setColor(Color.blue);
			g2.drawString(name.get(i), bx+r+40, byy+10);
		}
		g2.setColor(Color.white);
		g2.fillOval(bx-r/2, by-r/2, r, r);
		
		if(over>=0) {
			g2.setColor(Color.white);
			g2.fillArc(bx-r-10, by-r-10, r+r+20, r+r+20, 90, over);
		}
		return bi;
	}
	private void getData() {
		try (var rs = DB.res("select category.*, sum(`order`.quantity) sum from `order` join product using(pno) right join category using(cno) group by cno;")) {
			while(rs.next()) {
				rate.add(rs.getDouble("sum"));
				name.add(rs.getString("cname"));
			}
			double sum = rate.stream().mapToDouble(x->x).sum();
			rate = rate.stream().mapToDouble(x->x/sum).boxed().collect(Collectors.toList());
			c = new Color[rate.size()];
			for (int i = 0; i < c.length; i++) {
				c[i] = Color.getHSBColor((float) Math.random(), 0.7f, 0.9f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			bf.showPage(new H_경품());
		}
	}
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			bf.showPage(new E_충전소());
		}
	}
	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			bf.showPage(new I_미로());
		}
	}
	class ADPanel extends JPanel{
		float alpha;
		int hold, idx;
		boolean fade;
		public ADPanel() {
			new Timer(40, new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if(!fade) {
						hold++;
						if(hold>=70) {
							hold = 0;
							fade =true;
						}
					}
					else {
						if(alpha<0.7f)
							alpha+=0.05;
						else {
							alpha = 0f;
							fade = false;
							idx = ++idx %5;
						}
					}
					repaint();
				}
			}).start();
		}
		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			Image img = getIcon("advertise/"+(idx+1)+".jpg", getWidth(), getHeight()).getImage();
			g2.drawImage(img, 0, 0, null);
			if(fade) {
				var ori = g2.getComposite();
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				int fadeIdx = (idx+1)%5;
				var fadeImg = getIcon("advertise/"+(fadeIdx+1)+".jpg", getWidth(), getHeight()).getImage();
				g2.drawImage(fadeImg, 0, 0, null);
				g2.setComposite(ori);
			}
			int bx = (getWidth()-((6+6)*5))/2, by = getHeight()-10;
			for (int i = 0; i < 5; i++) {
				g2.setColor(Color.lightGray);
				if(i==idx) g2.setColor(Color.blue);
				g2.fillOval(bx-3+12*i, by-3, 6, 6);
			}
			g2.dispose();
		}
	}
	private class Panel_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			var arc = Arrays.stream(arcs).filter(x->x.contains(e.getPoint())).findFirst().orElse(null);
			if(arc!=null) {
				selIdx = Arrays.stream(arcs).collect(Collectors.toList()).indexOf(arc);
				panel_4.repaint();
			}
		}
	}
}
