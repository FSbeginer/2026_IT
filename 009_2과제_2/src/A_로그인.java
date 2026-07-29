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

public class A_로그인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JButton button;
	public JTextField textField;
	public JPasswordField passwordField;

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
		setBounds(100, 100, 387, 507);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("iDelivery",0);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setIcon(getIcon("logo/logo.png",100,100));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setBounds(75, 50, 208, 173);
		getContentPane().add(label);
		
		label_1 = new JLabel("ID");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setBounds(37, 268, 47, 34);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("PW");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBounds(37, 326, 47, 34);
		getContentPane().add(label_2);
		
		button = new JButton("로그인");
		button.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		button.addActionListener(new ButtonActionListener());
		button.setBounds(35, 375, 292, 34);
		getContentPane().add(button);
		
		textField = new JTextField();
		textField.setBounds(96, 268, 231, 34);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(96, 326, 231, 34);
		passwordField.setEchoChar('\u25cf');
		getContentPane().add(passwordField);

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
					showPage(new MainFrame());
					textField.setText("");
					passwordField.setText("");
					textField.requestFocus();
				}
				else {
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
