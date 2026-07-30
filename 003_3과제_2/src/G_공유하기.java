import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class G_공유하기 extends BF {


	int pno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public G_공유하기(int pno) {
		setTitle("공유하기");
		this.pno = pno;
		setBounds(100, 100, 322, 479);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("팔로우한 유저에게 보내기");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label.setHorizontalAlignment(SwingConstants.LEFT);
		label.setBounds(12, 10, 282, 40);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(3, 54, 303, 386);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	private void load() {
		try (var rs = DB.res("select * from user where find_in_set(u_no, (select u_follow from user where u_no = ?));",User.uno)) {
			int i = 0;
			while(rs.next()) {
				var pp = new ComentPanel(new Profile("profile/"+rs.getInt("u_no")+".jpg", 50, 50), 
						"<html><b>"+rs.getString("u_nick"), "게시물 보내기");
				pp.setLocation(0, 55*i);
				int uno = rs.getInt("u_no");
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new H_채팅(uno,pno));
					}
				});
				i++;
				panel.add(pp);
			}
			panel.setPreferredSize(new Dimension(0,55*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
