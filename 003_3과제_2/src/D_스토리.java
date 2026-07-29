import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.BorderLayout;

public class D_스토리 extends BF {

	/**
	 * Create the frame.
	 * 
	 * @param unos
	 */
	List<Integer> unos;
	int idx;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;

	public D_스토리(List<Integer> unos, int idx) {
		addWindowListener(new ThisWindowListener());
		getContentPane().setBackground(Color.BLACK);
		setTitle("스토리");
		this.unos = unos;
		this.idx = idx;
		setBounds(100, 100, 390, 606);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(12, 23, 53, 51);
		getContentPane().add(panel);
		panel.setLayout(new BorderLayout(0, 0));

		label = new JLabel("New label");
		label.setForeground(Color.WHITE);
		label.setBounds(77, 23, 242, 21);
		getContentPane().add(label);

		label_1 = new JLabel("New label");
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(77, 53, 242, 21);
		getContentPane().add(label_1);

		label_2 = new JLabel("");
		label_2.setBounds(34, 84, 307, 410);
		getContentPane().add(label_2);

		label_3 = new JLabel("<");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(0, 236, 33, 81);
		getContentPane().add(label_3);

		label_4 = new JLabel(">");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label_4.setBounds(341, 236, 33, 81);
		getContentPane().add(label_4);

		label_5 = new JLabel("New label");
		label_5.setForeground(Color.GRAY);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(123, 522, 136, 35);
		getContentPane().add(label_5);

		load();
		SwingUtilities.invokeLater(() -> lendering());
	}

	int tidx;
	Timer timer;
	private void lendering() {
		if(timer!=null&&timer.isRunning()) {
			timer.stop();
		}
		panel.removeAll();
		var profile = profilesByUno.get(unos.get(idx));
		panel.add(profile);
		
		var datas = storyDatasByUno.get(unos.get(idx));
		tidx = 0;
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				var data = datas.get(tidx);
				label.setText(data.nick);
				label_1.setText(data.s_content);
				label_2.setIcon(new ImageIcon(data.img));
				label_5.setText(String.format("스토리 %d / %d", tidx+1,datas.size()));
				tidx = ++tidx % datas.size();
			}
		});
		timer.setInitialDelay(0);
		timer.start();
		label_3.setEnabled(idx!=0);
		label_4.setEnabled(idx!=unos.size()-1);
	}

	private void load() {
		for (var uno : unos) {
			try {
				var rs = DB.res("select * from story join user using(u_no) where u_no = ?", uno);
				List<StoryData> storyDatas = new ArrayList<StoryData>();
				while (rs.next()) {
					String nick = rs.getString("u_nick");
					String content = rs.getString("s_content");
					Helper.getImage("story/" + rs.getString("s_file") + ".jpg", label_2.getWidth(), label_2.getHeight(),
							x -> storyDatas.add(new StoryData(x, nick, content)));
				}
				storyDatasByUno.put(uno, storyDatas);
				var profile = new Profile("profile/"+uno+".jpg", 50, 50, true, Color.white);
				profile.setBackground(Color.black);
				profilesByUno.put(uno, profile);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	Map<Integer, List<StoryData>> storyDatasByUno = new HashMap<Integer, List<StoryData>>();
	Map<Integer, Profile> profilesByUno = new HashMap<Integer, Profile>();
	
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
			if(label_4.isEnabled()) {
				idx++;
				lendering();
			}
		}
	}
	class StoryData{
		Image img;
		String nick, s_content;
		public StoryData(Image img, String nick, String s_content) {
			this.img = img;
			this.nick = nick;
			this.s_content = s_content;
		}
	}
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if(timer!=null&&timer.isRunning())
				timer.stop();
		}
	}
}
