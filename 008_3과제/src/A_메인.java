import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingConstants;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeNode;

public class A_메인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JScrollPane scrollPane;
	public JTree tree;
	public JScrollPane scrollPane_1;
	public JPanel panel;
	private DefaultTreeModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_메인 frame = new A_메인();
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
	

	private void addTreeNode() {
		DefaultMutableTreeNode root = new DefaultMutableTreeNode("전체");
		model = new DefaultTreeModel(root);
		tree.setModel(model);
		var cate = new DefaultMutableTreeNode("종류");
		root.add(cate);
		try (var rs = DB.res("select * from category")) {
			while(rs.next()) {
				cate.add(new DefaultMutableTreeNode(rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		var cap = new DefaultMutableTreeNode("용량"); 
		root.add(cap);
		cap.add(new DefaultMutableTreeNode("256GB"));
		cap.add(new DefaultMutableTreeNode("512GB"));
		cap.add(new DefaultMutableTreeNode("1024GB"));
		cap.add(new DefaultMutableTreeNode("128GB"));
		var service = new DefaultMutableTreeNode("통신사");
		root.add(service);
		service.add(new DefaultMutableTreeNode("LG U+"));
		service.add(new DefaultMutableTreeNode("KT"));
		service.add(new DefaultMutableTreeNode("SKT"));
		for (int i = 0; i < tree.getRowCount(); i++) {
			tree.expandRow(i);
		}
	}

	@Override
	public void updateForm() {
		addTreeNode();
		
		if(User.uno==-1) {
			label_1.setVisible(false);
			label_2.setText("로그인");
		}
		else {
			label_1.setVisible(true);
			label_2.setText("로그아웃");
		}
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void load() throws Exception {
		panel.removeAll();
		try (var rs = DB.res("select * from product")) {
			int w = (scrollPane_1.getWidth()-60-30)/4, h = 180,i=0;
			while(rs.next()) {
				int pno = rs.getInt(1);
				int avg =ProjectInfo.getPrice(pno);
				var pp = new A_패널(getIcon("기종/"+rs.getInt(1)+".jfif",w-10,h-75), rs.getString(2), avg);
				pp.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if(User.uno==-1) {
							msgErr("로그인을 하고 선택해주세요.");
							showPage(new B_로그인());
						}
						else {
							showPage(new C_상세정보(pno));
						}
					};
				});
				pp.setSize(w, h);
				pp.setLocation(10+(w+8)*(i%4), 20+(h+8)*(i/4));
				panel.add(pp);
				i++;
			}
			i+=3;
			panel.setPreferredSize(new Dimension(0, 20+(h+8)*(i/4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.revalidate();
		panel.repaint();
	}

	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(User.uno==-1) {
				showPage(new B_로그인());
			}
			else {
				User.uno = -1;
				msgInfo("로그아웃되었습니다.");
				updateForm();
			}
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new E_마이페이지());
		}
	}
}
