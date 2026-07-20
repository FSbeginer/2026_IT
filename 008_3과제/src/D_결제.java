import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.border.LineBorder;

public class D_결제 extends BF {
	private JLabel label;
	private JLabel label_1;
	private JPanel panel;
	private JLabel label_2;
	private JLabel label_3;
	private JLabel label_4;
	private JLabel label_5;
	private JLabel label_6;
	private JLabel label_7;
	private JLabel label_8;
	private JSeparator separator;
	private JLabel label_9;
	private JLabel label_10;
	private JLabel label_11;
	private JSeparator separator_1;
	private JLabel label_12;
	private JLabel label_13;
	private JLabel label_14;
	private JLabel label_15;

	public D_결제(PayInfo payInfo) {
		getContentPane().setBackground(new Color(236, 244, 234));
		setTitle("결제");
		setBounds(100, 100, 415, 716);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("결제 상세 내역");
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setForeground(new Color(0, 128, 0));
		label.setBounds(12, 10, 226, 35);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(12, 51, 179, 21);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 82, 377, 403);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_2 = new JLabel("▶ 기기 정보");
		label_2.setFont(new Font("굴림", Font.PLAIN, 14));
		label_2.setForeground(new Color(0, 128, 0));
		label_2.setBounds(12, 10, 195, 22);
		panel.add(label_2);
		
		label_3 = new JLabel("기종");
		label_3.setBounds(12, 42, 84, 15);
		panel.add(label_3);
		
		label_4 = new JLabel("선택 용량");
		label_4.setBounds(12, 67, 84, 15);
		panel.add(label_4);
		
		label_5 = new JLabel("통신사");
		label_5.setBounds(12, 92, 84, 15);
		panel.add(label_5);
		
		label_6 = new JLabel("할부 기간");
		label_6.setBounds(12, 117, 84, 15);
		panel.add(label_6);
		
		label_7 = new JLabel("개봉일");
		label_7.setBounds(12, 139, 84, 15);
		panel.add(label_7);
		
		label_8 = new JLabel("약정 종료일");
		label_8.setBounds(12, 164, 84, 15);
		panel.add(label_8);
		
		separator = new JSeparator();
		separator.setBounds(12, 189, 353, 2);
		panel.add(separator);
		
		label_9 = new JLabel("▶ 기기 정보");
		label_9.setForeground(new Color(0, 128, 0));
		label_9.setFont(new Font("굴림", Font.PLAIN, 14));
		label_9.setBounds(12, 201, 195, 22);
		panel.add(label_9);
		
		label_10 = new JLabel("선택 요금제");
		label_10.setBounds(12, 233, 84, 15);
		panel.add(label_10);
		
		label_11 = new JLabel("요금제 금액");
		label_11.setBounds(12, 258, 84, 15);
		panel.add(label_11);
		
		separator_1 = new JSeparator();
		separator_1.setBounds(12, 283, 353, 2);
		panel.add(separator_1);
		
		label_12 = new JLabel("▶ 기기 정보");
		label_12.setForeground(new Color(0, 128, 0));
		label_12.setFont(new Font("굴림", Font.PLAIN, 14));
		label_12.setBounds(12, 295, 195, 22);
		panel.add(label_12);
		
		label_13 = new JLabel("용량 가격");
		label_13.setBounds(12, 327, 84, 15);
		panel.add(label_13);
		
		label_14 = new JLabel("통신사 가격");
		label_14.setBounds(12, 352, 84, 15);
		panel.add(label_14);
		
		label_15 = new JLabel("단말기 총 출고가");
		label_15.setFont(new Font("굴림", Font.BOLD, 12));
		label_15.setForeground(new Color(0, 128, 0));
		label_15.setBounds(12, 377, 208, 15);
		panel.add(label_15);

	}

}
