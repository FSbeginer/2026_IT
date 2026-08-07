package test;
import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class F_댓글 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_댓글 frame = new F_댓글(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param pno 
	 */
	int pno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_3;
	public JTextField textField;
	public JLabel label_6;
	public F_댓글(int pno) {
		addWindowListener(new ThisWindowListener());
		setTitle("불러오는 중...");
		this.pno = pno;
		setBounds(100, 100, 748, 488);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("");
		label.setBounds(0, 0, 323, 449);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(321, 54, 433, 256);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("");
		label_1.setBounds(335, 0, 385, 50);
		
		label_2 = new JLabel("");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setBounds(339, 316, 39, 31);
		getContentPane().add(label_2);
		
		label_4 = new JLabel("");
		label_4.setFont(new Font("굴림", Font.BOLD, 13));
		label_4.setBounds(335, 370, 210, 21);
		getContentPane().add(label_4);
		
		label_5 = new JLabel("");
		label_5.setBounds(334, 388, 210, 21);
		getContentPane().add(label_5);
		
		label_3 = new JLabel("");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setBounds(399, 316, 39, 31);
		getContentPane().add(label_3);
		
		textField = new JTextField();
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBounds(330, 408, 320, 29);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		label_6 = new JLabel("게시");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_6.setForeground(new Color(0, 128, 255));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setBounds(668, 405, 49, 33);
		getContentPane().add(label_6);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		setData();
		load();
	}
	private void load() {
		panel.removeAll();
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?",pno)) {
			rs.next();
			var pp = Rows.profileForComment(rs.getInt("u_no"),rs.getString("u_nick"), rs.getString("p_content"), rs.getTimestamp("p_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), 400, 50);
			panel.add(pp);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from reply join user using(u_no) where p_no = ?",pno)) {
			int i = 1, w=400, h =50;
			while(rs.next()) {
				var pp = Rows.profileForComment(rs.getInt("u_no"),rs.getString("u_nick"), rs.getString("r_content"), rs.getTimestamp("r_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")), w, h);
				pp.setLocation(0, 60*i);
				panel.add(pp);
				i++;
			}
			panel.setPreferredSize(new Dimension(0, 60*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.revalidate();
		panel.repaint();
	}

	volatile int generation;
	int totGeneration;
	
	List<Image> imgs = new ArrayList<Image>();
	private void setData() {
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?",pno)) {
			rs.next();
			var files = rs.getString("p_files").split(",");
			for (var file : files) {
				Helper.getImage("posts/"+file+".jpg", label.getWidth(), label.getHeight(), false, x->{
					imgs.add(x);
					if(++generation==totGeneration) {
						setTitle("댓글");
						lendering();
					}
				});
				totGeneration++;
				label_3.setIcon(getIcon("icons/send.png",35,35));
				label_4.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
				label_5.setText(rs.getString("p_date"));
				
				remove(label_1);
				label_1 = Rows.profileForComment(rs.getInt("u_no"), rs.getString("u_nick"),  385, 50);
				label_1.setBounds(335, 0, 385, 50);
				getContentPane().add(label_1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from likes where u_no = ? and p_no = ?",User.uno,pno)) {
			if(rs.next()) {
				label_2.setIcon(BF.getIcon("icons/heart2.png",40,40));
			}
			else {
				label_2.setIcon(BF.getIcon("icons/heart1.png",40,40));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	Timer timer;
	int tidx=0;
	private void lendering() {
		timer = new Timer(1000, e->{
			label.setIcon(new ImageIcon(imgs.get(tidx)));
			tidx = ++tidx % imgs.size();
		});
		timer.setInitialDelay(0);
		timer.start();
	}
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where u_no = ? and p_no = ?",User.uno,pno)) {
				if(rs.next()) {
					DB.update("post", "p_like = p_like-1","p_no=?", pno);
					DB.delete("likes", "p_no = ? and u_no =?", pno,User.uno);
					label_2.setIcon(BF.getIcon("icons/heart1.png",40,40));
				}
				else {
					DB.update("post", "p_like = p_like+1","p_no=?", pno);
					DB.insert("likes", 0,pno, User.uno,LocalDateTime.now());
					label_2.setIcon(BF.getIcon("icons/heart2.png",40,40));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try (var rs = DB.res("select * from post join user using(u_no) where p_no = ? ",pno)) {
				rs.next();
				label_4.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			} catch (SQLException e1) {
				e1.printStackTrace();
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
			if(timer!=null && timer.isRunning())
				timer.stop();
		}
	}
	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			addReply();
		}

	}
	private void addReply() {
		var txt =textField.getText();
		if(txt.isBlank()) {
			msgErr("댓글을 입력하세요.");
			return;
		}
		try {
			DB.insert("reply", 0,pno,User.uno,txt,LocalDateTime.now());
			msgInfo("댓글이 작성되었습니다.");
			textField.setText("");
			load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if(e.getKeyCode()==e.VK_ENTER) {
				addReply();
			}
		}
	}
}
