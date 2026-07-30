package test;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BooleanSupplier;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.SwingUtilities;

/** 이미지를 백그라운드에서 1장씩 읽어 축소·크롭 후 라벨에 붙인다. 캐시·선로딩 없음. */
public class ImageLoader {

	private static final ExecutorService pool = Executors.newFixedThreadPool(6);

	/**
	 * @param valid 아직 유효한 요청인지 (지난 검색 결과면 false → 읽지도, 붙이지도 않음)
	 */
	public static void load(JLabel target, File file, int w, int h, boolean circle, BooleanSupplier valid) {
		pool.execute(() -> {
			if (!valid.getAsBoolean() || !file.isFile()) return;
			try {
				BufferedImage src = ImageIO.read(file);
				if (src == null) return;
				BufferedImage out = scale(src, w, h, circle);
				src.flush();

				SwingUtilities.invokeLater(() -> {
					if (!valid.getAsBoolean()) return;
					target.setIcon(new ImageIcon(out));
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/** 정사각 중앙 크롭 후 w×h 로 축소 (circle 이면 원형으로 잘라냄) */
	private static BufferedImage scale(BufferedImage src, int w, int h, boolean circle) {
		BufferedImage out = new BufferedImage(w, h, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g = out.createGraphics();
		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
		if (circle) g.setClip(new Ellipse2D.Float(0, 0, w, h));

		int side = Math.min(src.getWidth(), src.getHeight());
		int sx = (src.getWidth() - side) / 2, sy = (src.getHeight() - side) / 2;
		g.drawImage(src, 0, 0, w, h, sx, sy, sx + side, sy + side, null);
		g.dispose();
		return out;
	}
}
