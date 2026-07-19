import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

public class B_홈 extends BP {
	public JPanel panel_4;
	public JPanel panel_5;
	public JPanel panel_6;
	public JPanel panel_7;
	public JPanel panel_8;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JPanel panel_9;
	public JLabel label_7;
	public JLabel label_8;
	
	public B_홈() {
		text = "홈";
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		panel_4 = new JPanel();
		panel_4.setBackground(new Color(227, 227, 227));
		panel_4.setBounds(12, 14, 851, 211);
		add(panel_4);
		panel_4.setLayout(new GridLayout(1, 0, 20, 0));
		
		panel_5 = new JPanel();
		panel_5.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel_4.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		
		panel_6 = new JPanel();
		panel_6.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel_4.add(panel_6);
		panel_6.setLayout(new BorderLayout(0, 0));
		
		label_5 = new JLabel(getIcon("logo/cash.png", 50,50));
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setText(String.format("<html><br><br><div align = 'center'><font size = 5>충전소</font><br><font size = '4' color = 'gray'>포인트 충전하기"));
		label_5.setVerticalTextPosition(SwingConstants.CENTER);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setHorizontalTextPosition(0);
		label_5.setVerticalTextPosition(3);
		panel_6.add(label_5, BorderLayout.CENTER);
		
		panel_7 = new JPanel();
		panel_7.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel_4.add(panel_7);
		panel_7.setLayout(null);
		
		label_6 = new JLabel("바로가기");
		label_6.setBounds(12, 14, 99, 15);
		panel_7.add(label_6);
		
		panel_9 = new JPanel();
		panel_9.setBounds(12, 43, 246, 154);
		panel_7.add(panel_9);
		panel_9.setLayout(new GridLayout(1, 0, 10, 0));
		
		label_7 = new JLabel(String.format("<html><br><div align = 'center'><font size = 5>미로</font><br><font size = '4' color = 'gray'>포인트 적립"));
		label_7.addMouseListener(new Label_7MouseListener());
		label_7.setIcon(getIcon("logo/maze.png",40,40));
		label_7.setBorder(new LineBorder(new Color(227, 227, 227)));
		label_7.setVerticalTextPosition(SwingConstants.CENTER);
		label_7.setHorizontalAlignment(SwingConstants.CENTER);
		label_7.setHorizontalTextPosition(0);
		label_7.setVerticalTextPosition(3);
		panel_9.add(label_7);
		
		label_8 = new JLabel(String.format("<html><br><div align = 'center'><font size = 5>경품</font><br><font size = '4' color = 'gray'>룰렛 뽑기"));
		label_8.addMouseListener(new Label_8MouseListener());
		label_8.setIcon(getIcon("logo/roulette.png",40,40));
		label_8.setBorder(new LineBorder(new Color(227, 227, 227)));
		label_8.setVerticalTextPosition(SwingConstants.CENTER);
		label_8.setHorizontalAlignment(SwingConstants.CENTER);
		label_8.setHorizontalTextPosition(0);
		label_8.setVerticalTextPosition(3);
		panel_9.add(label_8);
		
		panel_8 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				var img = getBuf();
				g2.drawImage(img, 0, 0, null);
			}
		};
		panel_8.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel_8.addMouseListener(new Panel_8MouseListener());
		panel_8.setBounds(12, 239, 851, 230);
		add(panel_8);
		panel_8.setLayout(null);
		
		label_4 = new JLabel("사람들이 많이 구매하는 카테고리");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_4.setBounds(12, 14, 274, 24);
		panel_8.add(label_4);
		
		loadA();
		getData();
		new Timer(5, e->{
			rot-=10;
			panel_8.repaint();
			if(rot<=0) {
				rot = 0;
				((Timer)e.getSource()).stop();
				panel_8.repaint();
			}
		}).start();
	}
	double rot = 360;
	List<Double> data = new ArrayList<>();
	List<String> name = new ArrayList<>();
	Arc2D.Double[] arcs;
	Color[] color;
	int idx = -1;
	private void getData() {
		try (var rs = DB.res("select category.*, count(*) from product left join `order` using(pno) left join category using(cno) group by cno order by cno")) {
			while(rs.next()) {
				data.add(rs.getDouble(3));
				name.add(rs.getString(2));
			}
			var sum = data.stream().mapToDouble(x->x).sum();
			data = data.stream().map(x->x/sum).collect(Collectors.toList());
			arcs = new Arc2D.Double[data.size()];
			color = new Color[data.size()];
			for (int i = 0; i < arcs.length; i++) {
				color[i] = Color.getHSBColor((float) Math.random(), 0.6f, 0.9f);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select category.*, count(*) from product left join `order` using(pno) left join category using(cno) group by cno order by count(*) desc, cno")) {
			rs.next();
			idx = rs.getInt(1)-1;
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public BufferedImage getBuf() {
		BufferedImage bi = new BufferedImage(panel_8.getWidth(), panel_8.getHeight(), 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		double ang = 90;
		int bx = panel_8.getWidth()/2, by = panel_8.getHeight()/2;
		int r = panel_8.getHeight()/3;
		
		g2.setStroke(new BasicStroke(2f));
		g2.setFont(new Font("맑은 고딕", 1, 12));
		
		for (int i = 0; i < name.size(); i++) {
			double deg = data.get(i) * 360;
			if(idx==i&&rot==0)
				arcs[i] = new Arc2D.Double(bx-r-r-15, by-r-15, r+r+30,r+r+30,ang, -deg, 2);
			else
				arcs[i] = new Arc2D.Double(bx-r-r, by-r, r+r,r+r,ang, -deg, 2);
			g2.setColor(color[i]);
			g2.fill(arcs[i]);
			int h = by-r;
			g2.fillRect(bx+20, h+(20)*i, 15, 15);
			g2.setColor(idx==i ? new Color(55,128,255) : Color.black);
			g2.drawString(name.get(i), bx+40, h+12+20*i);
			g2.setColor(Color.white);
			g2.draw(arcs[i]);
			ang -= deg;
		}
		
		g2.setColor(Color.white);
		g2.fillOval(bx-35-r, by-35, 70, 70);
		
		g2.fill(new Arc2D.Double(bx-r-r, by-r, r+r, r+r, 90, rot, 2));
		return bi;
	}
	
	private void loadA() {
		panel_5.add(new Advertisement());
	}

	class Advertisement extends JPanel{
		public Advertisement() {
			setLayout(new BorderLayout());
			add(new JLabel("  광고"), "North");
			add(new AdPanel());
		}
		class AdPanel extends JPanel{
			int currentIdx= 0, cnt =0;
			float alpha = 0;
			boolean hold = true;
			public AdPanel() {
				var timer = new Timer(40, e->{
					if(hold) {
						cnt++;
						if(cnt >= 70) {
							cnt = 0;
							hold = false;
						}
					}
					else {
						if(alpha <= 0.7f) {
							alpha+=0.05;
						}
						else {
							currentIdx = ++currentIdx%5;
							alpha = 0f;
							hold = true;
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
				int w = getWidth()-40;
				int h = getHeight() - 30;
				var img = getIcon("advertise/"+(currentIdx+1)+".jpg", w, h).getImage();
				int nextIdx = (currentIdx+1)%5;
				var currentImg = getIcon("advertise/"+(currentIdx+1)+".jpg", w, h).getImage();
				var nextImg = getIcon("advertise/"+(nextIdx+1)+".jpg",w,h).getImage();
				var comp = g2.getComposite();
				g2.drawImage(currentImg, 20, 20, null);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
				g2.drawImage(nextImg, 20, 20, null);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				int totw = (6*5+6*4);
				int bx = (getWidth()-totw)/2;
				g2.setComposite(comp);
				for (int i = 0; i < 5; i++) {
					g2.setColor(i==currentIdx? new Color(55,125,225) : Color.gray);
					g2.fillOval(bx+(6+6)*i, getHeight()-20, 6, 6);
				}
				g2.dispose();
			}
		}
	}
	
	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			MainFrame.instance.showPage(new E_충전소());
		}
	}
	private class Label_7MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			MainFrame.instance.showPage(new I_미로());
		}
	}
	private class Label_8MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			MainFrame.instance.showPage(new H_경품());
		}
	}
	private class Panel_8MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			idx = -1;
			for (int i = 0; i < arcs.length; i++) {
				if(arcs[i].contains(e.getPoint())) {
					idx = i;
					break;
				}
			}
			panel_8.repaint();
		}
	}
}
