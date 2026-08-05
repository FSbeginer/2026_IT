package test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

import test.Origional;

public class A_메인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JScrollPane scrollPane;
	public JTree tree;
	public JScrollPane scrollPane_1;
	public JPanel panel;

	private DefaultTreeModel model;
	private List<Product> products;
	private Set<DefaultMutableTreeNode> checked;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_메인 frame = new A_메인();
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public A_메인() {
		setTitle("메인");
		setBounds(100, 100, 862, 539);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("main");
		label.setBorder(null);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 846, 63);
		getContentPane().add(label);

		label_1 = new JLabel("mypage");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(682, 10, 65, 15);
		getContentPane().add(label_1);

		label_2 = new JLabel("로그인");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(769, 10, 65, 15);
		getContentPane().add(label_2);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 61, 144, 439);
		getContentPane().add(scrollPane);

		tree = new JTree();
		tree.addMouseListener(new TreeMouseListener());
		tree.setCellRenderer(new CheckRenderer());
		tree.setSelectionModel(null); // 선택 하이라이트를 끈다
		scrollPane.setViewportView(tree);

		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(147, 61, 699, 439);
		getContentPane().add(scrollPane_1);

		panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setLayout(null);
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(30);
		updateForm();
	}

	@Override
	public void updateForm() {
		checked = new LinkedHashSet<DefaultMutableTreeNode>(); // 폼 초기화 = 체크 해제
		try {
			loadProducts();
		} catch (Exception e) {
			e.printStackTrace();
		}
		addTreeNode();

		if (User.uno == -1) {
			label_1.setVisible(false);
			label_2.setText("로그인");
		} else {
			label_1.setVisible(true);
			label_2.setText("로그아웃");
		}
		load();
	}

	private void loadProducts() throws Exception {
		products = new ArrayList<Product>();
		try (var rs = DB.res("select p.pno, p.pname, c.cname from product p join category c using(cno)")) {
			while (rs.next()) {
				var p = new Product();
				p.pno = rs.getInt(1);
				p.pname = rs.getString(2);
				p.cname = rs.getString(3);
				p.avg = Origional.getPrice(p.pno);
				p.caps = Origional.getCapaties(p.pno).stream()
						.map(c -> c.get("value").toString()).collect(Collectors.toSet());
				p.services = Origional.getItems(p.pno).stream()
						.map(c -> c.get("type").toString()).collect(Collectors.toSet());
				products.add(p);
			}
		}
	}

	private void addTreeNode() {
		var root = new DefaultMutableTreeNode("전체");
		model = new DefaultTreeModel(root);
		tree.setModel(model);

		var cate = new DefaultMutableTreeNode("종류");
		root.add(cate);
		try (var rs = DB.res("select * from category")) {
			while (rs.next()) {
				cate.add(new DefaultMutableTreeNode(rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		var caps = new HashSet<String>();
		var services = new HashSet<String>();
		for (Product p : products) {
			caps.addAll(p.caps);
			services.addAll(p.services);
		}

		var cap = new DefaultMutableTreeNode("용량");
		root.add(cap);
		for (String v : caps) {
			cap.add(new DefaultMutableTreeNode(v));
		}

		var service = new DefaultMutableTreeNode("통신사");
		root.add(service);
		for (String v : services) {
			service.add(new DefaultMutableTreeNode(v));
		}

		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	// [축약] 위 addTreeNode 대신 이 둘을 쓴다. 카테고리 노드 3벌을 group 하나로 묶고 합집합도 stream 으로.
	// toSet() 은 안 된다 - HashSet 순회 순서가 그림1-1 의 256,512,1024,128 을 만들기 때문.
	//
	// private void addTreeNode() {
	// var root = new DefaultMutableTreeNode("전체");
	// model = new DefaultTreeModel(root);
	// tree.setModel(model);
	//
	// var cnames = new LinkedHashSet<String>();
	// try (var rs = DB.res("select * from category")) {
	// while (rs.next()) {
	// cnames.add(rs.getString(2));
	// }
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// group(root, "종류", cnames);
	// group(root, "용량", products.stream().flatMap(p -> p.caps.stream())
	// .collect(Collectors.toCollection(HashSet::new)));
	// group(root, "통신사", products.stream().flatMap(p -> p.services.stream())
	// .collect(Collectors.toCollection(HashSet::new)));
	//
	// for (int i = 0; i < tree.getRowCount(); i++) {
	// tree.expandRow(i);
	// }
	// }
	//
	// private void group(DefaultMutableTreeNode root, String name, Set<String> values) {
	// var g = new DefaultMutableTreeNode(name);
	// root.add(g);
	// for (String v : values) {
	// g.add(new DefaultMutableTreeNode(v));
	// }
	// }

	private void load() {
		panel.removeAll();

		// 카테고리 안은 or, 카테고리끼리는 and. filter 를 이어 붙이면 자동으로 and 다.
		var cond = conditions();
		var shown = products.stream()
				.filter(p -> ok(cond, "종류", Set.of(p.cname)))
				.filter(p -> ok(cond, "용량", p.caps))
				.filter(p -> ok(cond, "통신사", p.services))
				.collect(Collectors.toList());

		int w = (scrollPane_1.getWidth() - 60 - 30) / 4, h = 180, i = 0;
		for (Product p : shown) {
			var pp = new A_패널(null, null, 0);
			pp.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if (e.getClickCount() != 2)
						return; // 더블클릭만
					if (User.uno == -1) {
						msgErr("로그인을 하고 선택해주세요.");
//						showPage(new B_로그인());
					} else {
//						showPage(new C_상세정보(p.pno));
					}
				};
			});
			pp.setLocation(10 + (w + 8) * (i % 4), 20 + (h + 8) * (i / 4));
			panel.add(pp);
			i++;
		}
		i += 3;
		panel.setPreferredSize(new Dimension(0, 20 + (h + 8) * (i / 4)));
		panel.revalidate();
		panel.repaint();
	}

	/** 체크된 노드를 카테고리별로 묶는다. 부모 노드가 곧 카테고리다. */
	private Map<String, Set<String>> conditions() {
		var cond = new HashMap<String, Set<String>>();
		for (var n : checked) {
			cond.computeIfAbsent(n.getParent().toString(), k -> new HashSet<String>()).add(n.toString());
		}
		return cond;
	}

	// [축약] 위 conditions 대신. LINQ 의 GroupBy 자리다.
	// 여기 toSet() 은 안전하다 - 조건 집합은 contains 판정만 하지 순서를 안 쓴다.
	//
	// private Map<String, Set<String>> conditions() {
	// return checked.stream().collect(Collectors.groupingBy(
	// n -> n.getParent().toString(),
	// Collectors.mapping(Object::toString, Collectors.toSet())));
	// }

	/** 그 카테고리를 체크 안 했으면 통과, 했으면 겹치는 값이 하나라도 있어야 통과 */
	private boolean ok(Map<String, Set<String>> cond, String cat, Set<String> mine) {
		var want = cond.get(cat);
		return want == null || !Collections.disjoint(want, mine);
	}

	/** 상태를 조회해서 그리기만 한다. 잎 노드만 앞에 체크를 붙인다. */
	private class CheckRenderer extends DefaultTreeCellRenderer {
		@Override
		public Component getTreeCellRendererComponent(JTree t, Object value, boolean sel, boolean expanded,
				boolean leaf, int row, boolean hasFocus) {
			super.getTreeCellRendererComponent(t, value, sel, expanded, leaf, row, hasFocus);
			if (leaf) {
				setIcon(null);
				setText("<html>" + (checked.contains(value)
						? "<font face='Segoe UI Emoji' color=green>✅</font>"
						: "<font color=silver>○</font>") + " " + value);
			}
			return this;
		}
	}

	/** 좌표를 노드로, 상태를 바꾸고, 다시 그리라고 알린다 */
	private class TreeMouseListener extends MouseAdapter {
		@Override
		public void mousePressed(MouseEvent e) {
			var path = tree.getPathForLocation(e.getX(), e.getY());
			if (path == null) return; // 빈 공간
			var node = (DefaultMutableTreeNode) path.getLastPathComponent();
			if (!node.isLeaf()) return; // 루트, 카테고리는 무시
			if (!checked.remove(node)) checked.add(node); // 있으면 빼고 없으면 넣는다
			model.nodeChanged(node); // 다시 그리라고 알리고
			load();
		}
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
//			showPage(new E_마이페이지());
		}
	}

	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (User.uno == -1) {
//				showPage(new B_로그인());
			} else {
				User.uno = -1;
				msgInfo("로그아웃되었습니다.");
				updateForm();
			}
		}
	}
}

class Product {
	int pno, avg;
	String pname, cname; // 패널 라벨, 종류 필터
	Set<String> caps, services; // 용량 필터, 통신사 필터
}