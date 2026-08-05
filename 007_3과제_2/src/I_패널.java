import javax.swing.JPanel;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;
import java.awt.Font;

public class I_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;

	/**
	 * Create the panel.
	 */
	public I_패널(ImageIcon img, String txt, String txt2, String txt3, double star) {
		setBorder(new LineBorder(new Color(230, 230, 230)));
		setBackground(new Color(236, 238, 244));
		setSize( 360, 147);
		setLayout(null);
		
		label = new JLabel(img);
		label.setBounds(10, 12, 98, 75);
		add(label);
		if(txt.length()>=10)
			txt = txt.substring(0,10)+"...";
		label_1 = new JLabel(txt);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_1.setBounds(118, 12, 167, 22);
		add(label_1);
		
		label_2 = new JLabel(String.format("★ %.1f", star));
		label_2.setForeground(Color.ORANGE);
		label_2.setBounds(299, 12, 51, 22);
		add(label_2);
		if(txt2.length()>=35)
			txt2 = txt2.substring(0,35)+"...";
		label_3 = new JLabel("<html>"+txt2);
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(118, 57, 232, 30);
		add(label_3);
		
		label_4 = new JLabel(txt3);
		label_4.setForeground(Color.GRAY);
		label_4.setBounds(118, 108, 76, 27);
		add(label_4);
	}

}
