import javax.swing.JPanel;
import java.awt.BorderLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.CompoundBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.border.EmptyBorder;
import javax.swing.SwingConstants;
import java.awt.GridLayout;

public class A_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	public A_패널(ImageIcon img, String txt) {
		setSize(575/4, 506/3);
		setBackground(new Color(219, 219, 219));
		setBorder(new CompoundBorder(new LineBorder(new Color(0, 0, 0)), new EmptyBorder(5, 5, 5, 5)));
		setLayout(null);
		
		label = new JLabel(img);
		label.setBounds(6, 6, 131, 94);
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		add(label);
		
		label_1 = new JLabel(txt);
		label_1.setBounds(6, 110, 131, 48);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		add(label_1);

	}

}
