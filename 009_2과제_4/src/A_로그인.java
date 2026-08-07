import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class A_로그인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JPasswordField passwordField;
	public JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_로그인 frame = new A_로그인();
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
	public A_로그인() {
		setTitle("로그인");
		setBounds(100, 100, 452, 460);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("iDelivery");
		label.setIcon(getIcon("logo/logo.png",100,100));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(134, 69, 167, 164);
		getContentPane().add(label);
		
		label_1 = new JLabel("ID");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(63, 258, 41, 34);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("PW");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_2.setBounds(61, 306, 41, 34);
		getContentPane().add(label_2);
		
		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		textField.setBounds(123, 260, 249, 36);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		passwordField.setBounds(123, 307, 249, 33);
		passwordField.setEchoChar('\u25cf');
		getContentPane().add(passwordField);
		
		button = new JButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		button.setBounds(50, 354, 325, 34);
		getContentPane().add(button);

	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try (var rs = DB.res("select * from user where id = ? and pw = ?",id,pw)) {
				if(rs.next()) {
					User.uno = rs.getInt(1);
					msgInfo(rs.getString("uname")+"님 환영합니다.");
					showpage(new MainFrame());
					textField.setText("");
					passwordField.setText("");
					textField.requestFocus();
				}else {
					msgErr("회원 정보가 일치하지 않습니다.");
					textField.setText("");
					passwordField.setText("");
					textField.requestFocus();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
