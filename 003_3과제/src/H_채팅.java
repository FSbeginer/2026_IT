import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JScrollPane;

public class H_채팅 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					H_채팅 frame = new H_채팅();
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
	int uno, pno;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label;
	public JPanel panel_3;
	public JLabel label_1;
	public JScrollPane scrollPane;
	public JPanel panel_4;
	public H_채팅(int uno, int pno) {
		this();
		this.uno = uno;
		this.pno = pno;
	}
	public H_채팅() {
		getContentPane().setBackground(new Color(240, 240, 240));
		setTitle("메시지");
		setBounds(100, 100, 986, 554);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(0, 0, 328, 515);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(12, 10, 47, 37);
		panel.add(panel_2);
		
		label = new JLabel(User.uname);
		label.setBounds(67, 10, 209, 37);
		panel.add(label);
		
		panel_3 = new JPanel();
		panel_3.setBackground(new Color(240, 240, 240));
		panel_3.setBounds(12, 57, 303, 19);
		panel.add(panel_3);
		
		label_1 = new JLabel("메시지");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(12, 86, 92, 27);
		panel.add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 124, 306, 381);
		panel.add(scrollPane);
		
		panel_4 = new JPanel();
		scrollPane.setViewportView(panel_4);
		panel_4.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(331, 0, 638, 515);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		
		load();
	}
	private void load() {
	}

}
