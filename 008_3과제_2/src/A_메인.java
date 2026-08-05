import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import javax.script.ScriptException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.DefaultTreeModel;

public class A_메인 extends BF {
	public JScrollPane scrollPane;
	public JScrollPane scrollPane_1;
	public JTree tree;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	private DefaultMutableTreeNode root;
	private DefaultTreeModel model;

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
		setBounds(100, 100, 856, 607);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 62, 176, 506);
		getContentPane().add(scrollPane);
		
		tree = new JTree();
		scrollPane.setViewportView(tree);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(174, 62, 665, 506);
		getContentPane().add(scrollPane_1);
		
		panel = new JPanel();
		scrollPane_1.setViewportView(panel);
		panel.setLayout(null);
		
		label = new JLabel("main");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(324, 23, 192, 39);
		getContentPane().add(label);
		
		label_1 = new JLabel("mypage");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setFont(new Font("굴림", Font.BOLD, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(662, 10, 72, 15);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("로그인");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("굴림", Font.BOLD, 13));
		label_2.setBounds(745, 10, 83, 15);
		getContentPane().add(label_2);

		settree();
		updateForm();
		scrollPane_1.getVerticalScrollBar().setUnitIncrement(30);
	}
	
	List<Product> products = new ArrayList<Product>();
	Map<String, HashSet<String>> where = new HashMap<String, HashSet<String>>();
	Set<String> highLight = new HashSet<String>(); 
	private void settree() {
		root = new DefaultMutableTreeNode("전체");
		model = new DefaultTreeModel(root);
		var categoryFolder = new DefaultMutableTreeNode("종류");
		var capacityFolder = new DefaultMutableTreeNode("용량");
		var typeFolder = new DefaultMutableTreeNode("통신사");
		root.add(categoryFolder);
		root.add(capacityFolder);
		root.add(typeFolder);
		
		try (var rs = DB.res("select * from category")) {
			while(rs.next()) {
				var catenode = new DefaultMutableTreeNode(rs.getString(2));
				categoryFolder.add(catenode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		try (var rs = DB.res("select * from product join category using(cno)")) {
			Set<String> capacities = new HashSet<String>();
			Set<String> types = new HashSet<String>();
			while(rs.next()) {
				var product = new Product(rs.getInt("pno"), rs.getString("pname"), new Category(rs.getInt("cno"), rs.getString("cname")));
				product.capacities.stream().map(x->x.value).forEach(capacities::add);
				product.items.stream().map(x->x.type).forEach(types::add);
				products.add(product);
			}
			for (String capacity : capacities) {
				var capNode = new DefaultMutableTreeNode(capacity);
				capacityFolder.add(capNode);
			}
			for (String type : types) {
				var typeNode = new DefaultMutableTreeNode(type);
				typeFolder.add(typeNode);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ScriptException e) {
			e.printStackTrace();
		}
		tree.setModel(model);
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
		
		where.put("종류", new HashSet<>());
		where.put("용량", new HashSet<>());
		where.put("통신사", new HashSet<>());
		tree.addTreeSelectionListener(new TreeSelectionListener() {
			@Override
			public void valueChanged(TreeSelectionEvent e) {
				DefaultMutableTreeNode node = (DefaultMutableTreeNode) tree.getLastSelectedPathComponent();
				if(node!=null&&node.isLeaf()) {
					var item = node.getUserObject().toString();
					var set = where.get(node.getParent().toString());
					if(set.contains(item)) {
						set.remove(item);
						highLight.remove(item);
					}
					else {
						set.add(item);
						highLight.add(item);
					}
					addProduct();
				}
				tree.clearSelection();
			}
		});
		tree.setCellRenderer(new DefaultTreeCellRenderer() {
			public Component getTreeCellRendererComponent(JTree tree, Object value, boolean sel, boolean expanded, boolean leaf, int row, boolean hasFocus) {
				JLabel comp = (JLabel) super.getTreeCellRendererComponent(tree, value, sel, expanded, leaf, row, hasFocus);
				comp.setOpaque(true);
				if(leaf)
					comp.setFont(new Font("맑은 고딕",1,13));
				if(leaf&&highLight.contains(value.toString()))
					comp.setBackground(Color.orange);
				else
					comp.setBackground(Color.white);
				return comp;
			};
		});
	}
	@Override
	public void updateForm() {
		if(User.uno==-1) {
			label_1.setVisible(false);
			label_2.setText("로그인");
		}
		else {
			label_1.setVisible(true);
			label_2.setText("로그아웃");
		}
		addProduct();
	}

	private void addProduct() {
		panel.removeAll();
		var list = products.stream().filter(x->{
			var conCate = where.get("종류");
			var conCap = where.get("용량");
			var conType = where.get("통신사");
			
			boolean cate = conCate.size()==0|| conCate.contains(x.category.cname);
			boolean cap = conCap.size()==0||x.capacities.stream().anyMatch(c-> conCap.contains(c.value));
			boolean type = conType.size()==0|| x.items.stream().anyMatch(c-> conType.contains(c.type));
			return cate&&cap&&type;
		}).collect(Collectors.toList());
		
		int w = (scrollPane_1.getWidth()-90)/4, h = scrollPane_1.getHeight()/3, i= 0;
		for (var product : list) {
			int price = product.items.stream().mapToInt(x->x.price).sum()/ product.items.size();
			var pp = new A_패널(getIcon("기종/"+product.pno+".jfif", 131, 94), String.format("<html><b>기종:%s<br>평균 가격: %,d원",product.name, price));
			pp.label.addMouseListener(new MouseAdapter() {
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						if(User.uno==-1) {
							msgErr("로그인을 하고 선택해주세요.");
							showPage(new B_로그인());
						}
						else {
							showPage(new C_상세정보(product));
						}
					}
				};
			});
			pp.setSize(w,h);
			pp.setLocation(10+(w+10)*(i%4), 25+(h+10)*(i/4));
			panel.add(pp);
			i++;
		}
		i+=3;
		panel.setPreferredSize(new Dimension(0, 25+(h+10)*(i/4)));
		panel.revalidate();
		panel.repaint();
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new E_마이페이지());
		}
	}
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(User.uno==-1) {
				showPage(new B_로그인());
			}
			else {
				User.uno=-1;
				msgErr("로그아웃되었습니다.");
				updateForm();
			}
		}
	}
}
