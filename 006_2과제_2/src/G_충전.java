import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Graphics;
import java.util.regex.Pattern;

import javax.swing.border.MatteBorder;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.awt.event.ActionEvent;

public class G_충전 extends BF {
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JButton button;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;

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
		getContentPane().setBackground(new Color(235, 245, 255));
		getContentPane().setLayout(null);
		
		panel = new RoundPanel(1f);
		panel.setForeground(Color.WHITE);
		panel.setBorder(new RoundBorder(new Color(240, 240, 240)));
		panel.setBounds(27, 37, 441, 408);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("잔액 충전");
		label.setFont(new Font("Dialog", Font.BOLD, 18));
		label.setBounds(21, 12, 119, 30);
		panel.add(label);
		
		label_1 = new JLabel("충전할 금액과 카드 정브를 입력해주세요");
		label_1.setBackground(Color.GRAY);
		label_1.setForeground(Color.LIGHT_GRAY);
		label_1.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(200, 200, 200)));
		label_1.setBounds(21, 45, 397, 23);
		panel.add(label_1);
		
		label_2 = new JLabel("충전 금액");
		label_2.setFont(new Font("Dialog", Font.BOLD, 12));
		label_2.setBounds(21, 80, 67, 23);
		panel.add(label_2);
		
		textField = new DecimelField("충전할 금액을 입력하세요. (원)");
		textField.setBorder(new RoundBorder(new Color(240, 240, 240).darker(),5));
		textField.setOpaque(false);
		textField.setBounds(21, 115, 397, 40);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new CardField("1234 5678 9012 3456");
		textField_1.setBorder(new RoundBorder(new Color(240, 240, 240).darker(),5));
		textField_1.setOpaque(false);
		textField_1.setColumns(10);
		textField_1.setBounds(21, 198, 397, 40);
		panel.add(textField_1);
		
		textField_2 = new PlaceHolder("MM/YY");
		textField_2.setBorder(new RoundBorder(new Color(240, 240, 240).darker(),5));
		textField_2.setOpaque(false);
		textField_2.setColumns(10);
		textField_2.setBounds(21, 274, 165, 40);
		panel.add(textField_2);
		
		textField_3 = new PlaceHolder("●●●");
		textField_3.setBorder(new RoundBorder(new Color(240, 240, 240).darker(),5));
		textField_3.setOpaque(false);
		textField_3.setColumns(10);
		textField_3.setBounds(253, 274, 165, 40);
		panel.add(textField_3);
		
		button = new RoundButton("충전하기 →");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(56, 88, 214));
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(26, 334, 391, 51);
		panel.add(button);
		
		label_3 = new JLabel("카드 번호");
		label_3.setFont(new Font("Dialog", Font.BOLD, 12));
		label_3.setBounds(22, 164, 67, 23);
		panel.add(label_3);
		
		label_4 = new JLabel("유효기간 (MM/YY)");
		label_4.setFont(new Font("Dialog", Font.BOLD, 12));
		label_4.setBounds(22, 246, 164, 23);
		panel.add(label_4);
		
		label_5 = new JLabel("CVC");
		label_5.setFont(new Font("Dialog", Font.BOLD, 12));
		label_5.setBounds(253, 246, 165, 23);
		panel.add(label_5);
		setTitle("충전");
		setBounds(100, 100, 514, 520);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String pricetxt = textField.getText();
			String card = textField_1.getText();
			String exdate = textField_2.getText();
			String cvc = textField_3.getText();
			if(pricetxt.isBlank()||card.isBlank()||exdate.isBlank()||cvc.isBlank()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try {
				int price = Integer.parseInt(textField.getText().replaceAll("\\D", ""));
				
				var user = DB.getUser(User.uno);
				if(!user.card.equals(card)) {
					msgErr("카드번호를 확인하세요.");
					return;
				}
				card = card.replaceAll("\\D", "");
				if(!exdate.equals(card.substring(5,5+4))) {
					msgErr("유효기간을 확인하세요.");
					return;
				}
				if(!cvc.equals(card.substring(10, 10+3))) {
					msgErr("cvc를 확인하세요.");
					return;
				}
				DB.update("user", "price = price + ?", "uno = ?", price, user.uno);
				msgInfo("충전이 완료되었습니다.");
				dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
		}
	}
}
class DecimelField extends PlaceHolder {
	public DecimelField(String txt) {
		super(txt);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = getText().replaceAll("\\D", "");
		if(txt.length()>9)
			txt = txt.substring(0,9); 
		if(txt.isBlank()) {
			setText("");
			return;
		}
		String format = String.format("%,d", Integer.parseInt(txt));
		setText(format);
	}
}
class CardField extends PlaceHolder {
	public CardField(String txt) {
		super(txt);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = getText().replaceAll("\\D", "");
		if(txt.length()>16)
			txt = txt.substring(0,16);
		if(txt.isBlank()) {
			setText("");
			return;
		}
		setText(txt.replaceAll("(\\d{4})(?=\\d)", "$1-"));
	}
}

