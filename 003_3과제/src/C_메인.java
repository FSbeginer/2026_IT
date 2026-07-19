import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingWorker;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class C_메인 extends BF {
	public JLabel label;
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JLabel label_1;
	public JPanel panel_4;
	public JLabel label_2;

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
		setBounds(100, 100, 754, 632);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel(getIcon("icons/profile.png", 35, 35));
		label.setBounds(12, 10, 48, 43);
		getContentPane().add(label);

		panel = new JPanel();
		panel.setBounds(12, 179, 48, 273);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(5, 1, 0, 0));

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(98, 104, 370, 479);
		scrollPane.getViewport().addChangeListener(new ChangeListener() {
			@Override
			public void stateChanged(ChangeEvent e) {
				loadVisibleViewPort();
			}
		});
		getContentPane().add(scrollPane);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);

		panel_2 = new JPanel();
		panel_2.setBounds(167, 10, 307, 70);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);

		panel_3 = new JPanel();
		panel_3.setBounds(486, 73, 240, 300);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		label_1 = new JLabel("회원님을 위한 추천");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setBounds(486, 47, 177, 15);
		getContentPane().add(label_1);

		panel_4 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setStroke(new BasicStroke(2f));
				g2.setColor(new Color(55, 128, 225));
				g2.drawOval(1, 1, getWidth() - 2, getHeight() - 2);
				g2.setFont(new Font("맑은 고딕", 1, 30));
				g2.drawString("+", (getWidth() - getFontMetrics(g2.getFont()).stringWidth("+")) / 2,
						getHeight() / 2 + 7);
			}
		};
		panel_4.addMouseListener(new Panel_4MouseListener());
		panel_4.setBounds(98, 10, 50, 50);
		getContentPane().add(panel_4);

		label_2 = new JLabel("내 스토리");
		label_2.setBounds(101, 65, 57, 15);
		getContentPane().add(label_2);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);

		areaA();
		areaC();
		areaD();
	}
	@Override
	public void updateForm() {
		areaA();
		areaC();
	}
	private void loadVisibleViewPort() {
		var viewbox = scrollPane.getViewport().getViewRect();
		for (var comp : panel_1.getComponents()) {
			if(viewbox.intersects(comp.getBounds())) {
				((C_패널)comp).loadImage();
			}
		}
	}
	
	private void areaC() {
		panel_1.removeAll();
		try (var rs = DB.res(
				"select post.*, u_nick from post join user using(u_no) where find_in_set(u_no, (select u_follow from user where u_no = ?)) order by rand();",
				User.uno)) {
			int w = 350, h = 479, i = 0;
			while (rs.next()) {
				int pno = rs.getInt("p_no");
				var pp = new C_패널(pno);
				pp.setLocation(0, h * i);
				pp.label_2.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new F_댓글(pno));
					}
				});
				pp.label_3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new G_공유하기(pno));
					}
				});
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0, h * i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_1.revalidate();
		panel_1.repaint();
		loadVisibleViewPort();
	}

	int mx = 0;
	List<JPanel> pps = new ArrayList<JPanel>();

	private void areaA() {
		panel_2.removeAll();
		pps.clear();
		try (var rs = DB.res("select * from user where find_in_set(u_no, (select u_follow from user where u_no = ?));",
				User.uno)) {
			int w = 50, h = 70, i = 0;
			List<Integer> unos = new ArrayList<Integer>();
			while (rs.next()) {
				var pp = new JPanel();
				pp.setLayout(new BorderLayout());
				pp.setSize(w, h);
				int uno = rs.getInt("u_no");
				int idx = i;
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mousePressed(MouseEvent e) {
						mx = e.getX();
					}

					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new D_스토리(unos, idx));
					}
				});
				pp.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseDragged(MouseEvent e) {
						int dx = e.getX() - mx;
						if (pps.get(0).getX() + dx > 0 || pps.get(pps.size() - 1).getX() + 50 + dx < panel_2.getWidth())
							return;
						for (var pp : pps) {
							pp.setLocation(pp.getX() + dx, pp.getY());
						}
					}
				});
				pp.setLocation((w + 15) * i, 0);

				var profile = new Profile(true);
				pp.add(profile);
				pp.add(new JLabel(rs.getString("u_nick")), "South");
				getImage("profile/" + uno + ".jpg", 50, 50, x -> profile.setImage(x)).execute();

				panel_2.add(pp);
				pps.add(pp);
				unos.add(uno);
				i++;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_2.revalidate();
		panel_2.repaint();
	}

	private void areaD() {
		String[] path = "home,search,send,heart1,plus".split(",");
		for (int i = 0; i < 5; i++) {
			int idx = i;
			JLabel jl = new JLabel(getIcon("icons/" + path[i] + ".png", 40, 40));
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					clickhandle(idx);
				}

			});
			panel.add(jl);
		}
	}

	private void clickhandle(int idx) {
		switch (idx) {
		case 0: {
			break;
		}
		case 1: {
			showPage(new I_검색());
			break;
		}
		case 2: {
			showPage(new H_채팅());
			break;
		}
		case 3: {
			showPage(new K_좋아요목록());
			break;
		}
		case 4: {
			showPage(new L_게시물추가());
			break;
		}
		}
	}

	private class Panel_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new E_스토리추가());
		}
	}
}
