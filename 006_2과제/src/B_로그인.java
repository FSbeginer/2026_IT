import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class B_로그인 extends BF {
	public JLabel label;
	public JSeparator separator;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JButton button;
	public JPasswordField passwordField;
	
	Runnable r;
	public B_로그인(Runnable r) {
		this();
		this.r = r;
	}
	public B_로그인() {
		setTitle("로그인");
		setBounds(100, 100, 450, 255);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("LOGIN");
		label.setOpaque(true);
		label.setBackground(Color.WHITE);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(161, 23, 112, 31);
		getContentPane().add(label);
		
		separator = new JSeparator();
		separator.setBounds(12, 36, 410, 2);
		getContentPane().add(separator);
		
		label_1 = new JLabel(getIcon("icon/user.png", 25,25));
		label_1.setBounds(38, 71, 47, 31);
		getContentPane().add(label_1);
		
		label_2 = new JLabel(getIcon("icon/lock.png", 25,25));
		label_2.setBounds(38, 122, 47, 31);
		getContentPane().add(label_2);
		
		textField = new PlaceHolder("ID");
		textField.setFont(new Font("굴림", Font.PLAIN, 15));
		textField.setBounds(105, 71, 242, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		button = new RoundButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(33, 107, 245));
		button.setBounds(105, 163, 242, 31);
		getContentPane().add(button);
		
		passwordField = new PlaceHolder2("PW");
		passwordField.setFont(new Font("굴림", Font.PLAIN, 15));
		passwordField.setBounds(105, 122, 242, 31);
		getContentPane().add(passwordField);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 존재합니다.");
				return;
			}
			if(id.equals("admin")&&pw.equals("1234")) {
				msgInfo("관리자님 환영합니다.");
				showPage(new H_관리자());
				return;
			}
			try (var rs = DB.res("select * from user where id = ? and pw = ?", id, pw)) {
				if(rs.next()) {
					User.uno = rs.getInt(1);
					User.uname = rs.getString(2);
					msgInfo(User.uname+"님 환영합니다.");
					if(r!=null) r.run();
				}
				else {
					msgErr("해당 유저가 존재하지 않습니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
class SearchDTO{
	String start, end;

	public SearchDTO(String start, String end) {
		super();
		this.start = start;
		this.end = end;
	}
}
class PlaceHolder2 extends JPasswordField {
	JLabel jl;
	public PlaceHolder2(String txt) {
		jl = new JLabel(txt);
		jl.setFont(new Font("굴림", 0, 12));
		jl.setEnabled(false);
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(new String(getPassword()).isBlank());
	}
}
class PlaceHolder extends JTextField {
	JLabel jl;
	public PlaceHolder(String txt) {
		jl = new JLabel(txt);
		jl.setFont(new Font("굴림", 0, 12));
		jl.setEnabled(false);
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(getText().isBlank());
	}
}
