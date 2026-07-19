package test01;

import java.awt.EventQueue;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Form7 extends JFrame {
	ProductInfo pi;
	
	private JPanel contentPane;
	private JButton btn2;

	public Form7() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
				
		btn2 = new JButton("\uC885\uB8CC");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		panel.add(btn2);
		
		contentPane.add(pi = new ProductInfo(), "Center");
		this.setSize(600, 300);
		this.setLocationRelativeTo(null);
	}

	class ProductInfo extends TransPanel {
		public ProductInfo() {
			left.setVisible(false);
			right.setVisible(false);

			add(item3, "South");
			item3.add(text);
			item3.add(btn4_2);
			item3.add(btn4_3);
			
			item3.setPreferredSize(new Dimension(0, 150));
			text.setPreferredSize(new Dimension(600, 100));
			
			item3.setLayout(new FlowLayout(FlowLayout.CENTER));
		}
	}
}
