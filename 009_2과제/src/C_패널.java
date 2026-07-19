import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.border.LineBorder;

public class C_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	public C_패널(ImageIcon img, String txt, int price, double st, int order) {
		setBorder(new LineBorder(new Color(192, 192, 192)));
		setSize((811-20)/4, 368-50);
		setLayout(null);
		
		label = new JLabel(img);
		label.setBounds(6, 5, 184, 193);
		add(label);
		
		label_1 = new JLabel(txt);
		label_1.setBounds(16, 212, 169, 15);
		add(label_1);
		
		label_2 = new JLabel(String.format("%,d원", price));
		label_2.setBounds(17, 248, 169, 15);
		add(label_2);
		
		label_3 = new JLabel(String.format("별점 %.1f 구매 %d",st, order));
		label_3.setForeground(new Color(192, 192, 192));
		label_3.setBounds(16, 289, 169, 15);
		add(label_3);
	}

}
