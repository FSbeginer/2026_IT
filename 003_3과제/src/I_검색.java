import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JSeparator;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;

public class I_검색 extends BF {
	public JSeparator separator;
	public JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_검색 frame = new I_검색();
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
	public I_검색() {
		setBounds(100, 100, 450, 585);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		separator = new JSeparator();
		separator.setBounds(0, 77, 434, 20);
		getContentPane().add(separator);
		
		label = new JLabel("<");
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 33, 57);
		getContentPane().add(label);

	}
}
