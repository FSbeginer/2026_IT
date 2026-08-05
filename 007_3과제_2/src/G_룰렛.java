
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class G_룰렛 extends BF {
	public JPanel panel;
	public JButton button;
	public JLabel label;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					G_룰렛 frame = new G_룰렛();
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
	public G_룰렛() {
		setTitle("룰렛");
		setBounds(100, 100, 748, 696);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(107, 12, 509, 432);
		getContentPane().add(panel);
		
		button = new RoundButton("룰렛 돌리기");
		button.setBackground(Color.ORANGE);
		button.setForeground(Color.BLACK);
		button.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		button.setBounds(204, 463, 297, 54);
		getContentPane().add(button);
		
		label = new JLabel("START");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(282, 529, 142, 45);
		getContentPane().add(label);
		
		load();
	}

	Color[] c = {new Color(255,99,132),new Color(54,162,235),new Color(255,206,86),new Color(75,192,192),new Color(153,102,255)};
	
	private void load() {
		
	}
}
