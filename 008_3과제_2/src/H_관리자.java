import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class H_관리자 extends BF {
	public JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					H_관리자 frame = new H_관리자();
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
	public H_관리자() {
		setTitle("관리자");
		setBounds(100, 100, 730, 534);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("차트→");
		label.addMouseListener(new LabelMouseListener());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(596, 10, 106, 22);
		getContentPane().add(label);

	}

	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new I_차트());
		}
	}
}
