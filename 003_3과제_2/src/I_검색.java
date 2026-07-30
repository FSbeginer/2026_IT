import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Image;

import javax.swing.JTextField;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class I_검색 extends BF {
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label;
	public JTextField textField;
	public JLabel lblBlank;
	public JLabel lblUser;
	public JLabel lblPost;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public I_검색() {
		setTitle("검색");
		setBounds(100, 100, 450, 568);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 86, 434, 443);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		lblBlank = new JLabel("검색어를 입력하세요.");
		lblBlank.setForeground(Color.GRAY);
		lblBlank.setBounds(12, 10, 408, 26);
		panel.add(lblBlank);

		lblUser = new JLabel("유저");
		lblUser.setHorizontalAlignment(SwingConstants.CENTER);
		lblUser.setForeground(Color.BLACK);
		lblUser.setBounds(12, 46, 408, 26);
		panel.add(lblUser);

		lblPost = new JLabel("게시물");
		lblPost.setHorizontalAlignment(SwingConstants.CENTER);
		lblPost.setForeground(Color.BLACK);
		lblPost.setBounds(12, 82, 408, 26);
		panel.add(lblPost);

		label = new JLabel("<");
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 57, 66);
		getContentPane().add(label);

		textField = new JTextField();
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBackground(new Color(240, 240, 240));
		textField.setBounds(81, 23, 326, 47);
		getContentPane().add(textField);
		textField.setColumns(10);

		getData();
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	
	Map<Integer, ComentPanel> userPanels = new HashMap<>();
	Map<Integer,PostPanel> postPanels = new HashMap<Integer, PostPanel>();
	private void getData() {
		try {
			var rs = DB.res("select * from user");
			while (rs.next()) {
				var pp = new ComentPanel(new Profile("profile/" + rs.getInt("u_no") + ".jpg", 50, 50),
						"<html><b>" + rs.getString("u_nick"), rs.getString("u_name"));
				int uno = rs.getInt("u_no");
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new J_유저정보(uno));
					}
				});
				userPanels.put(uno, pp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}try {
			var rs = DB.res("select * from user right join post using(u_no)");
			while (rs.next()) {
				var pp = new PostPanel(rs.getInt("p_no"));
				postPanels.put(rs.getInt("p_no"), pp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	
	private void load() {
		panel.removeAll();
		var txt = textField.getText().replaceAll(" ", "");
		if (txt.isBlank()) {
			lblBlank.setLocation(10, 10);
			panel.add(lblBlank);
			panel.setPreferredSize(new Dimension(0, 0));
		} else {
			int sumH = 0;
			try {
				lblUser.setLocation(10, 10);
				panel.add(lblUser);
				sumH += 10+30;
				var rs = DB.res("select u_no from user where u_nick like '%" + txt + "%' or u_name like '%" + txt + "%'");
				int i = 0;
				while (rs.next()) {
					int uno = rs.getInt("u_no");
					var pp = userPanels.get(uno);
					pp.setLocation(10, sumH + 55 * i);
					panel.add(pp);
					i++;
				}
				panel.setPreferredSize(new Dimension(0, sumH + 55 * i));
				sumH = panel.getPreferredSize().height;
			} catch (SQLException e) {
				e.printStackTrace();
			}
			try {
				lblPost.setLocation(10, sumH + 10);
				sumH += 10+30;
				panel.add(lblPost);
				var rs = DB.res("select p_no from user right join post using(u_no) where p_content like '%" + txt
						+ "%' or u_nick like '%" + txt + "%'");
				int i = 0;
				while (rs.next()) {
					var pp = postPanels.get(rs.getInt("p_no"));
					pp.setLocation(10, sumH + 75 * i);
					panel.add(pp);
					i++;
				}
				panel.setPreferredSize(new Dimension(0, sumH + 75 * i));
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		panel.revalidate();
		panel.repaint();
	}

	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			dispose();
			previous();
		}
	}

	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			load();
		}
	}
}
