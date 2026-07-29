import javax.swing.JPanel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;

public class C_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	public C_패널(ImageIcon img, String name, int price, double star, int cnt) {
		setBorder(new CompoundBorder(new LineBorder(new Color(192, 192, 192)), new EmptyBorder(5, 5, 5, 5)));
		setSize(160, 200);
		setLayout(new BorderLayout(0, 0));
		
		label = new JLabel(img);
		add(label, BorderLayout.NORTH);
		
		label_1 = new JLabel(String.format("<html><span style = 'font-weight:bold; font-size:12x'>%s<br><br>%,d원<br><br></span><font color = gray>별점 %.1f  구매 %d", name, price, star, cnt));
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 11));
		add(label_1, BorderLayout.CENTER);
	}

}
