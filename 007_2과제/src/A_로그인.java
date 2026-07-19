import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.awt.geom.NoninvertibleTransformException;
import java.awt.geom.Path2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.beans.Beans;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.CellRendererPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class A_로그인 extends BF {
	public MapPanel panel;

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

	public A_로그인() {
		getContentPane();
		setTitle("로그인");
		setBounds(100, 100, 324, 235);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setPreferredSize(new Dimension(797, 964));
		getContentPane().setLayout(null);

		panel = new MapPanel();
		panel.setBackground(new Color(250, 245, 235));
		getContentPane().add(panel);
		pack();
	}

	class MapPanel extends JPanel {
		List<Path2D> paths = new ArrayList<Path2D>();
		Map<Path2D, RegionStyle> pathInfo = new HashMap<Path2D, RegionStyle>();
		Rectangle2D.Double viewBox = new Rectangle2D.Double(81.27, -2, 797.46, 964);
		AffineTransform af = new AffineTransform() {
			{
				translate(-81.27, -2);
			}
		};
		Path2D target;
		boolean zoom = false;
		JLabel lblPrev;

		public void setZoom(boolean zoom) {
			this.zoom = zoom;
			if (zoom) {
				lblPrev.setVisible(true);
				var info = pathInfo.get(target);
				var pp = new A_로그인패널(info.color, info.name, info.no);
				
				pp.setBounds((getWidth() - 370) / 2, (getHeight() - 375) / 2, 370, 375);
				add(pp);
			} else {
				lblPrev.setVisible(false);
				target = null;
				zoom = false;
				if (getComponentCount() > 1)
					remove(1);
			}
			repaint();
		}

		public MapPanel() {
			if (Beans.isDesignTime())
				return;
			setSize(new Dimension(797, 964));
			try {
				getMapPaths();
			} catch (SAXException | IOException | ParserConfigurationException e) {
				e.printStackTrace();
			}
			setLayout(null);
			add(lblPrev = new JLabel("←이전") {
				{
					setFont(new Font("맑은 고딕", 0, 13));
					setForeground(Color.gray);
					setBounds(20, 20, 40, 20);
				}
			});
			lblPrev.setVisible(false);

			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(zoom) return;
					setZoom(target != null);
				}
			});
			addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					if (zoom) return;
					var p = e.getPoint();
					try {
						Point2D realPoint = af.inverseTransform(p, null);
						target = paths.stream().filter(x -> x.contains(realPoint)).findFirst().orElse(null);
					} catch (NoninvertibleTransformException e1) {
						e1.printStackTrace();
					}
					repaint();
				}
			});
			lblPrev.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					setZoom(false);
				};
			});
		}

		@Override
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			Graphics2D g2 = (Graphics2D) g.create();
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			
			if (zoom) {
				double padding = 100;
				var bounds = target.getBounds();
				double scaleX = (getWidth() - padding * 2) / bounds.getWidth();
				double scaleY = (getHeight() - padding * 2) / bounds.getHeight();
				double scale = Math.min(scaleX, scaleY);
				
				g2.translate(getWidth() / 2.0, getHeight() / 2.0);
				g2.scale(scale, scale);
				g2.translate(-bounds.getCenterX(), -bounds.getCenterY());
				g2.setColor(pathInfo.get(target).color);
				g2.fill(target);
				g2.setColor(Color.black);
				g2.draw(target);
				
				drawString(g2,target);
			} else {
				g2.translate(-viewBox.getX(), -viewBox.getY());
				for (var path : paths) {
					g2.setColor(Color.black);
					g2.draw(path);
					var color = path == target ? highlight(pathInfo.get(path).color) : pathInfo.get(path).color;
					g2.setColor(color);
					g2.fill(path);
				}
				for (var path : paths) {
					var info = pathInfo.get(path);
					if(info.name.equals("경상북도")) {
						g2.setFont(new Font("맑은 고딕", 1, 11));
						var fm =g2.getFontMetrics();
						int x = (int) (path.getBounds().getCenterX()- fm.stringWidth(info.name)/2)-100;
						int y = (int) (path.getBounds().getCenterY()+10)+20;
						var outline = g2.getFont().createGlyphVector(g2.getFontRenderContext(), info.name).getOutline(x,y);
						
						g2.setStroke(new BasicStroke(3f));
						g2.setColor(Color.white);
						g2.draw(outline);
						g2.setStroke(new BasicStroke(1f));
						g2.setColor(Color.black);
						g2.fill(outline);
					}
					else if(info.name.equals("전라남도")) {
						g2.setFont(new Font("맑은 고딕", 1, 11));
						var fm =g2.getFontMetrics();
						int x = (int) (path.getBounds().getCenterX()- fm.stringWidth(info.name)/2)+20;
						int y = (int) (path.getBounds().getCenterY()+10);
						var outline = g2.getFont().createGlyphVector(g2.getFontRenderContext(), info.name).getOutline(x,y);
						
						g2.setStroke(new BasicStroke(3f));
						g2.setColor(Color.white);
						g2.draw(outline);
						g2.setStroke(new BasicStroke(1f));
						g2.setColor(Color.black);
						g2.fill(outline);
					}
					else {
						drawString(g2, path);
					}
				}
			}
		}

		private void drawString(Graphics2D g2, Path2D target) {
			g2.setFont(new Font("맑은 고딕", 1, 11));
			var info = pathInfo.get(target);
			var fm =g2.getFontMetrics();
			int x = (int) (target.getBounds().getCenterX()- fm.stringWidth(info.name)/2);
			int y = (int) (target.getBounds().getCenterY()+10);
			var outline = g2.getFont().createGlyphVector(g2.getFontRenderContext(), info.name).getOutline(x,y);
			
			g2.setStroke(new BasicStroke(3f));
			g2.setColor(Color.white);
			g2.draw(outline);
			g2.setStroke(new BasicStroke(1f));
			g2.setColor(Color.black);
			g2.fill(outline);
		}

		private Color highlight(Color color) {
			int r = (int) Math.min(color.getRed() * 1.2, 255);
			int g = (int) Math.min(color.getGreen() * 1.2, 255);
			int b = (int) Math.min(color.getBlue() * 1.2, 255);
			return new Color(r, g, b);
		}

		private void getMapPaths() throws SAXException, IOException, ParserConfigurationException {
			Document doc = DocumentBuilderFactory.newInstance().newDocumentBuilder()
					.parse(new File("./datafiles/backimg/map.svg"));
			var tags = doc.getElementsByTagName("path");
			for (int i = 0; i < tags.getLength(); i++) {
				Element tag = (Element) tags.item(i);
				Path2D path = parsePath(tag.getAttribute("d"));
				paths.add(path);
				pathInfo.put(path, RegionStyle.findById(tag.getAttribute("id")));
			}
		}

		private Path2D parsePath(String d) {
			Path2D path = new Path2D.Double();
			String[] paths = d.split("Z");
			for (int i = 0; i < paths.length; i++) {
				var xy = Arrays.stream(paths[i].substring(1).split("[L,]")).mapToDouble(java.lang.Double::parseDouble)
						.toArray();
				path.moveTo(xy[0], xy[1]);
				for (int j = 2; j < xy.length; j += 2) {
					path.lineTo(xy[j], xy[j + 1]);
				}
				path.closePath();
			}
			return path;
		}
	}

	enum RegionStyle {
		충남("Chungnam", "충청남도", "#B3AC97", 1), 제주("Jeju", "제주도", "#98D1A7", 2), 경남("Gyeongnam", "경상남도", "#E5D1AF", 3),
		경북("Gyeongbuk", "경상북도", "#D6BADD", 4), 전북("Jeonbuk", "전라북도", "#A0D6D6", 5),
		충북("Chungbuk", "충청북도", "#E1C7CC", 6), 강원("Gangwon", "강원도", "#C1B1B0", 7), 경기("Gyeonggi", "경기도", "#97B5B4", 8),
		전남("Jeonnam", "전라남도", "#CEDBD8", 9), 울산("Ulsan", "울산광역시", "#DBA1A8", 10), 부산("Busan", "부산광역시", "#B3A4BD", 11),
		대구("Daegu", "대구광역시", "#C6CEDD", 12), 대전("Daejeon", "대전광역시", "#D2D4BB", 13),
		인천("Incheon", "인천광역시", "#9EB7B9", 14), 서울("Seoul", "서울특별시", "#AECBE5", 15),
		광주("Gwangju", "광주광역시", "#ABC5DC", 16), 세종("Sejong", "세종시", "#FFFDD0", 17);

		final String id;
		final String name;
		final Color color;
		final int no;

		private RegionStyle(String id, String name, String color, int no) {
			this.id = id;
			this.name = name;
			this.color = Color.decode(color);
			this.no = no;
		}

		public static RegionStyle findById(String target) {
			for (var regionstyle : values()) {
				if (regionstyle.id.equals(target))
					return regionstyle;
			}
			return null;
		}
	}
}
