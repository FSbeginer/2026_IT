import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JSeparator;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class F_댓글 extends BF {

	int pno;
	public JLabel label;
	public Profile panel;
	public JLabel label_1;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JTextField textField;
	public JLabel label_6;

	public F_댓글(int pno) {
		addWindowListener(new ThisWindowListener());
		setTitle("댓글");
		this.pno = pno;
		setBounds(100, 100, 716, 465);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("");
		label.setBounds(0, 0, 305, 426);
		getContentPane().add(label);

		panel = new Profile(false);
		panel.setBounds(335, 10, 41, 43);
		getContentPane().add(panel);

		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setBounds(385, 19, 174, 25);
		getContentPane().add(label_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(304, 58, 429, 239);
		getContentPane().add(scrollPane);

		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);

		label_2 = new JLabel("");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setBounds(314, 307, 41, 36);
		getContentPane().add(label_2);

		label_3 = new JLabel(getIcon("icons/send.png",35,35));
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setBounds(385, 307, 41, 36);
		getContentPane().add(label_3);

		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_4.setBounds(314, 349, 118, 15);
		getContentPane().add(label_4);

		label_5 = new JLabel("New label");
		label_5.setBounds(314, 374, 233, 15);
		getContentPane().add(label_5);

		textField = new JTextField();
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBounds(310, 396, 318, 23);
		getContentPane().add(textField);
		textField.setColumns(10);

		label_6 = new JLabel("게시");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_6.setForeground(new Color(118, 179, 239));
		label_6.setBounds(646, 395, 42, 25);
		getContentPane().add(label_6);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		
		load();
		loadReply();
	}

	private void loadReply() {
		panel_1.removeAll();
		int i = 0, h= 396/6;
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?", pno)) {
			rs.next();
			var pp = new G_패널(String.format("<html>%s<prev>  <span style ='font-size:12px; font-weight:normal;'>%s", rs.getString("u_nick"), rs.getString("p_content")), rs.getString("p_date"));
			pp.setSize(scrollPane.getWidth()-20, pp.getHeight());
			pp.setLocation(0, h*i);
			getImage(String.format("profile/%d.jpg", rs.getInt("u_no")), 58, 58, x->pp.panel.setImage(x)).execute();
			panel_1.add(pp);
			i++;
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try {
			var rs = DB.res("select * from reply join user using(u_no) where p_no = ?",pno);
			while(rs.next()) {
				var pp = new G_패널(String.format("<html>%s<prev>  <span style ='font-size:12px; font-weight:normal;'>%s", rs.getString("u_nick"), rs.getString("r_content")), rs.getString("r_date"));
				pp.setSize(scrollPane.getWidth()-20, pp.getHeight());
				pp.setLocation(0, h*i);
				getImage(String.format("profile/%d.jpg", rs.getInt("u_no")), 58, 58, x->pp.panel.setImage(x)).execute();
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0, h*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_1.revalidate();
		panel_1.repaint();
	}

	Image[] imgs;
	String[] files;
	int idx = 0;
	private Timer timer;

	private void load() {
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?", pno)) {
			rs.next();
			files = rs.getString("p_files").split(",");
			imgs = new Image[files.length];
			getImage(String.format("profile/%d.jpg", rs.getInt("u_no")), 38, 38, x->panel.setImage(x)).execute();
			label_1.setText(rs.getString("u_nick"));
			label_4.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			label_5.setText(rs.getString("p_date"));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try (var rs = DB.res("select * from likes where p_no = ? and u_no = ?", pno, User.uno)) {
			if (rs.next()) {
				label_2.setIcon(BF.getIcon("icons/heart2.png", 30, 30));
			} else {
				label_2.setIcon(BF.getIcon("icons/heart1.png", 30, 30));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		getImage("posts/" + files[idx] + ".jpg", label.getWidth(), label.getHeight(), x -> {
			imgs[idx] = x;
			label.setIcon(new ImageIcon(imgs[idx]));
		}).execute();
		
		timer = new Timer(1000, e -> {
			idx = ++idx%imgs.length;
			//2
			if (imgs[idx] == null) { 
				getImage("posts/" + files[idx] + ".jpg", label.getWidth(), label.getHeight(), x -> {
					imgs[idx] = x;
					label.setIcon(new ImageIcon(imgs[idx]));
				}).execute();
			}
			else {
				label.setIcon(new ImageIcon(imgs[idx]));
			}
		});
		timer.start();
	}

	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where p_no = ? and u_no = ?", pno, User.uno)) {
				if (!rs.next()) {
					DB.insert("likes", 0, pno, User.uno, LocalDateTime.now());
					label_2.setIcon(BF.getIcon("icons/heart2.png", 30, 30));
				} else {
					DB.delete("likes", "p_no = ? and u_no = ?", pno, User.uno);
					label_2.setIcon(BF.getIcon("icons/heart1.png", 30, 30));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}

	private class Label_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new G_공유하기(pno));
		}
	}
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if(timer!=null && timer.isRunning()) timer.stop();
		}
	}
	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			writeReply();
		}

	}
	private void writeReply() {
		if(textField.getText().isBlank()) {
			msgErr("빈칸이 있습니다.");
			return;
		}
		try {
			DB.insert("reply", 0,pno,User.uno,textField.getText(),LocalDateTime.now());
			msgInfo("댓글이 작성되었습니다.");
			textField.setText("");
			loadReply();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==KeyEvent.VK_ENTER) {
				writeReply();
			}
		}
	}
}
