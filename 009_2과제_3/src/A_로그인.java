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
					frame.setLocationRelativeTo(null);
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
		setBounds(100, 100, 457, 449);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("iDelivery");
		label.setIcon(getIcon("logo/logo.png",150,150));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setHorizontalTextPosition(SwingConstants.CENTER);
		label.setVerticalTextPosition(SwingConstants.BOTTOM);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(131, 45, 190, 179);
		getContentPane().add(label);
		
		label_1 = new JLabel("ID");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setBounds(71, 244, 51, 43);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("PW");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBounds(71, 297, 51, 43);
		getContentPane().add(label_2);
		
		textField = new JTextField();
		textField.setBounds(131, 254, 243, 33);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(131, 307, 243, 33);
		passwordField.setEchoChar('\u25cf');
		getContentPane().add(passwordField);
		
		button = new JButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(71, 350, 302, 33);
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
			try (var rs = DB.res("select * from user where id =? and pw=?", id, pw)) {
				if(rs.next()) {
					User.uno = rs.getInt(1);
					msgInfo(rs.getString("uname")+"님 환영합니다.");
					textField.setText("");
					passwordField.setText("");
					showPage(new MainFrame());
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
