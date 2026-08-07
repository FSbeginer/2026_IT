import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;

public class C_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	public C_패널(String name, int price, double star, int cnt) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setSize((933-20-40)/4, 393*3/4);
		setLayout(null);
		
		label = new JLabel("");
		label.setBounds(12, 2, 194, 175);
		add(label);
		
		label_1 = new JLabel(name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_1.setBounds(12, 185, 194, 21);
		add(label_1);
		
		label_2 = new JLabel(String.format("%,d원", price));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setBounds(12, 216, 194, 21);
		add(label_2);
		
		label_3 = new JLabel(String.format("별점 %.1f 구매 %d", star, cnt));
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(12, 247, 194, 21);
		add(label_3);
	}

}
