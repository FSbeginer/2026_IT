import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;

public class A_로그인패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JTextField textField;
	public JLabel label_2;
	public JPasswordField passwordField;
	public JButton button;
	public JLabel label_3;
	public JSeparator separator;

	/**
	 * Create the panel.
	 */
	int no;
	public A_로그인패널(int no, String name) {
		setSize(310,325);
		this.no = no;
		setBorder(new LineBorder(new Color(0, 0, 0)));
		setLayout(null);
		
		label = new JLabel(name);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 24, 317, 29);
		add(label);
		
		label_1 = new JLabel("아이디");
		label_1.setBounds(10, 63, 57, 15);
		add(label_1);
		
		textField = new JTextField();
		textField.setBounds(20, 88, 285, 34);
		add(textField);
		textField.setColumns(10);
		
		label_2 = new JLabel("비밀번호");
		label_2.setBounds(10, 142, 57, 15);
		add(label_2);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(20, 167, 285, 34);
		add(passwordField);
		
		button = new JButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(10, 226, 295, 39);
		add(button);
		
		label_3 = new JLabel("지도에서 지역을 선택한 후 로그인하세요.");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(20, 275, 285, 15);
		add(label_3);
		
		separator = new JSeparator();
		separator.setBounds(12, 51, 285, 2);
		add(separator);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				BF.msgErr("빈칸이 존재합니다.");
				return;
			}
			try (var rs = DB.res("select * from user where id = ? and pw = ?",id,pw)) {
				if(rs.next()) {
					if(rs.getInt("lno")!=no) {
						BF.msgErr("지역을 확인하세요.");
						return;
					}
					User.uno = rs.getInt(1);
					User.lno = no;
					BF.msgInfo(rs.getString(2)+"님 환영합니다.");
					((BF)SwingUtilities.getWindowAncestor(button)).showPage(new B_메인());
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try (var rs = DB.res("select * from doctor where id = ? and pw = ?",id,pw)) {
				if(rs.next()) {
					if(rs.getInt("lno")!=no) {
						BF.msgErr("지역을 확인하세요.");
						return;
					}
					User.uno = rs.getInt(1);
					User.lno = no;
					BF.msgInfo(rs.getString(2)+"님 환영합니다.");
					((BF)SwingUtilities.getWindowAncestor(button)).showPage(new H_스케줄());
				}
				else {
					BF.msgErr("존재하지 않는 아이디입니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
