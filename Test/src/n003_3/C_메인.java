package n003_3;

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
import java.util.Collections;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;
import javax.swing.Timer;
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

		areaD();
		
		//UI를 만든다. -> 화면을 먼저 표시한다. -> 스레드에서 DB조회를 한다.
		areaA();
		areaC();
	}
	@Override
	public void updateForm() {
		areaA();
		areaC();
	}
	private void loadVisibleViewPort() {
		var viewbox = scrollPane.getViewport().getViewRect();
		for (var comp : panel_1.getComponents()) {
			//comp가 C_패널 타입 캐스팅이 가능할 때만 처리
			if(comp instanceof C_패널 && viewbox.intersects(comp.getBounds())) {
				((C_패널)comp).loadImage();
			}
		}
	}
	
	private void areaC() {
		int w = 350, h = 479;
		
		//C영역 자체를 즉시 화면에 표시한다.
	    SwingUtilities.invokeLater(() -> {
	        panel_1.removeAll();
	        panel_1.setPreferredSize(new Dimension(w, h));

	        JLabel loadingLabel = new JLabel(
	                "게시물을 불러오는 중...",
	                JLabel.CENTER
	        );
	        loadingLabel.setBounds(0, 0, w, h);

	        panel_1.add(loadingLabel);
	        panel_1.revalidate();
	        panel_1.repaint();
	    });
	    

		new Thread(()->{
			List<Integer> pnoList = new ArrayList<>();
			
			//DB 조회
			try (var rs = DB.res(
					"select p_no from post join user using(u_no) where find_in_set(u_no, (select u_follow from user where u_no = ?));",
					User.uno)) {
				while (rs.next()) {
					pnoList.add(rs.getInt("p_no"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}

			//UI 처리
			Collections.shuffle(pnoList);
			
			SwingUtilities.invokeLater(()->{
				panel_1.removeAll();
				panel_1.setPreferredSize(new Dimension(w, h * pnoList.size()));
				
				//타이머를 이용해서 2개씩 추가한다.
				int[] idx = {0};
				
				Timer timer = new Timer(10, null);
				timer.addActionListener(e->{
					int addCnt = 0;

					while(idx[0]<pnoList.size() && addCnt<2) {
						int pno = pnoList.get(idx[0]);
						var pp = new C_패널(pno);
						pp.setBounds(0, h * idx[0], w, h);
						panel_1.add(pp);
						
						idx[0]++; addCnt++;
					}
					
					panel_1.revalidate();
					panel_1.repaint();

					loadVisibleViewPort();
					
					if(idx[0]>=pnoList.size()) ((Timer)e.getSource()).stop();
				});
				
				timer.start();
			});
		}).start();
	}
	
	int mx = 0;
	List<JPanel> pps = new ArrayList<JPanel>();

	private void areaA() {
		new Thread(()->{
			List<Integer> unos = new ArrayList<>();
			List<String> nicks = new ArrayList<>();

			//DB 조회
			try (var rs = DB.res("select u_no, u_nick from user where find_in_set(u_no, (select u_follow from user where u_no = ?));",
					User.uno)) {
				while (rs.next()) {
					unos.add(rs.getInt("u_no"));
	                nicks.add(rs.getString("u_nick"));
				}
			} catch (SQLException e) {
				e.printStackTrace();
				return;
			}
			
			//UI를 EDT(Event Dispatch Thread)에서 처리하도록 예약
			SwingUtilities.invokeLater(()->{
				panel_2.removeAll();
				pps.clear();
				
				int w = 50, h = 70;
				for(int i=0; i<unos.size(); i++) {
					int uno = unos.get(i);
					String nick = nicks.get(i);
					
					int idx = i;
					
					var pp = new JPanel();
					pp.setLayout(new BorderLayout());
					pp.setSize(w, h);
					pp.setLocation((w + 15) * i, 0);

					var profile = new Profile(true);
					pp.add(profile);
					pp.add(new JLabel(nick), "South");
					
					panel_2.add(pp);
					pps.add(pp);
					
					//이미지 로딩은 비동기 방식 유지
					getImage("profile/" + uno + ".jpg", 50, 50, x -> profile.setImage(x)).execute();
				}
				
				panel_2.revalidate();
				panel_2.repaint();	
			});
		}).start();
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
//			showPage(new I_검색());
			break;
		}
		case 2: {
//			showPage(new H_채팅());
			break;
		}
		case 3: {
//			showPage(new K_좋아요목록());
			break;
		}
		case 4: {
//			showPage(new L_게시물추가());
			break;
		}
		}
	}

	private class Panel_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
//			showPage(new E_스토리추가());
		}
	}
}
