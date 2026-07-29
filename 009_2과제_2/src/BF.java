import java.awt.Color;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.beans.Beans;
import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collections;
import java.util.Stack;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import javax.imageio.ImageIO;
import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;


public class BF extends JFrame {
	public static Stack<BF> prev = new Stack<>();
	static {
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.put("Panel.background", Color.white);
			UIManager.put("OptionPane.background", Color.white);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
				| UnsupportedLookAndFeelException e) {
			e.printStackTrace();
		}
	}

	public BF() {
		if (Beans.isDesignTime())
			return;
		setIconImage(getIcon("logo/logo.png").getImage());
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
			else{
				p.dispose();
			}
		}
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
	public static ImageIcon getIcon(String path, int w,int h) {
		return new ImageIcon(new ImageIcon("./datafiles/"+path).getImage().getScaledInstance(w, h, 4));
	}
	public void updateForm() {
		
	}
}
class Helper{
	public static SwingWorker<Image, Void> getImageWorker(byte[] data, int w, int h, Consumer<Image> con) {
		var worker=  new SwingWorker<Image, Void>(){
			@Override
			protected Image doInBackground() throws Exception {
				Image img = new ImageIcon(data).getImage();
				var bi = new BufferedImage(w, h, 2);
				var g2 = bi.createGraphics();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(img, 0, 0, w, h, 0, 0, img.getWidth(null), img.getHeight(null), null);
				g2.dispose();
				return bi;
			}
			@Override
			protected void done() {
				try {
					if(con!=null) con.accept(get());
				} catch (InterruptedException e) {
					e.printStackTrace();
				} catch (ExecutionException e) {
					e.printStackTrace();
				}
			}
		};
		return worker;
	}
}
class DB{
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/idelivery?serverTimezone=Asia/Seoul", "root", "1234");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	public static PreparedStatement pre(String sql) throws SQLException {
		return con.prepareStatement(sql);
	}
	public static void preSet(PreparedStatement pre,Object...objects) throws SQLException {
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
		var pre = pre(String.format("update %s set %s where %s", table , sql, where));
		preSet(pre, objects);
		pre.execute();
	}
	public static <T> T select(String sql, Class<T> type, Object...objects) throws SQLException {
		var rs = res(sql,objects);
		rs.next();
		return rs.getObject(1, type);
	}
}
class User {
	public static int uno = 1;
}
