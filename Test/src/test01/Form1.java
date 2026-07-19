package test01;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

public class Form1 extends JFrame {

	private JPanel contentPane;
	ProductInfo pi;

	private JButton btn2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Form1 frame = new Form1();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Form1() {
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
				Form1.this.dispose();
				new Form2().setVisible(true);
			}
		});
		panel.add(btn2);
		
		contentPane.add(pi = new ProductInfo(), "Center");
		pi.setPreferredSize(new Dimension(500, 600));
		
		this.setSize(400, 600);
		this.setLocationRelativeTo(null);
	}

	class ProductInfo extends TransPanel {
		public ProductInfo() {
			left.setVisible(false);
			right.setVisible(false);
			text.setPreferredSize(new Dimension(0, 200));
		}
	}
}
