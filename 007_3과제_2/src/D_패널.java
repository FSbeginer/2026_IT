import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

public class D_패널 extends JPanel {
	public JCheckBox checkBox;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public Counter counter;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	
	PayInfo info;
	public D_패널(PayInfo payinfo) {
		info = payinfo;
		setBorder(new LineBorder(new Color(230,230,230)));
		setSize(473, 163);
		setLayout(null);
		
		checkBox = new JCheckBox("");
		checkBox.setBackground(Color.WHITE);
		checkBox.setBounds(6, 51, 23, 35);
		add(checkBox);
		
		label = new JLabel(BF.getIcon("product/"+info.pno+".png",120,120));
		label.setBounds(45, 12, 122, 139);
		add(label);
		
		label_1 = new JLabel(info.name);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_1.setBounds(177, 25, 215, 25);
		add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_2.setBounds(177, 81, 215, 25);
		add(label_2);
		
		counter = new Counter() {
			@Override
			public void setCnt(int cnt) {
				super.setCnt(cnt);
				info.cnt = cnt;
				D_패널.this.label_2.setText(String.format("%,d원", info.price*cnt));
			}
		};
		counter.label_2.setOpaque(true);
		counter.label.setOpaque(true);
		counter.label_1.setOpaque(true);
		counter.label_2.setBackground(new Color(240, 240, 240));
		counter.label.setBackground(new Color(240, 240, 240));
		counter.label_1.setBackground(new Color(240, 240, 240));
		counter.setBounds(177, 118, 104, 25);
		add(counter);
		
		label_3 = new JLabel("삭제");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(412, 12, 51, 15);
		add(label_3);
		
		counter.setCnt(info.cnt);
	}
}
