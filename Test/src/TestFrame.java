import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.Queue;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class TestFrame extends JFrame {

	private JPanel contentPane;
	public JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestFrame frame = new TestFrame();
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
	public TestFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		button = new JButton("New button");
		try {
			button.setIcon(new ImageIcon(getBackgroundChangeImage("test.png", 210, 138, Color.orange)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		button.setVerticalTextPosition(3);
		button.setHorizontalAlignment(0);
		button.setBackground(Color.orange);
		button.setBounds(96, 36, 210, 138);
		contentPane.add(button);

	}

	public BufferedImage getBackgroundChangeImage(String path, int w, int h, Color color) throws IOException {
		BufferedImage src = ImageIO.read(new File(path));
		BufferedImage bi = new BufferedImage(w, h, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(src, 0, 0, w, h, 0, 0, src.getWidth(), src.getHeight(), null);
		int bgRGB = bi.getRGB(0, 0);
		replaceBackground(bi, color.getRGB());
//		System.out.println(bgRGB);
//		for (int i = 0; i < w; i++) {
//			for (int j = 0; j < h; j++) {
//				if (isBG(bi.getRGB(i, j), bgRGB)) {
//					bi.setRGB(i, j, color.getRGB());
//				}
//			}
//		}
		return bi;
	}
	private void replaceBackground(BufferedImage image, int newRGB) {
	    int w = image.getWidth();
	    int h = image.getHeight();
	    int bgRGB = image.getRGB(0, 0);

	    boolean[][] visited = new boolean[w][h];
	    Queue<Point> queue = new ArrayDeque<>();

	    // 이미지 네 모서리에서 탐색 시작
	    queue.add(new Point(0, 0));
	    queue.add(new Point(w - 1, 0));
	    queue.add(new Point(0, h - 1));
	    queue.add(new Point(w - 1, h - 1));

	    int[][] direction = {
	        { 1, 0 }, {-1, 0},
	        { 0, 1 }, { 0, -1}
	    };

	    while (!queue.isEmpty()) {
	        Point p = queue.poll();

	        if (p.x < 0 || p.x >= w || p.y < 0 || p.y >= h) {
	            continue;
	        }

	        if (visited[p.x][p.y]) {
	            continue;
	        }

	        visited[p.x][p.y] = true;

	        if (!isBG(image.getRGB(p.x, p.y), bgRGB)) {
	            continue;
	        }

	        image.setRGB(p.x, p.y, newRGB);

	        for (int[] d : direction) {
	            queue.add(new Point(p.x + d[0], p.y + d[1]));
	        }
	    }
	}
	private boolean isBG(int rgb, int bgRGB) {
		if(rgb>>24==0) {
			return true;
		}
		Color a = new Color(rgb);
		Color b = new Color(bgRGB);
		int diff = Math.abs(a.getRed()-b.getRed()) + Math.abs(a.getGreen()-b.getGreen()) + Math.abs(a.getBlue()-b.getBlue());
		return diff <= 40;
	}
}
