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

public class BF extends JFrame{
	public static Stack<BF> prev = new Stack<>();
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
	public static ImageIcon getIcon(String path) {
		return new ImageIcon("./datafiles/"+path);
	}
	public static ImageIcon getIcon(String path,int w,int h) {
		return new ImageIcon(new ImageIcon("./datafiles/"+path).getImage().getScaledInstance(w, h, 4));
	}
	public BF() {
		if(Beans.isDesignTime())return;
		try {
			setIconImage(Helper.getLogoImage());
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				previous();
			}

		});
	}
	public void previous() {
		if(prev.isEmpty()) return;
		prev.peek().updateFrom();
		prev.pop().setVisible(true);
	}
	public void updateFrom() {
		
	}
	public void showpage(BF next) {
		setVisible(false);
		if(!(this instanceof B_로그인)) prev.add(this);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
	public void showPage(Class<?> type) {
		if(!(this instanceof E_결제)) prev.add(this);
		while(!prev.isEmpty()) {
			var p = prev.pop();
			if(type.isInstance(p)) {
				p.updateFrom();
				p.setVisible(true);
				break;
			}
			else {
				p.dispose();
			}
		}
	}
}
class Helper{
	public static BufferedImage getTransferImage(String path, int w, int h) throws IOException {
		var img = ImageIO.read(new File("./datafiles/"+path));
		BufferedImage bi = new BufferedImage(w,h,2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(), img.getHeight(), null);
		for (int i = 0; i < w; i++) {
			for (int j = 0; j < h; j++) {
				if(bi.getRGB(i, j)>=new Color(240,240,240).getRGB())
					bi.setRGB(i, j, 0);
			}
		}
		return bi;
	}
	public static BufferedImage getLogoImage() throws IOException {
		var img = ImageIO.read(new File("./datafiles/logo.png"));
		BufferedImage bi = new BufferedImage(100,100,2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(img, 0, 0, 100, 100, 0, 0, img.getWidth()/3, img.getHeight(), null);
		for (int i = 0; i < 100; i++) {
			for (int j = 0; j < 100; j++) {
				if(bi.getRGB(i, j)==-1)
					bi.setRGB(i, j, 0);
			}
		}
		return bi;
	}
}
class DB {
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/skilltrain?serverTimezone=Asia/Seoul", "root","1234");
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
		var pre = pre(String.format("insert into %s values(%s)", table, String.join(",", Collections.nCopies(objects.length,"?"))));
		preSet(pre, objects);
		pre.execute();
	}
	public static void delete(String table, String where, Object...objects) throws SQLException {
		var pre = pre(String.format("delete from %s where %s", table, where));
		preSet(pre, objects);
		pre.execute();
	}
	public static void update(String table, String sql,String where, Object...objects) throws SQLException {
		var pre = pre(String.format("update %s set %s where %s", table, sql, where));
		preSet(pre, objects);
		pre.execute();
	}
	public static <T> T select(String sql, Class<T> type, Object...objects) throws SQLException {
		var rs = res(sql, objects);
		rs.next();
		return rs.getObject(1, type);
	}
	public static User getUser(int uno) throws SQLException {
		var rs = res("select * from user where uno = ?", uno);
		rs.next();
		return new User(rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getDate(6).toLocalDate(), rs.getInt(7));
	}
}
class User{
	public static int uno=-1;
	public String name,id,pw,card;
	public LocalDate birth;
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
	String name,line;
	int x,y;
	public Station(int sno, String name, String line, int x, int y) {
		super();
		this.sno = sno;
		this.name = name;
		this.line = line;
		this.x = x;
		this.y = y;
	}
}
