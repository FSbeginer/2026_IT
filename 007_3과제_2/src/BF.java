import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.beans.Beans;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.plaf.FontUIResource;

public class BF extends JFrame {
	public static Stack<BF> prev = new Stack<>();
	static {
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.background", Color.white);
		var keys = UIManager.getDefaults().keys();
		while(keys.hasMoreElements()) {
			var key  = keys.nextElement();
			if(UIManager.get(key) instanceof FontUIResource) {
				UIManager.put(key, new FontUIResource("맑은 고딕", 0, 11));
			}
		}
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
	public BF() {
		if(Beans.isDesignTime()) return;
		setIconImage(Helper.getLogoImage(100, 100));
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				previous();
			}
		});
	}
	public void previous() {
		if(prev.isEmpty()) return;
		prev.peek().updateForm();
		prev.pop().setVisible(true);
	}
	public static ImageIcon getIcon(String path) {
		return new ImageIcon("./datafiles/"+path);
	}
	public static ImageIcon getIcon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon("./datafiles/"+path).getImage().getScaledInstance(w, h, 4));
	}
	public static void msgInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", 1);
	}
	public static void msgErr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", 0);
	}
} 
class Helper{
	public static Image getLogoImage(int w,int h) {
		var bi = new BufferedImage(w, h, 2);
		var g2 =bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		var src = BF.getIcon("logo.png",w,h);
		g2.drawImage(src.getImage(), -30, -30, w, h+30, 0, 0, (int)(w/2.1), h, null);
		return bi;
	}
}
class DB {
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/skillmall?serverTimezone=Asia/Seoul", "root",
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
	public static <T> T select(String sql, Class<T> type, Object...objects) throws SQLException {
		var rs = res(sql, objects);
		rs.next();
		return rs.getObject(1, type);
	}
}
class User {
	public static int uno = 0;
}