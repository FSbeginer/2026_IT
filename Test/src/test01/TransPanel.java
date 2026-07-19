package test01;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

public class TransPanel extends JPanel {
	ArrayList info = new ArrayList();
	
	JLabel image;
	JLabel text;
	JCheckBox left;
	JLabel right;
	JPanel item1;
	JPanel item2;
	JPanel item3;
	JPanel item4;
	JLabel lbl2_1;
	JLabel lbl2_2;
	JLabel lbl2_3;
	JLabel lbl1_1;
	JLabel lbl1_2;
	JLabel lbl1_3;
	JPanel main;
	JPanel sub;
	JButton btn4_1;
	JLabel lbl4_1;
	JButton btn4_2;
	JButton btn4_3;

	/**
	 * Create the panel.
	 */
	public TransPanel() {
		setLayout(new BorderLayout(0, 0));
		
		sub = new JPanel();
		sub.setVisible(false);
		sub.setPreferredSize(new Dimension(10, 400));
		add(sub, BorderLayout.SOUTH);
		sub.setLayout(new GridLayout(2, 2, 0, 0));
		
		item1 = new JPanel();
		sub.add(item1);
		item1.setLayout(new BorderLayout(0, 0));
		
		lbl1_1 = new JLabel("-");
		lbl1_1.setPreferredSize(new Dimension(30, 15));
		lbl1_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		item1.add(lbl1_1, BorderLayout.WEST);
		
		lbl1_2 = new JLabel("0");
		lbl1_2.setPreferredSize(new Dimension(30, 15));
		lbl1_2.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		item1.add(lbl1_2, BorderLayout.CENTER);
		
		lbl1_3 = new JLabel("+");
		lbl1_3.setPreferredSize(new Dimension(30, 15));
		lbl1_3.setHorizontalAlignment(SwingConstants.CENTER);
		lbl1_3.setBorder(new LineBorder(new Color(0, 0, 0)));
		item1.add(lbl1_3, BorderLayout.EAST);
		
		item2 = new JPanel();
		sub.add(item2);
		item2.setLayout(new GridLayout(2, 0, 0, 0));
		
		lbl2_1 = new JLabel("\uB3D9\uCD95\uCF00\uC774\uBE14");
		lbl2_1.setHorizontalAlignment(SwingConstants.CENTER);
		item2.add(lbl2_1);
		
		JPanel panel = new JPanel();
		item2.add(panel);
		panel.setLayout(new GridLayout(1, 2, 0, 0));
		
		lbl2_2 = new JLabel("10,000\uC6D0");
		lbl2_2.setForeground(Color.RED);
		lbl2_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		lbl2_2.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lbl2_2);
		
		lbl2_3 = new JLabel("\uC7AC\uACE0 10\uAC1C");
		lbl2_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(lbl2_3);
		
		item3 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) item3.getLayout();
		flowLayout.setAlignment(FlowLayout.LEFT);
		sub.add(item3);
		
		item4 = new JPanel();
		FlowLayout flowLayout_1 = (FlowLayout) item4.getLayout();
		flowLayout_1.setAlignment(FlowLayout.RIGHT);
		sub.add(item4);
		
		lbl4_1 = new JLabel("\uBC30\uC1A1\uC900\uBE44\uC911");
		lbl4_1.setForeground(Color.WHITE);
		lbl4_1.setBackground(Color.ORANGE);
		lbl4_1.setOpaque(true);
		lbl4_1.setHorizontalAlignment(SwingConstants.CENTER);
		lbl4_1.setPreferredSize(new Dimension(100, 30));
		item4.add(lbl4_1);
		
		btn4_1 = new JButton("\uC0C1\uC138\uBCF4\uAE30");
		btn4_1.setPreferredSize(new Dimension(120, 30));
		item4.add(btn4_1);
		
		btn4_2 = new JButton("\uC218\uC815");
		btn4_2.setForeground(Color.WHITE);
		btn4_2.setBackground(new Color(30, 144, 255));
		btn4_2.setPreferredSize(new Dimension(80, 30));
		item4.add(btn4_2);
		
		btn4_3 = new JButton("\uC0AD\uC81C");
		btn4_3.setPreferredSize(new Dimension(80, 30));
		btn4_3.setForeground(Color.WHITE);
		btn4_3.setBackground(new Color(255, 99, 71));
		item4.add(btn4_3);
		
		main = new JPanel();
		main.setPreferredSize(new Dimension(10, 200));
		add(main, BorderLayout.CENTER);
		main.setLayout(new BorderLayout(0, 0));
		
		image = new JLabel("\uC774\uBBF8\uC9C0");
		image.setOpaque(true);
		image.setBackground(Color.PINK);
		image.setHorizontalAlignment(SwingConstants.CENTER);
		main.add(image, BorderLayout.CENTER);
		
		text = new JLabel("\uC124\uBA85");
		text.setBackground(Color.CYAN);
		text.setOpaque(true);
		text.setPreferredSize(new Dimension(100, 100));
		text.setHorizontalAlignment(SwingConstants.CENTER);
		main.add(text, BorderLayout.SOUTH);
		
		left = new JCheckBox("");
		main.add(left, BorderLayout.WEST);
		
		right = new JLabel("\uC0AD\uC81C");
		right.setPreferredSize(new Dimension(50, 0));
		right.setHorizontalAlignment(SwingConstants.CENTER);
		main.add(right, BorderLayout.EAST);

	}
}
