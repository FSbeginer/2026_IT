import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

public class G_패널 extends JPanel {
	public Profile panel;
	public JLabel label;
	public JLabel label_1;

	/**
	 * Create the panel.
	 */
	public G_패널(String nick, String comment) {
		this(nick);
		label_1.setText(comment);
	}
	
	/**
	 * @wbp.parser.constructor
	 */
	public G_패널(String nick) {
		setSize(286, 396/6);
		setLayout(null);
		
		panel = new Profile(false);
		panel.setBounds(2, 5, 61, 56);
		add(panel);
		
		label = new JLabel(nick);
		label.setFont(new Font("굴림", Font.BOLD, 13));
		label.setBounds(71, 11, 502, 22);
		add(label);
		
		label_1 = new JLabel("게시물 보내기");
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(73, 34, 500, 22);
		add(label_1);
		
	}
}
