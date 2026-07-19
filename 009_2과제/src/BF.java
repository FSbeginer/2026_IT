import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.Beans;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.plaf.FontUIResource;

public class BF extends JFrame {
	public static Stack<BF> prev = new Stack<>();
	public static List<Image> imgs = new ArrayList<Image>();
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Panel.background", Color.WHITE);
			UIManager.put("OptionPane.background", Color.WHITE);

			var keys = UIManager.getDefaults().keys();
			while (keys.hasMoreElements()) {
				var key = keys.nextElement();
				var value = UIManager.get(key);
				if (value instanceof FontUIResource) {
					UIManager.put(key, new FontUIResource("맑은 고딕", 1, 12));
				}
			}
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}
	public BF() {
		if (Beans.isDesignTime())
			return;
		setIconImage(new ImageIcon("./datafiles/logo/logo.png").getImage());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (prev.isEmpty())
					return;
				prev.peek().updateForm();
				prev.pop().updateForm();
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

	public static ImageIcon getIcon(byte[] path) {
		return new ImageIcon(path);
	}

	public static ImageIcon getIcon(byte[] path, int w, int h) {
		return new ImageIcon(new ImageIcon(path).getImage().getScaledInstance(w, h, 4));
	}

	public void updateForm() {

	}

	public void showPage(BF next) {
		setVisible(false);
		prev.add(this);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
}

class User {
	public static int uno;
	public static String uname;
}

class DB {
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/idelivery?serverTimezone=Asia/Seoul", "root",
					"1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static PreparedStatement pre(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}

	public static void preSet(PreparedStatement pre, Object... objects) throws SQLException {
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
}
