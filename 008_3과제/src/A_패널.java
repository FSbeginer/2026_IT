import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.CompoundBorder;
import java.awt.Dimension;

public class A_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	public A_패널(ImageIcon img, String name, int price) {
		setBackground(new Color(240, 240, 240));
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		setLayout(new BorderLayout(0, 0));
		
		label = new JLabel(img);
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		add(label, BorderLayout.CENTER);
		
		label_1 = new JLabel(String.format("<html>기종:%s<br>평균 가격:%,d원", name, price));
		label_1.setPreferredSize(new Dimension(57, 60));
		add(label_1, BorderLayout.SOUTH);

	}

}
