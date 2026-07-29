import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class C_메인 extends BF {
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label_1;
	public JScrollPane scrollPane;
	public JPanel panel_2;
	public JPanel panel_3;
	public JLabel label_2;
	public JLabel label_3;

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
		setBounds(100, 100, 748, 613);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel(getIcon("icons/profile.png",40,40));
		label.setBounds(12, 10, 57, 50);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBounds(156, 10, 329, 68);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(497, 52, 223, 336);
		getContentPane().add(panel_1);
		
		label_1 = new JLabel("회원님을 위한 추천");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setBounds(497, 10, 155, 32);
		getContentPane().add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(94, 79, 391, 495);
		getContentPane().add(scrollPane);
		
		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		
		panel_3 = new JPanel();
		panel_3.setBounds(12, 139, 57, 351);
		getContentPane().add(panel_3);
		panel_3.setLayout(new GridLayout(0, 1, 0, 20));
		
		label_2 = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(55,125,255));
				g2.setStroke(new BasicStroke(2f));
				g2.drawOval(1, 1, getWidth()-3, getHeight()-3);
				g2.setFont(new Font("맑은 고딕",1,35));
				g2.drawString("+", getWidth()/2-12, getHeight()/2+12);
				g2.dispose();
			}
		};
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setForeground(new Color(55,125,255));
		label_2.setBounds(94, 10, 50, 50);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("내 스토리");
		label_3.setBounds(96, 63, 57, 15);
		getContentPane().add(label_3);
		
		addMenu();
		addFriends();
		load();
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}

	List<JPanel> profiles;
	int cx;
	private void addFriends() {
		profiles = new ArrayList<>();
		try (var rs = DB.res("SELECT * FROM itgram.user where find_in_set(u_no, (select u_follow from user where u_no = ?));",User.uno)) {
			int w =50, h=70,i=0;
			List<Integer> unos = new ArrayList<Integer>();
			while(rs.next()) {
				var pp = new JPanel();
				pp.setSize(w,h);
				pp.setLayout(new BorderLayout());
				var profile = new Profile("profile/"+rs.getInt("u_no")+".jpg", w, h-15,true);
				var jl = new JLabel(rs.getString("u_nick"),0);
				pp.add(profile);
				pp.add(jl, "South");
				pp.setLocation((w+5)*i, 0);
				int idx = i;
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new D_스토리(unos, idx));
					}
					@Override
					public void mousePressed(MouseEvent e) {
						cx = e.getX();
					}
				});
				pp.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int dx = e.getX()-cx;
						if(profiles.get(0).getX()+dx>0||profiles.get(profiles.size()-1).getX()+w+dx<panel.getWidth()) return;
						for (var pp: profiles) {
							pp.setLocation(pp.getX()+dx, 0);
						}
					}
				});
				panel.add(pp);
				profiles.add(pp);
				unos.add(rs.getInt("u_no"));
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		try (var rs = DB.res("select * from post join user using(u_no) where find_in_set(u_no,(select u_follow from user where u_no = ?)) order by rand()",User.uno)) {
			int w =371, h=495,i=0;
			while(rs.next()) {
				var pp = new C_게시물패널(rs.getInt("p_no"));
				pp.setLocation(0, h*i);
				panel_2.add(pp);
				i++;
			}
			panel_2.setPreferredSize(new Dimension(0,h*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void addMenu() {
		String[] paths = "home,search,send,heart1,plus".split(",");
		for (int i = 0; i < paths.length; i++) {
			JLabel jl =new JLabel(getIcon("icons/"+paths[i]+".png",35,35));
			int idx = i;
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					jlHandle(idx);
				}

			});
			panel_3.add(jl);
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
			showPage(new L_게시물추가());
			break;
		}
	}

	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new E_스토리_추가());
		}
	}
}
