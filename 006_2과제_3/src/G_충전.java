import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class G_충전 extends BF {
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JPanel panel_1;
	public JLabel label_2;
	public JTextField textField;
	public JLabel label_3;
	public JTextField textField_1;
	public JLabel label_4;
	public JTextField textField_2;
	public JTextField textField_3;
	public JLabel label_5;
	public JButton button;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					G_충전 frame = new G_충전();
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
	public G_충전() {
		getContentPane().setBackground(new Color(210, 230, 244));
		getContentPane().setLayout(null);
		
		panel = new RoundPanel(1f);
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(23, 29, 379, 378);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("잔액 충전");
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setBounds(12, 10, 76, 22);
		panel.add(label);
		
		label_1 = new JLabel("충전할 금액과 카드 정보를 입력해주세요");
		label_1.setFont(new Font("굴림", Font.BOLD, 12));
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setBounds(12, 42, 334, 22);
		panel.add(label_1);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(232, 232, 232));
		panel_1.setBounds(11, 64, 345, 1);
		panel.add(panel_1);
		
		label_2 = new JLabel("충전 금액");
		label_2.setFont(new Font("굴림", Font.BOLD, 12));
		label_2.setBounds(21, 76, 57, 15);
		panel.add(label_2);
		
		textField = new NumberText("충전할 금액을 입력하세요(원)");
		textField.setBorder(new RoundBorder(Color.LIGHT_GRAY));
		textField.setBounds(21, 101, 323, 42);
		panel.add(textField);
		textField.setColumns(10);
		
		label_3 = new JLabel("카드 번호");
		label_3.setFont(new Font("굴림", Font.BOLD, 12));
		label_3.setBounds(21, 153, 57, 15);
		panel.add(label_3);
		
		textField_1 = new CardText("1234 5678 9012 3456");
		textField_1.setBorder(new RoundBorder(Color.LIGHT_GRAY));
		textField_1.setColumns(10);
		textField_1.setBounds(19, 178, 323, 43);
		panel.add(textField_1);
		
		label_4 = new JLabel("유효기간 (MM/YY)");
		label_4.setFont(new Font("굴림", Font.BOLD, 12));
		label_4.setBounds(19, 231, 136, 15);
		panel.add(label_4);
		
		textField_2 = new PlaceHolder("MM/YY");
		textField_2.setBorder(new RoundBorder(Color.LIGHT_GRAY));
		textField_2.setBounds(18, 256, 137, 36);
		panel.add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new PlaceHolder2("●●●");
		textField_3.setBorder(new RoundBorder(Color.LIGHT_GRAY));
		textField_3.setColumns(10);
		textField_3.setBounds(191, 256, 145, 36);
		panel.add(textField_3);
		
		label_5 = new JLabel("CVC");
		label_5.setFont(new Font("굴림", Font.BOLD, 12));
		label_5.setBounds(201, 231, 57, 15);
		panel.add(label_5);
		
		button = new RoundButton("충전하기 →");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(34, 44, 215));
		button.setForeground(Color.WHITE);
		button.setBounds(17, 318, 329, 43);
		panel.add(button);
		setTitle("충전");
		setBounds(100, 100, 450, 461);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var price = textField.getText();
			var card = textField_1.getText();
			var exp = textField_2.getText();
			var cvc = textField_3.getText();
			if(price.isBlank()||card.isBlank()||exp.isBlank()||cvc.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try {
				var u = DB.getUser(User.uno);
				if(!u.card.equals(card)) {
					msgErr("카드번호를 확인하세요.");
					return;
				}
				if(!card.replaceAll("\\D", "").substring(5,5+4).equals(exp)) {
					msgErr("유효기간을 확인하세요.");
					return;
				}
				if(!card.replaceAll("\\D", "").substring(10,10+3).equals(cvc)) {
					msgErr("cvc를 확인하세요.");
					return;
				}
				int p = Integer.parseInt(price.replaceAll("\\D", ""));
				System.out.println(p);
				DB.update("user", "price = price + ?","uno = ?", p, User.uno);
				msgInfo("충전이 완료되었습니다.");
				dispose();
				previous();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
class NumberText extends PlaceHolder {
	public NumberText(String txt) {
		super(txt);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = getText().replaceAll("\\D", "");
		if(txt.length()>9)
			txt =txt.substring(0,9);
		if(txt.isBlank()) {
			setText("");
			return;
		}
		int price  = Integer.parseInt(txt);
		setText(String.format("%,d", price));
	}
}
class CardText extends PlaceHolder {
	public CardText(String txt) {
		super(txt);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = getText().replaceAll("\\D", "");
		if(txt.length()>16)
			txt =txt.substring(0,16);
		if(txt.isBlank()) {
			setText("");
			return;
		}
		txt = txt.replaceAll("(\\d{4})(?=\\d)", "$1-");
		setText(txt);
	}
}
