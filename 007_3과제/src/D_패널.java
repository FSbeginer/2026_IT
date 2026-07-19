import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;

public class D_패널 extends JPanel {
	public JCheckBox checkBox;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public Counter panel;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	public D_패널(ImageIcon img, String txt, int price, int cnt) {
		setBorder(new LineBorder(Color.LIGHT_GRAY));
		setPreferredSize(new Dimension( 436, 144));
		setLayout(null);
		
		checkBox = new JCheckBox("");
		checkBox.setSelected(true);
		checkBox.setBackground(Color.WHITE);
		checkBox.setBounds(7, 54, 21, 23);
		add(checkBox);
		
		label = new JLabel(img);
		label.setBounds(39, 12, 113, 120);
		add(label);
		
		label_1 = new JLabel(txt);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_1.setBounds(162, 12, 160, 23);
		add(label_1);
		
		label_2 = new JLabel(String.format("%,d원", price*cnt));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_2.setBounds(162, 62, 160, 23);
		add(label_2);
		
		panel = new Counter();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(162, 92, 95, 23);
		add(panel);
		
		label_3 = new JLabel("삭제");
		label_3.setBounds(386, 12, 40, 23);
		add(label_3);
		
		panel.setCnt(cnt);
	}
	
}
