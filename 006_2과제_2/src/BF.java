import java.awt.Color;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.Beans;
import java.io.File;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Stack;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.UIManager;


public class BF extends JFrame {
	public static Stack<BF> prev = new Stack<BF>();
	static {
		UIManager.put("Panel.background", Color.white);
		UIManager.put("OptionPane.background", Color.white);
	}
	public static void msgInfo(String msg) {
		JOptionPane.showMessageDialog(null, msg, "정보", 1);
	}
	public static void msgErr(String msg) {
		JOptionPane.showMessageDialog(null, msg, "경고", 0);
	}
	public BF() {
		if(Beans.isDesignTime()) return;
		try {
			setIconImage(Helper.getLogoImage(100, 100));
		} catch (IOException e1) {
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				prev();
			}
		});
	}
	public void updateForm() {
		
	}
	public static void prev() {
		if(prev.isEmpty()) return;
		prev.peek().updateForm();
		prev.pop().setVisible(true);
	}
	public void showPage(BF next) {
		setVisible(false);
		if(!(this instanceof B_로그인)) prev.add(this);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
	public void showPage(Class<?> ins) {
		if(!(this instanceof E_결제)) prev.add(this);
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
	public static ImageIcon getIcon(String path) {
		return new ImageIcon("./datafiles/"+path);
	}
	public static ImageIcon getIcon(String path, int w, int h) {
		return new ImageIcon(new ImageIcon("./datafiles/"+path).getImage().getScaledInstance(w, h, 4));
	}
}
class Helper {
	public static BufferedImage getTransImage(String path, int w, int h) throws IOException {
		BufferedImage bi = new BufferedImage(w, h, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		var img = ImageIO.read(new File("./datafiles/"+path));
		g2.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(), null);
		int bg = bi.getRGB(0, 0);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(isBG(bi.getRGB(i, j), bg)) {
					bi.setRGB(i, j, 0);
				}
			}
		}
		g2.dispose();
		return bi;
	}
	public static BufferedImage getLogoImage(int w, int h) throws IOException {
		BufferedImage bi = new BufferedImage(w, h, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		var img = ImageIO.read(new File("./datafiles/logo.png"));
		g2.drawImage(img, -15, 0, w+10, h, 0, 0, img.getWidth()/3, img.getHeight(), null);
		int bg = bi.getRGB(0, 0);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(isBG(bi.getRGB(i, j), bg)) {
					bi.setRGB(i, j, 0);
				}
			}
		}
		g2.dispose();
		return bi;
	}

	private static boolean isBG(int rgb, int bg) {
		Color a = new Color(rgb), b = new Color(bg);
		int diff = Math.abs(a.getRed()-b.getRed())+Math.abs(a.getGreen()-b.getGreen())+Math.abs(a.getBlue()-b.getBlue());
		return diff<40;
	}
}
class DB{
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/skilltrain?serverTimezone=Asia/Seoul", "root", "1234");
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
		var pre = pre(String.format("insert into %s values(%s)", table, String.join(",", Collections.nCopies(objects.length, "?"))));
		preSet(pre, objects);
		pre.execute();
	}
	public static void delete(String table, String where, Object...objects) throws SQLException {
		var pre = pre(String.format("delete from %s where %s", table, where));
		preSet(pre, objects);
		pre.execute();
	}
	public static void update(String table, String sql, String where, Object...objects) throws SQLException {
		var pre = pre(String.format("update %s set %s where %s", table, sql,where));
		preSet(pre, objects);
		pre.execute();
	}
	public static <T> T select(String sql, Class<T> type, Object...objects) throws SQLException {
		var pre = pre(sql);
		preSet(pre, objects);
		var rs = pre.executeQuery();
		rs.next();
		return rs.getObject(1, type);
	}
	
	public static User getUser(int uno) throws SQLException {
		var rs = DB.res("select * from user where uno = ?", uno);
		rs.next();
		return new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toLocalDate(), rs.getInt(7));
	}
}
class User{
	public static int uno = -1;
	String name, id, pw,card;
	LocalDate birth;
	int price;
	public User(String name, String id, String pw, String card, LocalDate birth, int price) {
		super();
		this.name = name;
		this.id = id;
		this.pw = pw;
		this.card = card;
		this.birth = birth;
		this.price = price;
	}
}
class Station {
	int sno;
	String name, line;
	int x,y;
	public Station(int sno, String name, String line, int x, int y) {
		super();
		this.sno = sno;
		this.name = name;
		this.line = line;
		this.x = x;
		this.y = y;
	}
	@Override
	public String toString() {
		return name+"("+line.substring(0,2)+")";
	}
	@Override
	public boolean equals(Object obj) {
		if(obj==this)
			return true;
		if(!(obj instanceof Station)) return false;
		Station target = (Station) obj;
		return this.sno==target.sno;
	}
}
