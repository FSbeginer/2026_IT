import java.awt.Color;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
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

public class BF extends JFrame{
	public static Stack<BF> prev = new Stack<BF>();
	static {
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.background", Color.white);
	}
	public BF() {
		if(Beans.isDesignTime()) return;
		setIconImage(getIcon("icon/logo.png").getImage());
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
	public void updateForm() {
		
	}
	public void showPage(BF next) {
		prev.add(this);
		setVisible(false);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
	public static void msgInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", 1);
	}
	public static void msgErr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", 0);
	}
	public static ImageIcon getIcon(String path) {
		return new ImageIcon("./datafiles/"+path);
	}
	public static ImageIcon getIcon(String path,int w,int h) {
		return new ImageIcon(new ImageIcon("./datafiles/"+path).getImage().getScaledInstance(w, h, 4));
	}
}
class Helper {
	public static Image getTransParentIcon(Image img) {
		var bi = new BufferedImage(img.getHeight(null), img.getHeight(null), 2);
		var g2 = bi.createGraphics();
		g2.drawImage(img, 0, 0, null);
		int bg = bi.getRGB(0, 0);
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				if(isBG(bi.getRGB(i, j), bg)) {
					bi.setRGB(i, j, 0);
				}
			}
		}
		return bi;
	}

	private static boolean isBG(int rgb, int bg) {
		Color a = new Color(rgb);
		Color b = new Color(bg);
		int diff = Math.abs(a.getRed()-b.getRed())+Math.abs(a.getGreen()-b.getGreen())+Math.abs(a.getBlue()-b.getBlue());
		return diff<25;
	}
}
class DB{
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/dentaldb?serverTimezone=Asia/Seoul", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static PreparedStatement pre(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}
	public static void preSet(PreparedStatement pre, Object...objects) throws SQLException {
		int i = 1;
		for (Object object : objects) {
			pre.setObject(i++, object);
		}
	}
	public static ResultSet res(String sql) throws SQLException {
		return pre(sql).executeQuery();
	}
	public static ResultSet res(String sql, Object...objects) throws SQLException {
		var pre = pre(sql);
		preSet(pre, objects);
		return pre.executeQuery();
	}
	public static void insert(String table, Object...objects) throws SQLException {
		var pre = pre(String.format("insert into %s values(%s)", table, String.join(",",Collections.nCopies(objects.length, "?"))));
		preSet(pre, objects);
		pre.execute();
	}
	public static void delete(String table, String where, Object...objects) throws SQLException {
		var pre = pre(String.format("delete from %s where %s", table, where));
		preSet(pre, objects);
		pre.execute();
	}
	public static void update(String table,String sql ,String where, Object...objects) throws SQLException {
		var pre = pre(String.format("update %s set %s where %s", table, sql,where));
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
	public static int uno = -1, lno =1;
}