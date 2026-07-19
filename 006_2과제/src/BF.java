import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.Beans;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Stack;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class BF extends JFrame {
	public static Stack<BF> prev = new Stack<>();
	static {
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.background", Color.white);
	}
	
	public BF() {
		if (Beans.isDesignTime())
			return;
		setIconImage(getTransferLogo().getImage());
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				if (prev.isEmpty())
					return;
				if(prev.peek() instanceof B_로그인) prev.pop();
				prev.peek().updateForm();
				prev.pop().updateForm();
			}
		});
	}

	public static ImageIcon getTransferImage(String path, int w, int h) {
		var icon = getIcon(path).getImage();
		var bi = new BufferedImage(w, h, 2);
		var g2 = bi.createGraphics();
		
		g2.drawImage(icon, 0,0,w, h, 0,0, icon.getWidth(null), icon.getHeight(null), null);
		int bgRGB = bi.getRGB(0, 0);
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				if(isBG(bi.getRGB(i, j),bgRGB)) {
					bi.setRGB(i, j, 0);
				}
			}
		}
		return new ImageIcon(bi);
	}
	public static ImageIcon getTransferLogo() {
		var icon = getIcon("logo.png").getImage();
		var bi = new BufferedImage(100, 100, 2);
		var g2 = bi.createGraphics();
		
		g2.drawImage(icon, 0,0,100, 100, 0,0, icon.getWidth(null)*3/10, icon.getHeight(null), null);
		int bgRGB = bi.getRGB(0, 0);
		for (int i = 0; i < bi.getWidth(); i++) {
			for (int j = 0; j < bi.getHeight(); j++) {
				if(isBG(bi.getRGB(i, j),bgRGB)) {
					bi.setRGB(i, j, 0);
				}
			}
		}
		return new ImageIcon(bi);
	}

	private static boolean isBG(int rgb, int bgRGB) {
		Color a = new Color(rgb);
		Color b = new Color(bgRGB);
		int diff = Math.abs(a.getRed()-b.getRed())+Math.abs(a.getGreen()-b.getGreen())+Math.abs(a.getBlue()-b.getBlue());
		
		return diff<=60;
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

	public void showPage(BF next) {
		setVisible(false);
		prev.add(this);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
	public void showPage(Class<?> ins) {
		prev.add(this);
		while(!prev.isEmpty()) {
			var p = prev.pop();
			if(ins.isInstance(p)) {
				p.updateForm();
				p.setVisible(true);
				break;
			}
			else {
				p.dispose();
			}
		}
	}
}

class User {
	public static int uno = 1;
	public static String uname;
	public static LocalDate getBirth() throws SQLException {
		var rs = DB.res("select * from user where uno = ?",uno);
		rs.next();
		var birth = rs.getDate("birth").toLocalDate();
		return birth;
	}
	public static int getPrice() throws SQLException {
		var rs = DB.res("select * from user where uno = ?",uno);
		rs.next();
		return rs.getInt("price");
	}
	public static int getAge() throws SQLException {
		var birth = getBirth();
		int age = LocalDate.now().getYear() - birth.getYear();
		if(birth.plusYears(age).isAfter(LocalDate.now()))
			age--;
		return age;
	}
}

class DB {
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/skilltrain?serverTimezone=Asia/Seoul", "root",
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
