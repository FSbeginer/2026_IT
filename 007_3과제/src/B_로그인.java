import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class B_로그인 extends BF {
	Runnable r;
	public JLabel label;
	public JLabel label_1;
	public JPanel panel;
	public JButton button;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField textField;
	public JPasswordField passwordField;
	public B_로그인(Runnable r) {
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		
		label = new JLabel("WELCOME BACK");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		label.setForeground(Color.GRAY);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(136, 60, 162, 21);
		getContentPane().add(label);
		
		label_1 = new JLabel("LOGIN");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 34));
		label_1.setBounds(106, 93, 222, 49);
		getContentPane().add(label_1);
		
		panel = new RoundPanel();
		panel.setBounds(29, 170, 374, 157);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_2 = new JLabel("아이디");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBounds(20, 22, 57, 20);
		panel.add(label_2);
		
		label_3 = new JLabel("비밀번호");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_3.setBounds(20, 82, 71, 20);
		panel.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(21, 44, 326, 31);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(23, 110, 326, 31);
		panel.add(passwordField);
		
		button = new RoundButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(113, 116, 221));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		button.setBounds(29, 348, 374, 40);
		getContentPane().add(button);
		setTitle("로그인");
		this.r= r;
		setBounds(100, 100, 450, 465);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			if(id.equals("admin")&&pw.equals("1234")) {
				msgInfo("관리자님 환영합니다.");
				User.uno=0;
				dispose();
				return;
			}
			try (var rs = DB.res("select * from user where uid = ? and upw =?",id,pw)) {
				if(rs.next()) {
					User.uno = rs.getInt("uno");
					User.uname= rs.getString("unick");
					msgInfo(User.uname+"님 환영합니다.");
					if(r !=null) r.run();
					else dispose();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
class RoundPanel extends JPanel{
	public RoundPanel() {
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
	}
}
