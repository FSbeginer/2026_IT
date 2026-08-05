import java.awt.Color;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.stream.Collectors;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
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

	public BF() {
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				previous();
			}

		});
	}

	public void updateForm() {

	}

	public void previous() {
		if (prev.isEmpty())
			return;
		prev.peek().updateForm();
		prev.pop().setVisible(true);
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

	public void showPage(BF next) {
		setVisible(false);
		prev.add(this);
		next.setDefaultCloseOperation(2);
		next.setLocationRelativeTo(null);
		next.setVisible(true);
	}
}

class Helper {
	public static Map<String, Object> getProjectInfos(int target) throws IOException, ScriptException {
		String json = Files.readString(Path.of("./datafiles/project.json"));
		json = json.replaceAll("\\}\\s+\\{", "},{");

		ScriptEngine engine = new ScriptEngineManager().getEngineByName("javascript");
		List<Map<String, Object>> infos = (List<Map<String, Object>>) engine
				.eval("Java.asJSONCompatible(" + json + ")");
		for (Map<String, Object> map : infos) {
			int pno = (int) map.get("pno");
			if (pno == target)
				return map;
		}
		return null;
	}

	public static List<Item> getItems(int pno) throws IOException, ScriptException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) getProjectInfos(pno).get("items");
		return list.stream().map(x ->new Item(x.get("type").toString(), (int) x.get("price"))).collect(Collectors.toList());
	}
	public static List<Installment> getInstallments(int pno) throws IOException, ScriptException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) getProjectInfos(pno).get("installments");
		return list.stream().map(x->new Installment((int) x.get("month"))).collect(Collectors.toList());
	}
	public static List<Capacity> getCapacities(int pno) throws IOException, ScriptException {
		List<Map<String, Object>> list = (List<Map<String, Object>>) getProjectInfos(pno).get("capacities");
		return list.stream().map(x->new Capacity(x.get("value").toString(), (int) x.get("price"))).collect(Collectors.toList());
	}
}
class Product{
	int pno;
	String name;
	List<Item> items;
	List<Capacity> capacities;
	List<Installment> installments;
	Category category;
	public Product(int pno, String name, Category category) throws IOException, ScriptException {
		this.pno = pno;
		this.name = name;
		this.items = Helper.getItems(pno);
		this.capacities = Helper.getCapacities(pno);
		this.installments = Helper.getInstallments(pno);
		this.category = category;
	}
}
class Category{
	int cno;
	String cname;
	public Category(int cno, String cname) {
		this.cno = cno;
		this.cname = cname;
	}
}
class Capacity {
	String value;
	int price;

	public Capacity(String value, int price) {
		this.value = value+"GB";
		this.price = price;
	}
}

class Item {
	String type;
	int price;

	public Item(String type, int price) {
		this.type = type;
		this.price = price;
	}
}

class Installment {
	int month;

	public Installment(int month) {
		this.month = month;
	}
}

class DB {
	public static Connection con;
	static {
		try {
			con = DriverManager.getConnection("jdbc:mysql://localhost/smartdb?serverTimezone=Asia/Seoul", "root",
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

	public static <T> T select(String sql, Class<T> type, Object... objects) throws SQLException {
		var rs = res(sql, objects);
		rs.next();
		return rs.getObject(1, type);
	}
}

class User {
	public static int uno = 1;
}