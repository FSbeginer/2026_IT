import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.text.Position.Bias;

import com.mysql.cj.protocol.x.XProtocolRow;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.stream.Collectors;
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
	public JPasswordField passwordField;
	public JPasswordField passwordField_1;
	public JTextField textField_1;


	/**
	 * Create the frame.
	 */
	int ono;
	public G_결제(int ono) {
		this.ono = ono;
		getContentPane().setBackground(new Color(234, 234, 242));
		setBounds(100, 100, 540, 495);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("결제");
		label.setOpaque(true);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setForeground(Color.WHITE);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBackground(new Color(0, 0, 189));
		label.setBounds(0, 0, 524, 69);
		getContentPane().add(label);
		
		panel = new RoundPanel();
		panel.setBorder(new RoundBorder(new Color(0, 0, 0)));
		panel.setBounds(25, 89, 472, 203);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("날짜");
		label_1.setBounds(12, 16, 49, 21);
		panel.add(label_1);
		
		label_2 = new JLabel("의사");
		label_2.setBounds(12, 53, 49, 21);
		panel.add(label_2);
		
		label_3 = new JLabel("시간");
		label_3.setBounds(12, 90, 49, 21);
		panel.add(label_3);
		
		label_4 = new JLabel("진료");
		label_4.setBounds(12, 127, 49, 21);
		panel.add(label_4);
		
		label_5 = new JLabel("금액");
		label_5.setBounds(12, 164, 49, 21);
		panel.add(label_5);
		
		label_6 = new JLabel("날짜");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setBounds(205, 16, 255, 21);
		panel.add(label_6);
		
		label_7 = new JLabel("의사");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setBounds(205, 53, 255, 21);
		panel.add(label_7);
		
		label_8 = new JLabel("시간");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setBounds(205, 90, 255, 21);
		panel.add(label_8);
		
		label_9 = new JLabel("진료");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(205, 127, 255, 21);
		panel.add(label_9);
		
		label_10 = new JLabel("???원");
		label_10.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_10.setHorizontalAlignment(SwingConstants.RIGHT);
		label_10.setBounds(205, 164, 255, 21);
		panel.add(label_10);
		
		panel_1 = new RoundPanel();
		panel_1.setBorder(new RoundBorder(new Color(0, 0, 0)));
		panel_1.setBounds(25, 302, 472, 88);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.addKeyListener(new TextFieldKeyListener());
		textField.setBounds(14, 24, 100, 36);
		panel_1.add(textField);
		textField.setColumns(10);
		
		passwordField = new JPasswordField();
		passwordField.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		passwordField.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField.addKeyListener(new PasswordFieldKeyListener());
		passwordField.setBounds(128, 24, 100, 36);
		passwordField.setEchoChar('\u25cf');
		panel_1.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		passwordField_1.setHorizontalAlignment(SwingConstants.CENTER);
		passwordField_1.addKeyListener(new PasswordField_1KeyListener());
		passwordField_1.setBounds(242, 24, 100, 36);
		passwordField_1.setEchoChar('\u25cf');
		panel_1.add(passwordField_1);
		
		textField_1 = new JTextField();
		textField_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		textField_1.setHorizontalAlignment(SwingConstants.CENTER);
		textField_1.addKeyListener(new TextField_1KeyListener());
		textField_1.setColumns(10);
		textField_1.setBounds(356, 24, 100, 36);
		panel_1.add(textField_1);
		
		button = new RoundButton("결제");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(Color.LIGHT_GRAY);
		button.setForeground(Color.GRAY);
		button.setBounds(35, 402, 201, 44);
		getContentPane().add(button);
		
		button_1 = new RoundButton("취소");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(218, 228, 245));
		button_1.setBounds(280, 400, 201, 44);
		getContentPane().add(button_1);
		

	}
	private class PasswordFieldKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var tf =  ((JTextField)e.getSource());
			var txt = tf.getText().replaceAll("\\D", "");
			if(txt.length()>4)
				txt =txt.substring(0,4);
			tf.setText(txt);
			
			if(txt.length()>=4)
				passwordField_1.requestFocus();
			
			buttonEnable();
		}
	}
	private void buttonEnable() {
		if(allInput()) {
			button.setForeground(button_1.getForeground());
			button.setBackground(button_1.getBackground());
		}
		else {
			button.setForeground(Color.gray);
			button.setBackground(Color.lightGray);
		}
	}
	public boolean allInput() {
		var txts = new JTextField[] {textField, passwordField, passwordField_1, textField_1};
		for (JTextField txt : txts) {
			if(txt.getText().length()<4)
				return false;
		}
		return true;
	}
	private class PasswordField_1KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var tf =  ((JTextField)e.getSource());
			var txt = tf.getText().replaceAll("\\D", "");
			if(txt.length()>4)
				txt =txt.substring(0,4);
			tf.setText(txt);
			
			if(txt.length()>=4)
				textField_1.requestFocus();
			
			buttonEnable();
		}
	}
	private class TextFieldKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var tf =  ((JTextField)e.getSource());
			var txt = tf.getText().replaceAll("\\D", "");
			if(txt.length()>4)
				txt =txt.substring(0,4);
			tf.setText(txt);
			
			if(txt.length()>=4)
				passwordField.requestFocus();
			
			buttonEnable();
		}
	}
	private class TextField_1KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			var tf =  ((JTextField)e.getSource());
			var txt = tf.getText().replaceAll("\\D", "");
			if(txt.length()>4)
				txt =txt.substring(0,4);
			tf.setText(txt);
			
			buttonEnable();
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			previous();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(allInput()) {
				try {
					String card = String.join("-",Arrays.stream(new JTextField[] {textField, passwordField, passwordField_1, textField_1}).map(x->x.getText()).collect(Collectors.toList()));
					var ucard = DB.select("select card from user where uno=?", String.class, User.uno);
					if(card.equals(ucard)) {
						DB.update("orders", "paydate=?", "ono = ?", LocalDate.now(),ono);
						msgInfo("결제가 완료되었습니다.");
						dispose();
						while (!prev.isEmpty()) {
							var p = prev.pop();
							if (p instanceof B_메인) {
								p.updateForm();
								p.setVisible(true);
								break;
							} else {
								p.dispose();
							}
						}
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
}
class RoundBorder extends LineBorder {
	public RoundBorder(Color c) {
		super(c);
	}
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(lineColor);
		g2.drawRoundRect(0, 0, width-1, height-1, 15, 15);
		g2.dispose();
	}
}
class RoundButton extends JButton {
	public RoundButton(String txt) {
		super(txt);
		setOpaque(false);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorder(null);
		setForeground(Color.white);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
		g2.dispose();
		super.paintComponent(g);
	}
}

