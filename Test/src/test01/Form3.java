package test01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Form3 extends JFrame {
	ProductInfo pi;
	
	private JPanel contentPane;
	private JButton btn2;

	public Form3() {
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
				Form3.this.dispose();
				new Form4().setVisible(true);
			}
		});
		panel.add(btn2);
		
		contentPane.add(pi = new ProductInfo(), "Center");
		
		this.setSize(800, 400);
		this.setLocationRelativeTo(null);
	}

	class ProductInfo extends TransPanel {
		public ProductInfo() {
			left.setVisible(false);
			right.setVisible(false);

			add(item3, "East");
			item3.add(text);
			item3.add(item1);
			
			text.setPreferredSize(new Dimension(400, 250));
			item3.setPreferredSize(new Dimension(400, 0));
			item1.setPreferredSize(new Dimension(100, 30));
		}
	}
}
