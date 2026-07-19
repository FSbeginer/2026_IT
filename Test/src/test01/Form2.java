package test01;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;


import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Form2 extends JFrame {
	ProductInfo pi;
	
	private JPanel contentPane;
	private JButton btn2;

	public Form2() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 795, 649);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.NORTH);
		
		btn2 = new JButton("\uB2E4\uC74C \uD398\uC774\uC9C0");
		btn2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Form2.this.dispose();
				new Form3().setVisible(true);
			}
		});
		panel.add(btn2);
		
		contentPane.add(pi = new ProductInfo(), "Center");
		
		this.setSize(700, 300);
		this.setLocationRelativeTo(null);
	}

	class ProductInfo extends TransPanel {
		public ProductInfo() {
			left.setVisible(false);
			right.setVisible(false);
			add(this.text, "East");
			text.setPreferredSize(new Dimension(400, 0));
		}
	}
}
