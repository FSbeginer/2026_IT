package test;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.FlowLayout;

public class K_좋아요목록 extends BF {
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					K_좋아요목록 frame = new K_좋아요목록();
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
	public K_좋아요목록() {
		setBounds(100, 100, 450, 634);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 96, 434, 499);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		label = new JLabel("<");
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 62, 76);
		getContentPane().add(label);
		
		label_1 = new JLabel("내가 좋아요 누른 게시물");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(60, 10, 313, 31);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("로딩중...");
		label_2.setForeground(Color.GRAY);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(166, 48, 101, 31);
		getContentPane().add(label_2);
		
		load();
	}

	private void load() {
		try (var rs = DB.res("select * from likes join post using(p_no) where likes.u_no = ?",User.uno)) {
			int i = 0, w= 406, h=106;
			while(rs.next()) {
//				var pp = new K_패널(rs.getString("p_files").split(",")[0], rs.getInt("p_no"),rs.getString("u_nick"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
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
