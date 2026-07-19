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

public class B_로그인 extends BF {
	public JPanel panel;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JPasswordField passwordField;
	public JButton button;

	public B_로그인() {
		setTitle("로그인");
		setBounds(100, 100, 475, 321);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		JLabel label = new JLabel("LOGIN");
		label.setBackground(Color.WHITE);
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 28));
		label.setBounds(167, 29, 125, 33);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(10, 47, 439, 2);
		getContentPane().add(panel);
		
		label_1 = new JLabel(getIcon("icon/user.png",40,40));
		label_1.setBounds(56, 92, 45, 42);
		getContentPane().add(label_1);
		
		label_2 = new JLabel(getIcon("icon/lock.png",40,40));
		label_2.setBounds(56, 153, 45, 42);
		getContentPane().add(label_2);
		
		textField = new PlaceHolder("ID");
		textField.setBounds(111, 96, 246, 33);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new PlaceHolderPass("PW");
		passwordField.setBounds(111, 162, 246, 33);
		getContentPane().add(passwordField);
		
		button = new RoundButton("로그인");
		button.setBackground(new Color(71, 94, 218));
		button.addActionListener(new ButtonActionListener());
		button.setBounds(112, 212, 252, 33);
		getContentPane().add(button);

	}
	Runnable r;
	public B_로그인(Runnable r) {
		this();
		this.r=r;
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw= new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 존재합니다.");
				return;
			}
			if(id.equals("admin")&&pw.equals("1234")) {
				msgInfo("관리자님 환영합니다.");
				showPage(new H_관리자());
				return;
			}
			try (var rs = DB.res("select * from user where id = ? and pw =?",id,pw)) {
				if(rs.next()) {
					User.uno = rs.getInt("uno");
					msgInfo(rs.getString("name")+"님 환영합니다.");
					dispose();
					prev();
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
class PlaceHolder  extends JTextField {
	JLabel jl;
	public PlaceHolder(String txt) {
		jl = new JLabel(txt);
		jl.setEnabled(false);
		jl.setFont(new Font("맑은 고딕", 0 ,12));
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(getText().isBlank());
	}
}
class PlaceHolderPass  extends JPasswordField{
	JLabel jl;
	public PlaceHolderPass(String txt) {
		jl = new JLabel(txt);
		jl.setEnabled(false);
		jl.setFont(new Font("맑은 고딕", 0 ,12));
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(new String(getPassword()).isBlank());
	}
}