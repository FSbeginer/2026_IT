package test;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import javax.swing.JScrollPane;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class C_메인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JPanel panel;
	public JLabel label_2;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					C_메인 frame = new C_메인();
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
	public C_메인() {
		setTitle("ITGRAM");
		setBounds(100, 100, 827, 706);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel(getIcon("icons/profile.png",40,40));
		label.setBounds(12, 10, 57, 50);
		getContentPane().add(label);
		
		label_1 = Rows.myStory(50, 70);
		label_1.setBounds(103, 10, 50, 70);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBounds(172, 10, 353, 68);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_2 = new JLabel("회원님을 위한 추천");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBounds(547, 42, 229, 29);
		getContentPane().add(label_2);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(103, 91, 435, 576);
		getContentPane().add(scrollPane);
		
		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 228, 57, 327);
		getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(0, 1, 0, 20));
		
		addStorys();
		addMenu();
		addPost();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}

	private void addPost() {
		try (var rs = DB.res("select * from post where find_in_set(u_no, (select u_follow from user where u_no =?)) order by rand();",User.uno)) {
			int  w = 415, h= 576, i= 0;
			while(rs.next()) {
				var pp = new C_게시물패널(rs.getInt("p_no"));
				pp.setLocation(0, h*i);
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0,h*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addMenu() {
		String[] paths = "home,search,send,heart1,plus".split(",");
		for (int i = 0; i < 5; i++) {
			var jl = new JLabel(getIcon("icons/"+paths[i]+".png",40,40));
			int idx = i;
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					jlHandle(idx);
				}
			});
			panel_2.add(jl);
		}
	}
	private void jlHandle(int idx) {
		switch (idx) {
		case 0:
			break;
		case 1:
			showPage(new I_검색());
			break;
		case 2:
			showPage(new H_채팅());
			break;
		case 3:
			showPage(new K_좋아요목록());
			break;
		case 4:
			showPage(new L_게시물_추가());
			break;
		}
	}

	List<JLabel> storys = new ArrayList<JLabel>();
	int cx = 0;
	private void addStorys() {
		try (var rs = DB.res("select * from user where find_in_set(u_no, (select u_follow from user where u_no =?));",User.uno)) {
			int w = 50, h = 70, i =0;
			List<Integer> unos = new ArrayList<Integer>();
			while(rs.next()) {
				var pp = Rows.profileStory(rs.getInt("u_no"), rs.getString("u_nick"), w, h);
				int idx = i;
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						cx = e.getX();
					}
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new D_스토리(idx,unos));
					}
				});
				pp.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int dx = e.getX()-cx;
						if(storys.get(0).getX()+dx>0||storys.get(storys.size()-1).getX()+dx+50<panel.getWidth()) return;
						for (var pp : storys) {
							pp.setLocation(pp.getX()+dx, pp.getY());
						}
					}
				});
				pp.setLocation(60*i, 0);
				panel.add(pp);
				storys.add(pp);
				unos.add(rs.getInt(1));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
