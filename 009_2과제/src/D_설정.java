import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.SwingConstants;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.event.ActionEvent;

public class D_설정 extends BP {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JButton button;
	public JButton button_1;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;

	/**
	 * Create the panel.
	 */
	public D_설정() {
		setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel.setBounds(12, 14, 851, 64);
		add(panel);
		panel.setLayout(null);
		
		label_4 = new JLabel(BF.getIcon("logo/user.png",50,50));
		label_4.setBounds(6, 6, 54, 51);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_5.setText(User.uname+" 님");
		label_5.setBounds(84, 14, 123, 36);
		panel.add(label_5);
		
		label_6 = new JLabel("");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_6.setBounds(373, 15, 467, 36);
		panel.add(label_6);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel_1.setBounds(12, 92, 851, 377);
		add(panel_1);
		panel_1.setLayout(null);
		
		label = new JLabel("이름");
		label.setBounds(22, 56, 57, 31);
		panel_1.add(label);
		
		label_1 = new JLabel("ID");
		label_1.setBounds(22, 112, 57, 31);
		panel_1.add(label_1);
		
		label_2 = new JLabel("PW");
		label_2.setBounds(22, 170, 57, 31);
		panel_1.add(label_2);
		
		label_3 = new JLabel("거주지");
		label_3.setBounds(22, 231, 57, 31);
		panel_1.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(102, 56, 620, 31);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		textField_1.setColumns(10);
		textField_1.setBounds(102, 112, 620, 31);
		panel_1.add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(102, 170, 620, 31);
		panel_1.add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setEditable(false);
		textField_3.setColumns(10);
		textField_3.setBounds(102, 231, 620, 31);
		panel_1.add(textField_3);
		
		button = new JButton("거주지 변경");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(742, 235, 97, 23);
		panel_1.add(button);
		
		button_1 = new JButton("수정하기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBounds(22, 284, 817, 23);
		panel_1.add(button_1);
		text = "설정";
		
		load();
	}
	
	int sno = -1;
	private void load() {
		try (var rs = DB.res("select * from user join sub_area using(sno) join area using(ano) where uno = ?", User.uno)) {
			rs.next();
			label_6.setText(String.format("잔액 : %,d원", rs.getInt("point")));
			textField.setText(rs.getString("uname"));
			textField_1.setText(rs.getString("id"));
			textField_2.setText(rs.getString("pw"));
			textField_3.setText(rs.getString("aname")+"-"+rs.getString("sname"));
			sno = rs.getInt("sno");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var f = new D_거주지선택();
			f.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if(f.sub != null) {
						sno = f.sub.sno;
					}
				}
			});
			f.setVisible(true);
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(textField.getText().isBlank()||textField_2.getText().isBlank()) {
				msgInfo("빈칸이 있습니다.");
				return;
			}
			try {
				DB.update("user", "uname = ?, sno = ?, pw = ?", "uno = ?", textField.getText(), sno, textField_2.getText(), User.uno);
				User.uname = textField.getText();
				msgInfo("수정이 완료되었습니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
