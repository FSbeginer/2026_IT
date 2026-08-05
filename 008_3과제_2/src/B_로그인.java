import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class B_로그인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JButton button;
	public JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B_로그인 frame = new B_로그인();
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
	public B_로그인() {
		setTitle("로그인");
		setBounds(100, 100, 509, 246);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("LOGIN");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 25));
		label.setForeground(new Color(0, 128, 0));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 469, 48);
		getContentPane().add(label);
		
		label_1 = new JLabel("아이디");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(48, 68, 62, 31);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("비밀번호");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_2.setBounds(48, 109, 62, 31);
		getContentPane().add(label_2);
		
		textField = new JTextField();
		textField.setBounds(122, 68, 309, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		button = new JButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0, 128, 0));
		button.setBounds(122, 150, 309, 40);
		getContentPane().add(button);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(121, 105, 309, 31);
		getContentPane().add(passwordField);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id =textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgInfo("빈칸이 존재합니다.");
				return;
			}
			if(id.equals("admin")&&pw.equals("1234")) {
				msgInfo("관리자님 환영합니다.");
				showPage(new H_관리자());
				return;
			}
			try (var rs = DB.res("select * from user where id = ?")) {
				if(rs.next()) {
					if(pw.equals(rs.getString("pw"))) {
						User.uno = rs.getInt("uno");
						msgInfo(rs.getString("name")+"님 로그인에 성공하였습니다.");
						dispose();
						previous();
					}
					else {
						msgErr("비밀번호가 올바르지 않습니다.");
					}
				} else {
					msgErr("아이디가 존재하지 않습니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
