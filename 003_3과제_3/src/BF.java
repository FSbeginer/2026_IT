import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public class BF extends JFrame {
	public static Stack<BF> prev = new Stack<>();
	static {
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.background", Color.white);
	}

	public BF() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				previous();
			}
		});
	}

	public static void msgInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", 1);
	}

	public static void msgErr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", 0);
	}

	public static ImageIcon getIcon(String path) {
		return new ImageIcon("./datafiles/" + path);
	}

	public static ImageIcon getIcon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon("./datafiles/" + path).getImage().getScaledInstance(w, h, 4));
	}

	public void updateForm() {
	}

	public void previous() {
		if (prev.isEmpty())
			return;
		prev.peek().updateForm();
		prev.pop().setVisible(true);
	}

	public void showPage(BF next) {
		prev.add(this);
		setVisible(false);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
}

class User {
	public static int uno = 1;
}

class Helper {
	private static final ExecutorService services = Executors.newFixedThreadPool(6);
//	private static final Object KEY = new Object();
	public static void getImage(String path, int w, int h, boolean circle, Consumer<Image> con) {
		services.execute(() -> {
//			var file = new File("./datafiles/" + path);
			try {
				BufferedImage src;
//				synchronized (KEY) {
				src = ImageIO.read(new ByteArrayInputStream(Files.readAllBytes(Path.of("./datafiles/"+path))));
//				}
				var out = scale(src, w, h, circle);
				SwingUtilities.invokeLater(() -> con.accept(out));
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}

	private static BufferedImage scale(BufferedImage src, int w, int h, boolean circle) {
		var bi = new BufferedImage(w, h, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		if (circle)
			g2.clip(new Ellipse2D.Double(0, 0, w, h));
		g2.drawImage(src, 0, 0, w, h, 0, 0, src.getWidth(), src.getHeight(), null);

		g2.dispose();
		return bi;
	}
}

class DB {
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/itgram?serverTimezone=Asia/Seoul", "root",
					"1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PreparedStatement pre(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}

	public static void preSet(PreparedStatement pre, Object... objects) throws SQLException {
		if (objects == null)
			return;
		int i = 1;
		for (Object object : objects) {
			pre.setObject(i++, object);
		}
	}

	public static ResultSet res(String sql) throws SQLException {
		return pre(sql).executeQuery();
	}

	public static ResultSet res(String sql, Object... objects) throws SQLException {
		var pre = pre(sql);
		preSet(pre, objects);
		return pre.executeQuery();
	}

	public static void insert(String table, Object... objects) throws SQLException {
		var pre = pre(String.format("insert into %s values(%s)", table,
				String.join(",", Collections.nCopies(objects.length, "?"))));
		preSet(pre, objects);
		pre.execute();
	}

	public static void delete(String table, String where, Object... objects) throws SQLException {
		var pre = pre(String.format("delete from %s where %s", table, where));
		preSet(pre, objects);
		pre.execute();
	}

	public static void update(String table, String sql, String where, Object... objects) throws SQLException {
		var pre = pre(String.format("update %s set %s where %s", table, sql, where));
		preSet(pre, objects);
		pre.execute();
	}

	public static <T> T select(String sql, Class<T> type, Object... objects) throws SQLException {
		var rs = res(sql, objects);
		rs.next();
		return rs.getObject(1, type);
	}
}
