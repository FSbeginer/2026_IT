import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.stream.IntStream;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Passworad extends JDialog {

	private JPanel contentPane;

	/**
	 * Create the frame.
	 */
	String password;
	boolean success;
	public JPanel panel;
	public JLabel label;
	public JTextField textField;
	public JButton button;
	Runnable r;
	public Passworad(String password, Runnable r) {
		this.r=r;
		setTitle("비밀번호");
		this.password = password;
		setModal(true);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 419, 460);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(12, 10, 379, 325);
		contentPane.add(panel);
		panel.setLayout(new GridLayout(0, 5, 0, 0));
		
		label = new JLabel("pass:");
		label.setFont(new Font("굴림", Font.BOLD, 13));
		label.setBounds(22, 345, 57, 15);
		contentPane.add(label);
		
		textField = new JTextField();
		textField.setBounds(91, 342, 234, 32);
		contentPane.add(textField);
		textField.setColumns(10);
		
		button = new JButton("결제");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(39, 379, 286, 32);
		contentPane.add(button);
		
		IntStream.range(65, 65+26).forEach(x->{
			var jl = new JLabel((char)x+"",0);
			jl.setForeground(new Color(0,128,0));
			jl.setFont(new Font("맑은 고딕",1,15));
			jl.addMouseListener(new dksltlqkfwlsWk());
			panel.add(jl);
		});
		
	}
	class dksltlqkfwlsWk extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			var txt = ((JLabel)e.getSource()).getText();
			textField.setText(textField.getText()+txt);
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(textField.getText().equals(password)) {
				r.run();
				dispose();
			}
		}
	}
}
