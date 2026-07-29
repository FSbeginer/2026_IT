import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.DebugGraphics;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class B_로그인 extends BF {

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
	Runnable r;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JPasswordField passwordField;
	public JButton button;
	
	public B_로그인(Runnable r) {
		this();
		this.r = r;
	}

	public B_로그인() {
		setTitle("로그인");
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("LOGIN");
		label.setOpaque(true);
		label.setDebugGraphicsOptions(DebugGraphics.LOG_OPTION);
		label.setBackground(Color.WHITE);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(172, 21, 89, 32);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBackground(Color.BLACK);
		panel.setBounds(12, 34, 410, 2);
		getContentPane().add(panel);
		
		label_1 = new JLabel(getIcon("icon/user.png",30,30));
		label_1.setBounds(48, 87, 38, 39);
		getContentPane().add(label_1);
		
		label_2 = new JLabel(getIcon("icon/lock.png",30,30));
		label_2.setBounds(48, 142, 38, 39);
		getContentPane().add(label_2);
		
		textField = new PlaceHolder("ID");
		textField.setBounds(114, 87, 234, 39);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		passwordField = new PlaceHolder2("PW");
		passwordField.setBounds(114, 146, 234, 35);
		getContentPane().add(passwordField);
		
		button = new RoundButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(34, 44, 215));
		button.setForeground(Color.WHITE);
		button.setBounds(114, 191, 234, 32);
		getContentPane().add(button);

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
				showpage(new H_관리자());
				return;
			}
			try (var rs = DB.res("select * from user where id = ? and pw = ?",id,pw)) {
				if(rs.next()) {
					User.uno = rs.getInt(1);
					msgInfo(rs.getString("name")+"님 환영합니다.");
					if(r!=null) {
						dispose();
						r.run();
					}
					else {
						dispose();
						previous();
					}
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
class PlaceHolder extends JTextField {
	JLabel jl;
	public PlaceHolder(String txt) {
		jl=  new JLabel(txt);
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
class PlaceHolder2 extends JPasswordField{
	JLabel jl;
	public PlaceHolder2(String txt) {
		jl=  new JLabel(txt);
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

