import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextField;
import java.beans.Beans;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class G_결제 extends BF {
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JLabel label_10;
	public JTextField textField;
	public JPasswordField textField_1;
	public JPasswordField textField_2;
	public JTextField textField_3;

	public static void main(String[] args) {
		new G_결제(1).setVisible(true);
	}

	int ono;

	public G_결제(int ono) {
		this.ono = ono;
		getContentPane().setBackground(new Color(235, 240, 255));
		setTitle("결제하기");
		setBounds(100, 100, 572, 553);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("결제");
		label.setFont(new Font("굴림", Font.PLAIN, 24));
		label.setOpaque(true);
		label.setBackground(new Color(66, 120, 210));
		label.setForeground(new Color(255, 255, 255));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 556, 69);
		getContentPane().add(label);

		panel = new RoundPanel();
		panel.setBorder(new RoundBorder(Color.black));
		panel.setBounds(37, 90, 477, 233);
		getContentPane().add(panel);
		panel.setLayout(null);

		label_1 = new JLabel("날짜");
		label_1.setBounds(12, 21, 46, 21);
		panel.add(label_1);

		label_2 = new JLabel("의사");
		label_2.setBounds(12, 63, 46, 21);
		panel.add(label_2);

		label_3 = new JLabel("시간");
		label_3.setBounds(12, 105, 46, 21);
		panel.add(label_3);

		label_4 = new JLabel("진료");
		label_4.setBounds(12, 147, 46, 21);
		panel.add(label_4);

		label_5 = new JLabel("금액");
		label_5.setBounds(12, 189, 46, 21);
		panel.add(label_5);

		label_6 = new JLabel("날짜");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(283, 21, 182, 21);
		panel.add(label_6);

		label_7 = new JLabel("날짜");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(283, 63, 182, 21);
		panel.add(label_7);

		label_8 = new JLabel("날짜");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setBounds(283, 105, 182, 21);
		panel.add(label_8);

		label_9 = new JLabel("날짜");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(283, 147, 182, 21);
		panel.add(label_9);

		label_10 = new JLabel("날짜");
		label_10.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_10.setForeground(new Color(49, 35, 173));
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setBounds(283, 189, 182, 21);
		panel.add(label_10);

		panel_1 = new RoundPanel();
		panel_1.setBorder(new RoundBorder(Color.black));
		panel_1.setBounds(37, 354, 477, 79);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);

		textField = new FocusTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBounds(37, 24, 73, 30);
		panel_1.add(textField);
		textField.setColumns(10);

		textField_1 = new FocusPasswordField();
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		textField_1.addKeyListener(new TextField_1KeyListener());
		textField_1.setColumns(10);
		textField_1.setBounds(147, 24, 73, 30);
		panel_1.add(textField_1);

		textField_2 = new FocusPasswordField();
		textField_2.setHorizontalAlignment(SwingConstants.CENTER);
		textField_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		textField_2.addKeyListener(new TextField_2KeyListener());
		textField_2.setColumns(10);
		textField_2.setBounds(257, 24, 73, 30);
		panel_1.add(textField_2);

		textField_3 = new FocusTextField();
		textField_3.addKeyListener(new TextField_3KeyListener());
		textField_3.setHorizontalAlignment(SwingConstants.CENTER);
		textField_3.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		textField_3.setColumns(10);
		textField_3.setBounds(367, 24, 73, 30);
		panel_1.add(textField_3);

		button = new RoundButton("결제");
		button.addActionListener(new ButtonActionListener());
		button.setEnabled(false);
		button.setBackground(Color.LIGHT_GRAY);
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(47, 443, 210, 45);
		getContentPane().add(button);

		button_1 = new RoundButton("취소");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(228, 234, 254));
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setBounds(288, 443, 210, 45);
		getContentPane().add(button_1);

		load();
	}

	private void load() {
		try (var rs = DB.res(
				"select orderdate, dname, ordertime, cname from orders join category using(cno) join doctor using(dno) where ono = ?",
				ono)) {
			rs.next();
			label_6.setText(rs.getString(1));
			label_7.setText(rs.getString(2));
			label_8.setText(rs.getString(3));
			label_9.setText(rs.getString(4));
			label_10.setText("???");
			txts.add(textField);
			txts.add(textField_1);
			txts.add(textField_2);
			txts.add(textField_3);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	List<JTextField> txts = new ArrayList<>();
	private class TextField_2KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var src = ((JTextField) e.getSource()).getText();
			if (src.length() >= 4)
				textField_3.requestFocus();
			if(txts.stream().filter(x->x.getText().length()>=4).count()==4) 
				buttonenable(true);
			else 
				buttonenable(false);
		}

	}
	
	private void buttonenable(boolean b) {
		button.setEnabled(b);
		if(b)
			button.setBackground(button_1.getBackground());
		else
			button.setBackground(Color.LIGHT_GRAY);
	}
	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var src = ((JTextField) e.getSource()).getText();
			if (src.length() >= 4)
				textField_1.requestFocus();
			if(txts.stream().filter(x->x.getText().length()>=4).count()==4) 
				buttonenable(true);
			else 
				buttonenable(false);
		}
	}

	private class TextField_1KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var src = ((JTextField) e.getSource()).getText();
			if (src.length() >= 4)
				textField_2.requestFocus();
			if(txts.stream().filter(x->x.getText().length()>=4).count()==4) 
				buttonenable(true);
			else 
				buttonenable(false);
		}
	}
	private class TextField_3KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if(txts.stream().filter(x->x.getText().length()>=4).count()==4) 
				buttonenable(true);
			else 
				buttonenable(false);
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				var card = String.join("-",txts.stream().map(x->x.getText()).collect(Collectors.toList()));
				var ucard = DB.select("select card from user where uno = ?", String.class, User.uno);
				if(card.equals(ucard)) {
					DB.update("orders", "paydate = ?", "ono = ?", LocalDate.now(),ono);
					msgInfo("결제가 완료되었습니다.");
					showPage(B_메인.class);
				}
				else {
					msgErr("카드번호를 확인해주세요.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class FocusTextField extends JTextField {
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = getText().replaceAll("\\D", "");
		if (txt.length() > 4)
			txt = txt.substring(0, 4);
		if (txt.isBlank())
			setText("");
		else {
			setText(txt);
		}
	}
}

class FocusPasswordField extends JPasswordField {
	public FocusPasswordField() {
		setEchoChar('\u25cf');
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = new String(getPassword()).replaceAll("\\D", "");
		if (txt.length() > 4)
			txt = txt.substring(0, 4);
		if (txt.isBlank())
			setText("");
		else {
			setText(txt);
		}
	}
}

class RoundPanel extends JPanel {
	public RoundPanel() {
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 25, 25);
		g2.dispose();
	}
}
