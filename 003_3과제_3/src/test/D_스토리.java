package test;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class D_스토리 extends BF {

	/**
	 * Create the frame.
	 * 
	 * @param unos
	 * @param idx
	 */
	int idx;
	List<Integer> unos;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;

	public D_스토리(int idx, List<Integer> unos) {
		addWindowListener(new ThisWindowListener());
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		label_3 = new JLabel("<");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(12, 242, 45, 113);
		getContentPane().add(label_3);

		label_4 = new JLabel(">");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label_4.setForeground(Color.WHITE);
		label_4.setBounds(332, 242, 45, 113);
		getContentPane().add(label_4);

		label_1 = new JLabel("");
		label_1.setBounds(22, 74, 343, 462);
		getContentPane().add(label_1);

		label_2 = new JLabel("불러오는 중....");
		label_2.setForeground(Color.GRAY);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(135, 567, 131, 34);
		getContentPane().add(label_2);
		setTitle("불러오는 중...");
		this.idx = idx;
		this.unos = unos;
		setBounds(100, 100, 404, 650);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		label = new JLabel("불러오는 중...");
		label.setBounds(12, 10, 364, 54);
		getContentPane().add(label);
		
		getData();
	}

	Map<Integer, List<JLabel>> profilesByUno = new HashMap<>();
	Map<Integer, List<Image>> imgsByUno = new HashMap<>();

	volatile int generation = 0;
	int maxGeneration = 0;

	private void getData() {
		for (var uno : unos) {
			try {
				var rs = DB.res(
						"select * from user join story using(u_no) where find_in_set(u_no, (select u_follow from user where u_no =?)) and u_no = ?;",
						User.uno, uno);
				List<Image> imgs = new ArrayList<Image>();
				List<JLabel> profiles = new ArrayList<JLabel>();
				while (rs.next()) {
					profiles.add(Rows.profileStory(rs.getInt("u_no"), rs.getString("u_nick"), rs.getString("s_content"),
							364, 40));
					Helper.getImage("story/" + rs.getInt("s_no") + ".jpg", label_1.getWidth(), label_1.getHeight(),
							false, x -> {
								imgs.add(x);
								generation++;
								if (maxGeneration == generation) {
									setTitle("스토리");
									lendering();
								}
							});
					maxGeneration++;
				}
				profilesByUno.put(uno, profiles);
				imgsByUno.put(uno, imgs);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	Timer timer;
	int tidx = 0;

	private void lendering() {
		if (timer != null && timer.isRunning()) {
			timer.stop();
		}
		tidx = 0;
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				remove(label);
				label = profilesByUno.get(unos.get(idx)).get(tidx);
				label.setBounds(12, 10, 364, 40);
				getContentPane().add(label);
				label.repaint();
				label_1.setIcon(new ImageIcon(imgsByUno.get(unos.get(idx)).get(tidx)));
				label_2.setText(String.format("스토리 %d / %d", tidx + 1, imgsByUno.get(unos.get(idx)).size()));

				tidx = ++tidx % imgsByUno.get(unos.get(idx)).size();
			}
		});
		timer.setInitialDelay(0);
		timer.start();

		label_3.setEnabled(idx != 0);
		label_4.setEnabled(idx != unos.size() - 1);
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (timer != null && timer.isRunning()) {
				timer.stop();
			}
		}
	}

	private class Label_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (label_3.isEnabled()) {
				idx--;
				lendering();
			}
		}
	}

	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if (label_4.isEnabled()) {
				idx++;
				lendering();
			}
		}
	}
}
