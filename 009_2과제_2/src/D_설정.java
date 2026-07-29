import javax.swing.JPanel;
import java.awt.Color;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
public class D_설정 extends BP {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JButton button;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JButton button_1;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;

	/**
	 * Create the panel.
	 */
	public D_설정(MainFrame bf) {
		super(bf);
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 898, 61);
		add(panel);
		panel.setLayout(null);
		
		label_4 = new JLabel(getIcon("logo/user.png",40,40));
		label_4.setBounds(12, 10, 57, 41);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_5.setBounds(81, 10, 120, 41);
		panel.add(label_5);
		
		label_6 = new JLabel("New label");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_6.setBounds(578, 10, 308, 41);
		panel.add(label_6);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(12, 80, 898, 386);
		add(panel_1);
		panel_1.setLayout(null);
		
		label = new JLabel("이름");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label.setBounds(36, 54, 59, 32);
		panel_1.add(label);
		
		label_1 = new JLabel("ID");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setBounds(36, 96, 59, 32);
		panel_1.add(label_1);
		
		label_2 = new JLabel("PW");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setBounds(36, 138, 59, 32);
		panel_1.add(label_2);
		
		label_3 = new JLabel("거주지");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_3.setBounds(36, 180, 59, 32);
		panel_1.add(label_3);
		
		button = new JButton("수정하기");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(32, 246, 828, 32);
		panel_1.add(button);
		
		textField = new JTextField();
		textField.setBounds(107, 55, 645, 32);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(107, 96, 645, 32);
		panel_1.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(107, 138, 645, 32);
		panel_1.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(107, 180, 645, 32);
		panel_1.add(textField_3);
		
		button_1 = new JButton("거주지 변경");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(763, 184, 103, 23);
		panel_1.add(button_1);
		
		load();
	}
	
	private void load() {
		try (var rs = DB.res("select * from user join sub_area using(sno) join area using(ano) where uno = ?", User.uno)) {
			rs.next();
			label_5.setText(rs.getString("uname")+" 님");
			label_6.setText("잔액 : "+String.format("%,d원", rs.getInt("point")));
			textField.setText(rs.getString("uname"));
			textField_1.setText(rs.getString("id"));
			textField_2.setText(rs.getString("id"));
			textField_3.setText(rs.getString("aname")+"-"+rs.getString("sname"));
			sno = rs.getInt("sno");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var f = new D_거주지선택();
			f.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if(f.sno!=-1) {
						sno = f.sno;
						try {
							var rs = DB.res("select * from sub_area join area using(ano) where sno = ?",sno);
							rs.next();
							textField_3.setText(rs.getString("aname")+"-"+rs.getString("sname"));
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			f.setLocationRelativeTo(null);
			f.setVisible(true);
		}
	}
	int sno;
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var name = textField.getText();
			var pw = textField_2.getText();
			if(name.isBlank()||pw.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try {
				DB.update("user", "uname = ?, pw =?, sno = ?", "uno=?", name,pw,sno,User.uno);
				msgInfo("수정이 완료되었습니다.");
				load();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
