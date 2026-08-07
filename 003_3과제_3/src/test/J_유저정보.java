package test;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Color;

public class J_유저정보 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					J_유저정보 frame = new J_유저정보(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param uno 
	 */
	int uno;
	public JPanel panel;
	public J_유저정보(int uno) {
		setTitle("유저 정보");
		this.uno = uno;
		setBounds(100, 100, 765, 628);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setBounds(0, 160, 749, 1);
		getContentPane().add(panel);

	}

}
