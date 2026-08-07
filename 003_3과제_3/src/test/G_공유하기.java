package test;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class G_공유하기 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					G_공유하기 frame = new G_공유하기(1);
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
	int pno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public G_공유하기(int pno) {
		setTitle("공유하기");
		this.pno = pno;
		setBounds(100, 100, 311, 474);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("  팔로우한 유저에게 보내기");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setForeground(Color.BLACK);
		label.setBounds(0, 0, 295, 48);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 58, 295, 377);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	private void load() {
		try (var rs = DB.res("select * from user where find_in_set(u_no, (select u_follow from user where u_no =?));",User.uno)) {
			int w = 400, h = 40,i=0;
			while(rs.next()) {
				var pp = Rows.profileForComment(rs.getInt("u_no"), rs.getString("u_nick"), "", "게시물 보내기", w, h);
				pp.setLocation(0, 50*i);
				panel.add(pp);
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						showPage(new H_채팅());
					}
				});
				i++;
			}
			panel.setPreferredSize(new Dimension(0, 50*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
