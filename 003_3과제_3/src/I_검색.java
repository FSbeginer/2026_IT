import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class I_검색 extends BF {
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label;
	public JTextField textField;
	public JLabel lblBlank;
	public JLabel lblUser;
	public JLabel lblPost;


	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_검색 frame = new I_검색();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}


	public I_검색() {
		setTitle("검색");
		setBounds(100, 100, 390, 562);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 68, 374, 455);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		lblBlank = new JLabel("검색어를 입력하세요.");
		lblBlank.setForeground(Color.GRAY);
		lblBlank.setHorizontalAlignment(SwingConstants.LEFT);
		lblBlank.setBounds(12, 10, 348, 39);
		panel.add(lblBlank);

		lblUser = new JLabel("유저");
		lblUser.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setForeground(Color.BLACK);
		lblUser.setBounds(14, 51, 348, 39);
		panel.add(lblUser);

		lblPost = new JLabel("게시물");
		lblPost.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lblPost.setHorizontalAlignment(SwingConstants.CENTER);
		lblPost.setForeground(Color.BLACK);
		lblPost.setBounds(14, 91, 348, 39);
		panel.add(lblPost);

		label = new JLabel("<");
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 44, 46);
		getContentPane().add(label);

		textField = new JTextField();
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBackground(new Color(240, 240, 240));
		textField.setBounds(68, 10, 294, 46);
		getContentPane().add(textField);
		textField.setColumns(10);

		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}

	Map<Integer, JLabel> lblUsers = new HashMap<Integer, JLabel>();
	Map<Integer, JLabel> lblPosts = new HashMap<>();

	private void load() {
		panel.removeAll();
		var txt = textField.getText().replaceAll(" ", "");
		int sumH = 0;
		if (txt.isBlank()) {
			panel.add(lblBlank);
			lblBlank.setLocation(10, 10);
		} else {
			txt = "%" + txt + "%";
			try {
				panel.add(lblUser);
				lblUser.setLocation(10, 10);
				sumH += lblUser.getHeight() + 10;

				var rs = DB.res("select * from user where u_name like ? or u_nick like ?", txt, txt);
				int i = 0;
				while (rs.next()) {
					int uno = rs.getInt("u_no");
					JLabel pp;
					if (!lblUsers.containsKey(uno)) {
						pp = Rows.profileForComment(uno, rs.getString("u_nick"), "", rs.getString("u_name"), 400,
								40);
						pp.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								showPage(new J_유저정보(uno));
							}
						});
						lblUsers.put(uno, pp);
					}
					else {
						pp = lblUsers.get(uno);
					}
					pp.setLocation(10, sumH + 55 * i);
					panel.add(pp);
					i++;
				}
				sumH += 55 * i;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				panel.add(lblPost);
				lblPost.setLocation(10, sumH + 10);
				sumH += lblPost.getHeight() + 10;
				var rs = DB.res("select * from post join user using(u_no) where p_content like ? or u_nick like ?", txt,
						txt);
				int i = 0;
				while (rs.next()) {
					JLabel pp;
					int pno = rs.getInt("p_no");
					if(!lblPosts.containsKey(pno)) {
						pp = Rows.postForSearch(rs.getInt("p_no"), rs.getString("u_nick"), rs.getString("p_content"),
							rs.getString("p_files").split(",")[0], 400, 80);
						pp.addMouseListener(new MouseAdapter() {
							@Override
							public void mouseClicked(MouseEvent e) {
								showPage(new F_댓글(pno));
							}
						});
						lblPosts.put(pno, pp);
					}
					else {
						pp = lblPosts.get(pno);
					}
					pp.setLocation(10, sumH + 90 * i);
					panel.add(pp);
					i++;
				}
				panel.setPreferredSize(new Dimension(0, sumH + 90 * i));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		panel.revalidate();
		panel.repaint();
	}

	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			load();
		}
	}

	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			dispose();
			previous();
		}
	}
}
