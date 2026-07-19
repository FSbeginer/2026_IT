import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class G_공유하기 extends BF {

	int pno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public G_공유하기(int pno) {
		setTitle("공유하기");
		this.pno = pno;
		setBounds(100, 100, 344, 495);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("팔로우한 유저에게 보내기");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(12, 10, 273, 40);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(22, 60, 306, 396);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		load();
	}
	private void load() {
		try (var rs = DB.res("select * from user where find_in_set(u_no, (select u_follow from user where u_no = ?));", User.uno)) {
			int w = 286, h = 396/6, i = 0;
			while(rs.next()) {
				var pp = new G_패널(rs.getString("u_nick"));
				getImage("profile/"+rs.getInt("u_no")+".jpg", pp.panel.getWidth(), pp.panel.getHeight(), x->pp.panel.setImage(x)).execute();;
				pp.setLocation(0, h*i);
				int uno =rs.getInt("u_no");
				pp.addMouseListener(new MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						showPage(new H_채팅(uno, pno));
					};
				});
				panel.add(pp);
				i++;
			}
			panel.setPreferredSize(new Dimension(0,h*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
