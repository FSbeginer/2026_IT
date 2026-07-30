import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class F_댓글 extends BF {

	List<Image> imgs;
	int pno;

	public F_댓글(int pno, List<Image> imgs) {
		addWindowListener(new ThisWindowListener());
		setTitle("댓글");
		this.pno = pno;
		this.imgs = imgs;
		setBounds(100, 100, 735, 496);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("");
		label.setBounds(0, 0, 339, 457);
		getContentPane().add(label);

		panel_1 = new JPanel();
		panel_1.setLayout(null);
		panel_1.setBounds(351, 10, 49, 44);
		getContentPane().add(panel_1);

		label_1 = new JLabel("New label");
		label_1.setFont(new Font("굴림", Font.BOLD, 14));
		label_1.setBounds(402, 12, 231, 44);
		getContentPane().add(label_1);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(new LineBorder(new Color(240, 240, 240)));
		scrollPane.setBounds(338, 64, 407, 265);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		label_2 = new JLabel("");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setBounds(374, 339, 37, 32);
		getContentPane().add(label_2);

		label_3 = new JLabel(getIcon("icons/send.png",30,30));
		label_3.setBounds(423, 339, 37, 32);
		getContentPane().add(label_3);

		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_4.setBounds(351, 370, 272, 22);
		getContentPane().add(label_4);

		label_5 = new JLabel("New label");
		label_5.setForeground(Color.LIGHT_GRAY);
		label_5.setBounds(351, 391, 272, 22);
		getContentPane().add(label_5);

		textField = new JTextField();
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBounds(351, 413, 301, 34);
		getContentPane().add(textField);
		textField.setColumns(10);

		label_6 = new JLabel("게시");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_6.setHorizontalAlignment(SwingConstants.CENTER);
		label_6.setForeground(new Color(0, 128, 255));
		label_6.setBounds(660, 409, 54, 39);
		getContentPane().add(label_6);

		load();
		addComent();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}

	private void addComent() {
		panel.removeAll();
		panel.add(me);
		try (var rs = DB.res("select * from reply join user using(u_no) where p_no = ?",pno)) {
			int i = 1;
			while(rs.next()) {
				var pp = new ComentPanel(new Profile("profile/"+rs.getInt("u_no")+".jpg", 50, 50),
						String.format("<html><b>%s</b> %s", rs.getString("u_nick"),rs.getString("r_content")),
						rs.getTimestamp("r_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")));
				pp.setLocation(0, 55*i);
				panel.add(pp);
				i++;
			}
			panel.setPreferredSize(new Dimension(0,55*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.revalidate();
		panel.repaint();
	}

	Timer timer;
	public JLabel label;
	public JPanel panel_1;
	public JLabel label_1;
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JTextField textField;
	public JLabel label_6;
	int idx = 0;
	ComentPanel me;
	private void load() {
		timer = new Timer(1000, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				label.setIcon(new ImageIcon(imgs.get(idx).getScaledInstance(label.getWidth(), label.getHeight(), 4)));
				idx = ++idx % imgs.size();
			}
		});
		timer.setInitialDelay(0);
		timer.start();
		
		try (var rs = DB.res("select * from user join post using(u_no)  where p_no = ?",pno)) {
			rs.next();
			panel_1.add(new Profile("profile/"+rs.getInt("u_no")+".jpg", 40, 40));
			label_1.setText(rs.getString("u_nick"));
			me =new ComentPanel(
					new Profile("profile/"+rs.getInt("u_no")+".jpg", 50, 50),
					String.format("<html><b>%s </b>%s", rs.getString("u_nick"),rs.getString("p_content")),
					rs.getTimestamp("p_date").toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"))
					);
			panel.add(me);
			label_5.setText(rs.getString("p_date"));
			label_4.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		try (var rs = DB.res("select * from likes where u_no = ? and p_no =?;",User.uno, pno)) {
			if(rs.next()) {
				label_2.setIcon(BF.getIcon("icons/heart2.png",30,30));
			}
			else {
				label_2.setIcon(BF.getIcon("icons/heart1.png",30,30));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			if (timer != null && timer.isRunning())
				timer.stop();
		}
	}
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where u_no = ? and p_no =?;",User.uno, pno)) {
				if(rs.next()) {
					DB.update("post", "p_like=p_like-1", "p_no=?", pno);
					DB.delete("likes", "p_no =? and u_no = ?", pno,User.uno);
					label_2.setIcon(BF.getIcon("icons/heart1.png",30,30));
				}
				else {
					DB.update("post", "p_like=p_like+1", "p_no=?", pno);
					DB.insert("likes", 0,pno,User.uno,LocalDate.now());
					label_2.setIcon(BF.getIcon("icons/heart2.png",30,30));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?",pno)) {
				rs.next();
				label_4.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			} catch (SQLException e2) {
			}
		}
	}
	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			reply();
		}

	}
	private void reply() {
		var txt= textField.getText();
		if(txt.isBlank()) {
			msgErr("댓글을 입력하세요.");
			return;
		}
		try {
			DB.insert("reply", 0,pno,User.uno,txt,LocalDateTime.now());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		msgInfo("댓글이 작성되었습니다.");
		textField.setText("");
		addComent();
	}
	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyPressed(KeyEvent e) {
			if(e.getKeyCode()==e.VK_ENTER) {
				reply();
			}
		}
	}
}