import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;

public class E_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	public E_패널(ImageIcon img, String txt, int cnt, int price) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setSize(510, 80);
		setLayout(null);
		
		label = new JLabel(img);
		label.setBounds(0, 0, 93, 80);
		add(label);
		
		label_1 = new JLabel(txt);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_1.setBounds(118, 12, 199, 23);
		add(label_1);
		
		label_2 = new JLabel(String.format("수량 %d개", cnt));
		label_2.setBounds(117, 36, 199, 23);
		add(label_2);
		
		label_3 = new JLabel(String.format("%,d원", price*cnt));
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_3.setBounds(117, 55, 199, 23);
		add(label_3);
	}

}
