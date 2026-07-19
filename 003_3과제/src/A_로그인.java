import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;


public class A_로그인 extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JTextField textField;
	public JButton button;
	public JLabel label_1;
	public JLabel label_2;
	public JPasswordField passwordField;

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

	public A_로그인() {
		getContentPane().setBackground(new Color(240, 240, 240));
		setTitle("로그인");
		setBounds(100, 100, 450, 433);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 10, 410, 301);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("ITGRAM");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(77, 33, 252, 55);
		panel.add(label);
		
		textField = new PlaceHolder("전화번호, 사용자 이름 또는 이메일");
		textField.setBounds(54, 102, 297, 49);
		panel.add(textField);
		textField.setColumns(10);
		
		button = new JButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(118, 179, 239));
		button.setBounds(54, 220, 297, 36);
		panel.add(button);
		
		passwordField = new PlaceHolder2("비밀번호");
		passwordField.setBounds(54, 161, 297, 49);
		panel.add(passwordField);
		
		panel_1 = new JPanel();
		panel_1.setBounds(12, 321, 410, 61);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel("계정이 없으신가요?");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(87, 23, 161, 15);
		panel_1.add(label_1);
		
		label_2 = new JLabel("가입하기");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_2.setForeground(new Color(118, 179, 239));
		label_2.setBounds(245, 23, 82, 15);
		panel_1.add(label_2);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try (var rs = DB.res("select * from user where u_id = ? and u_pw = ?", id, pw)) {
				if(rs.next()) {
					User.uname = rs.getString("u_nick");
					User.uno = rs.getInt(1);
					msgInfo(User.uname+"님 환영합니다.");
					showPage(new C_메인());
					textField.setText("");
					passwordField.setText("");
				}
				else {
					msgErr("일치하는 회원이 없습니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
class PlaceHolder2 extends JPasswordField {
	JLabel jl;
	public PlaceHolder2(String txt) {
		jl = new JLabel(txt);
		jl.setFont(new Font("맑은 고딕", 0, 12));
		jl.setEnabled(false);
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(new String(getPassword()).isEmpty());
	}
}
class PlaceHolder extends JTextField {
	JLabel jl;
	public PlaceHolder(String txt) {
		jl = new JLabel(txt);
		jl.setFont(new Font("맑은 고딕", 0, 12));
		jl.setEnabled(false);
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(getText().isEmpty());
	}
}
