import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

import javax.swing.ImageIcon;
import javax.swing.JButton;

public class K_관리자패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JButton button;
	public JButton button_1;

	/**
	 * Create the panel.
	 */
	public K_관리자패널(ImageIcon img, String name, int price, int left) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setSize(130, 177);
		setLayout(null);
		
		label = new JLabel(img);
		label.setBounds(0, 0, 130, 88);
		add(label);
		
		label_1 = new JLabel(name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_1.setBounds(10, 92, 110, 15);
		add(label_1);
		
		label_2 = new JLabel(String.format("%,d원", price));
		label_2.setForeground(Color.RED);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		label_2.setBounds(10, 113, 110, 15);
		add(label_2);
		
		label_3 = new JLabel(String.format("재고 %d개", left));
		label_3.setForeground(Color.GRAY);
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		label_3.setBounds(12, 133, 110, 15);
		add(label_3);
		
		button = new JButton("수정");
		button.setBackground(new Color(91, 98, 227));
		button.setForeground(Color.WHITE);
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 8));
		button.setBounds(10, 153, 53, 20);
		add(button);
		
		button_1 = new JButton("삭제");
		button_1.setBackground(Color.RED);
		button_1.setForeground(Color.WHITE);
		button_1.setFont(new Font("맑은 고딕", Font.PLAIN, 8));
		button_1.setBounds(68, 152, 53, 20);
		add(button_1);
	}

}
