import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.Beans;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

public class A_로그인 extends BF {
	public JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_로그인 frame = new A_로그인();
					frame.setLocationRelativeTo(null);
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

	Rectangle2D viewBox = new Rectangle2D.Double(81.27, -2, 797.46, 964);

	public A_로그인() {
		setTitle("로그인");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		panel = new MyPanel();
		panel.setPreferredSize(new Dimension((int) viewBox.getWidth(), (int) viewBox.getHeight()));
		getContentPane().add(panel, BorderLayout.CENTER);

		pack();
	}

	class MyPanel extends JPanel {
		List<Path2D> paths = new ArrayList<Path2D>();
		Map<Path2D, RegionStyle> styles = new HashMap<>();
		Path2D target;
		boolean zoom;
		private JLabel jl;

		private void setzoom(boolean b) {
			zoom = b;
			if (zoom) {
				add(jl);
				var style = styles.get(target);
				var pp = new A_로그인패널(style.no, style.name);
				pp.setLocation((getWidth()-pp.getWidth())/2, (getHeight()-pp.getHeight())/2);
				add(pp);
			} else {
				removeAll();
			}
			repaint();
		}

		public MyPanel() {
			if (Beans.isDesignTime())
				return;
			setBackground(new Color(243, 241, 233));
			setLayout(null);

			jl = new JLabel("←이전");
			jl.setBounds(20, 20, 40, 20);
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					setzoom(false);
				}
			});

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (target != null) {
						setzoom(true);
					}
				}
			});
			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					if(zoom) return;
					var af = new AffineTransform();
					af.translate(-viewBox.getX(), -viewBox.getY());
					try {
						var rp = af.inverseTransform(e.getPoint(), null);
						var path = paths.stream().filter(x -> x.contains(rp)).findFirst().orElse(null);
						if (path != null) {
							target = path;
						} else {
							target = null;
						}
					} catch (NoninvertibleTransformException e1) {
					}
					repaint();
				}
			});
			try {
				getPaths();
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
		}

		private void getPaths() throws SAXException, IOException, ParserConfigurationException {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new File("./datafiles/backimg/map.svg"));
			var tags = doc.getElementsByTagName("path");
			for (int i = 0; i < tags.getLength(); i++) {
				Element tag = (Element) tags.item(i);
				Path2D path = parsePath(tag.getAttribute("d"));
				paths.add(path);
				styles.put(path, RegionStyle.findById(tag.getAttribute("id")));
			}
		}

		private Path2D parsePath(String attribute) {
			Path2D path = new Path2D.Double();
			var ps = attribute.split("Z");
			for (String p : ps) {
				var xy = Arrays.stream(p.substring(1).split("[L,]")).mapToDouble(Double::parseDouble).toArray();
				path.moveTo(xy[0], xy[1]);
				for (int i = 2; i < xy.length; i += 2) {
					path.lineTo(xy[i], xy[i + 1]);
				}
			}
			return path;
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setFont(new Font("맑은 고딕", 1, 12));

			if (zoom) {
				var rect = target.getBounds();
				int padding = 100;
				double scaleX = (getWidth()-padding)/rect.getWidth();
				double scaleY = (getHeight()-padding)/rect.getHeight();
				double scale = Math.min(scaleX, scaleY);
				
				var ori = g2.getTransform();
				
				g2.translate(getWidth()/2, getHeight()/2);
				g2.scale(scale, scale);
				g2.translate(-rect.getCenterX(), -rect.getCenterY());
				
				var style = styles.get(target);
				g2.setColor(style.c);
				g2.fill(target);
				g2.setColor(Color.black);
				g2.draw(target);
				
				drawString(g2, target, style);
			} else {
				g2.translate(-viewBox.getX(), -viewBox.getY());
				for (var path : paths) {
					var style = styles.get(path);
					g2.setColor(path == target ? highlight(style.c) : style.c);
					g2.fill(path);
					g2.setColor(Color.black);
					g2.draw(path);
				}
				for (var path : paths) {
					var style = styles.get(path);
					drawString(g2,path, style);
				}
			}

			g2.dispose();
		}

		private void drawString(Graphics2D g2, Path2D path, A_로그인.RegionStyle style) {
			var rect = path.getBounds();
			int x = (int) (rect.getCenterX() - g2.getFontMetrics().stringWidth(style.name) / 2);
			int y = (int) (rect.getCenterY() + 6);
			
			if(style.name.equals("경상북도")) {
				x -=100;
				y+=30;
			}
			
			var outline = g2.getFont().createGlyphVector(g2.getFontRenderContext(), style.name).getOutline(x,
					y);
			g2.setStroke(new BasicStroke(2f));
			g2.setColor(Color.white);
			g2.draw(outline);
			g2.setStroke(new BasicStroke(1f));
			g2.setColor(Color.black);
			g2.fill(outline);
		}

		private Color highlight(Color c) {
			int r = (int) Math.min(c.getRed() * 1.2, 255);
			int g = (int) Math.min(c.getGreen() * 1.2, 255);
			int b = (int) Math.min(c.getBlue() * 1.2, 255);
			return new Color(r, g, b);
		}
	}

	enum RegionStyle {
		충남(1, "Chungnam", "충청남도", 0xb3ac97), 제주(2, "Jeju", "제주도", 0x98d1a7), 경남(3, "Gyeongnam", "경상남도", 0xe5d1af),
		경북(4, "Gyeongbuk", "경상북도", 0xd6badd), 전북(5, "Jeonbuk", "전라북도", 0xa0d6d6), 충북(6, "Chungbuk", "충청북도", 0xe1c7cc),
		강원(7, "Gangwon", "강원도", 0xceb1b0), 경기(8, "Gyeonggi", "경기도", 0x97b5b4), 전남(9, "Jeonnam", "전라남도", 0xcedbd8),
		울산(10, "Ulsan", "울산광역시", 0xdba1a8), 부산(11, "Busan", "부산광역시", 0xb3a4bd), 대구(12, "Daegu", "대구광역시", 0xc6cedd),
		대전(13, "Daejeon", "대전광역시", 0xd2d4bb), 인천(14, "Incheon", "인천광역시", 0x9eb7b9), 서울(15, "Seoul", "서울특별시", 0xaecbe5),
		광주(16, "Gwangju", "광주광역시", 0xabc5dc), 세종(17, "Sejong", "세종시", 0xfffdd0);

		final int no;
		final String id, name;
		final Color c;

		private RegionStyle(int no, String id, String name, int c) {
			this.no = no;
			this.id = id;
			this.name = name;
			this.c = new Color(c);
		}

		public static RegionStyle findById(String id) {
			for (var s : values()) {
				if (s.id.equals(id)) {
					return s;
				}
			}
			return null;
		}
	}
}
