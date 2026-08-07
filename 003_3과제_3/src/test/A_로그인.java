package test;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 10, 410, 289);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("ITGRAM");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(69, 34, 279, 61);
		panel.add(label);
		
		textField = new PlaceHolder("전화번호, 사용자 이름 또는 이메일");
		textField.setBounds(69, 105, 279, 39);
		panel.add(textField);
		textField.setColumns(10);
		
		button = new JButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 255));
		button.setForeground(Color.WHITE);
		button.setBounds(69, 211, 279, 39);
		panel.add(button);
		
		passwordField = new PlaceHolder2("비밀번호");
		passwordField.setBounds(69, 154, 279, 39);
		panel.add(passwordField);
		
		panel_1 = new JPanel();
		panel_1.setBounds(12, 309, 410, 76);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel("계정이 없으신가요?");
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(74, 24, 186, 23);
		panel_1.add(label_1);
		
		label_2 = new JLabel("가입하기");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setForeground(new Color(0, 128, 255));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(226, 24, 92, 23);
		panel_1.add(label_2);
		setTitle("로그인");
		setBounds(100, 100, 450, 439);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new B_회원가입());
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id =textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try (var rs = DB.res("select * from user where u_id = ? and u_pw=?", id,pw)) {
				if(rs.next()) {
					User.uno = rs.getInt(1);
					msgInfo(rs.getString("u_name")+"님 환영합니다.");
					showPage(new C_메인());
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
class PlaceHolder extends JTextField {
	JLabel jl;
	public PlaceHolder(String txt) {
		jl= new JLabel(txt);
		jl.setEnabled(false);
		jl.setFont(new Font("맑은 고딕",1,12));
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(getText().isBlank());
	}
}
class PlaceHolder2 extends JPasswordField {
	JLabel jl;
	public PlaceHolder2(String txt) {
		jl= new JLabel(txt);
		jl.setEnabled(false);
		jl.setFont(new Font("맑은 고딕",1,12));
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(new String(getPassword()).isBlank());
	}
}
