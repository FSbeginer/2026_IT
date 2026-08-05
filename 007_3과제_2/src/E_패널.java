import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
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
	public E_패널(PayInfo payInfo) {
		setBorder(new LineBorder(new Color(230, 230, 230)));
		setSize(582, 82);
		setLayout(null);
		
		label = new JLabel(BF.getIcon("product/"+payInfo.pno+".png",80,80));
		label.setBounds(10, 0, 81, 82);
		add(label);
		
		label_1 = new JLabel(payInfo.name);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_1.setBounds(98, 7, 293, 27);
		add(label_1);
		
		label_2 = new JLabel("수량 "+payInfo.cnt+"개");
		label_2.setBounds(96, 28, 293, 27);
		add(label_2);
		
		label_3 = new JLabel(String.format("%,d원", payInfo.price * payInfo.cnt));
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_3.setBounds(97, 52, 293, 27);
		add(label_3);
	}

}
