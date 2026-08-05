import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;

public class B_로그인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JPanel panel;
	public JLabel label_2;
	public JLabel label_3;
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
	Runnable r;
	
	public B_로그인(Runnable r) {
		this();
		this.r = r;
	}
	public B_로그인() {
		getContentPane().setBackground(new Color(236, 238, 244));
		getContentPane().setLayout(null);
		
		label = new JLabel("WELCOM BACK");
		label.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label.setForeground(Color.GRAY);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(107, 28, 190, 23);
		getContentPane().add(label);
		
		label_1 = new JLabel("LOGIN");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.BLACK);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label_1.setBounds(107, 63, 190, 50);
		getContentPane().add(label_1);
		
		panel = new RoundPanel();
		panel.setBounds(25, 142, 342, 175);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_2 = new JLabel("아이디");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setBounds(25, 22, 60, 21);
		panel.add(label_2);
		
		label_3 = new JLabel("비밀번호");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_3.setBounds(25, 92, 60, 21);
		panel.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(30, 49, 281, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(29, 119, 282, 30);
		passwordField.setEchoChar('\u25cf');
		panel.add(passwordField);
		
		button = new RoundButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(91, 98, 227));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		button.setBounds(18, 349, 364, 42);
		getContentPane().add(button);
		setTitle("로그인");
		setBounds(100, 100, 421, 473);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = passwordField.getText();
			if(id.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			if(id.equals("admin")&&pw.equals("1234")) {
				msgInfo("관리자님 환영합니다");
				User.uno = 0;
				dispose();
				previous();
				return;
			}
			try (var rs = DB.res("select * from user where uid = ? and upw = ?",id,pw)) {
				if(rs.next()) {
					User.uno = rs.getInt(1);
					msgInfo(rs.getString("unick")+"님 환영합니다.");
					dispose();
					previous();
					if(r!=null)r.run();
				}
				else {
					msgErr("아이디 또는 비밀번호가 틀렸습니다.");
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
		g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
		g2.dispose();
	}
}
class RoundButton extends JButton {
	public RoundButton(String txt) {
		super(txt);
		setForeground(Color.white);
		setBorder(null);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15, 15));
		g2.dispose();
		super.paintComponent(g);
	}
}