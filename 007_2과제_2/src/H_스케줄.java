import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class H_스케줄 extends BF {
	public JLabel label;
	public JButton button;
	public JButton button_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					H_스케줄 frame = new H_스케줄();
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
	public H_스케줄() {
		setTitle("스케줄");
		setBounds(100, 100, 726, 571);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("New label");
		label.setFont(new Font("굴림", Font.PLAIN, 14));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(204, 10, 301, 35);
		getContentPane().add(label);
		
		button = new JButton("숨기기");
		button.setBackground(Color.BLUE);
		button.setForeground(Color.WHITE);
		button.setBounds(486, 16, 96, 29);
		getContentPane().add(button);
		
		button_1 = new JButton("의사 메인");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(Color.BLUE);
		button_1.setForeground(Color.WHITE);
		button_1.setBounds(594, 16, 104, 29);
		getContentPane().add(button_1);
		
		try {
			var name = DB.select("select dname from doctor where dno = ?", String.class, User.uno);
			label.setText(name+" 선생님 스케줄");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showPage(new I_의사메인());
		}
	}
}
