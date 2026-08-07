package test;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class K_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;

	/**
	 * Create the panel.
	 */
	int pno;
	public K_패널(String path, int pno, String nick, String content, int likes, String date) {
		this.pno = pno;
		setSize(406, 106);
		setLayout(null);

		label = new JLabel("");
		Helper.getImage(path, 86, 86, false, x->label.setIcon(new ImageIcon(x)));
		label.setBounds(35, 10, 86, 86);
		add(label);

		label_1 = new JLabel(nick);
		label_1.setFont(new Font("굴림", Font.BOLD, 12));
		label_1.setBounds(134, 10, 260, 25);
		add(label_1);

		label_2 = new JLabel(content);
		label_2.setBounds(134, 35, 260, 25);
		add(label_2);

		label_3 = new JLabel(String.format("좋아요 %d개 • %s", likes, date));
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(133, 56, 260, 25);
		add(label_3);

		label_4 = new JLabel("좋아요 취소");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setForeground(Color.RED);
		label_4.setBounds(164, 81, 69, 25);
		add(label_4);
	}

	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try {
				DB.update("post", "p_like = p_like-1", "p_no=?", pno);
				DB.delete("likes", "p_no = ? and u_no =?", pno, User.uno);
				getParent().remove(K_패널.this);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
