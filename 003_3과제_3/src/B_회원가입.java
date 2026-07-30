import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

public class B_회원가입 extends BF {
	public JLabel label;
	public JScrollPane scrollPane;
	public JButton button;
	public JPanel panel;
	public JLabel label_1;
	public JButton button_1;
	public JPanel panel_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_4;
	public JTextField textField_5;
	public JTextField textField_6;
	public JTextField textField_7;
	public JLabel label_10;
	public JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B_회원가입 frame = new B_회원가입();
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
	public B_회원가입() {
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);

		label = new JLabel("회원가입");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 26));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 399, 44);
		getContentPane().add(label);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 57, 399, 516);
		getContentPane().add(scrollPane);

		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 550));
		panel.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		label_1 = new JLabel("프로필 사진");
		label_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_1.setFont(new Font("굴림", Font.BOLD, 12));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(144, 10, 109, 107);
		panel.add(label_1);

		button_1 = new JButton("사진 선택");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(0, 128, 255));
		button_1.setForeground(Color.WHITE);
		button_1.setBounds(150, 130, 97, 23);
		panel.add(button_1);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		panel_1.setBounds(0, 155, 372, 259);
		panel.add(panel_1);
		panel_1.setLayout(null);

		label_2 = new JLabel("이름");
		label_2.setBounds(0, 1, 140, 31);
		panel_1.add(label_2);

		label_3 = new JLabel("아이디");
		label_3.setBounds(0, 33, 140, 31);
		panel_1.add(label_3);

		label_4 = new JLabel("비밀번호");
		label_4.setBounds(0, 65, 140, 31);
		panel_1.add(label_4);

		label_5 = new JLabel("비밀번호 확인");
		label_5.setBounds(0, 97, 140, 31);
		panel_1.add(label_5);

		label_6 = new JLabel("닉네임");
		label_6.setBounds(0, 129, 140, 31);
		panel_1.add(label_6);

		label_7 = new JLabel("생년월일 예) 2008-05-31");
		label_7.setBounds(0, 161, 140, 31);
		panel_1.add(label_7);

		label_8 = new JLabel("전화번호");
		label_8.setBounds(0, 193, 140, 31);
		panel_1.add(label_8);

		label_9 = new JLabel("이메일");
		label_9.setBounds(0, 225, 140, 31);
		panel_1.add(label_9);

		textField = new JTextField();
		textField.setBounds(150, 5, 222, 26);
		panel_1.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(150, 36, 222, 26);
		panel_1.add(textField_1);

		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(150, 129, 222, 26);
		panel_1.add(textField_4);

		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(150, 160, 222, 26);
		panel_1.add(textField_5);

		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(150, 191, 222, 26);
		panel_1.add(textField_6);

		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(150, 222, 222, 26);
		panel_1.add(textField_7);

		passwordField = new JPasswordField();
		passwordField.setBounds(150, 67, 222, 26);
		panel_1.add(passwordField);

		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(150, 98, 222, 26);
		panel_1.add(passwordField_1);

		label_10 = new JLabel("소개글");
		label_10.setFont(new Font("굴림", Font.BOLD, 12));
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setBounds(127, 424, 135, 15);
		panel.add(label_10);

		textArea = new JTextArea();
		textArea.setPreferredSize(new Dimension(5, 100));
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(0, 439, 372, 101);
		panel.add(textArea);

		button = new JButton("회원가입");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 255));
		button.setForeground(Color.WHITE);
		button.setBounds(12, 583, 399, 31);
		getContentPane().add(button);
		setTitle("회원가입");
		setBounds(100, 100, 439, 660);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	File file;
	public JPasswordField passwordField;
	public JPasswordField passwordField_1;

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var jfc = new JFileChooser();
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(new FileNameExtensionFilter("jpg image", "jpg"));
			jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(null) == jfc.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				try {
					label_1.setIcon(new ImageIcon(
							ImageIO.read(file).getScaledInstance(label_1.getWidth(), label_1.getHeight(), 1)));
					label_1.setText("");
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var name = textField.getText();
			var id = textField_1.getText();
			var pw = new String(passwordField.getPassword());
			var pwchk = new String(passwordField.getPassword());
			var nick = textField_4.getText();
			var birth = textField_5.getText();
			var phone = textField_6.getText();
			var email = textField_7.getText();
			var txt = textArea.getText();
			if (Arrays.stream(new String[] { name, id, pw, pwchk, nick, birth, phone, email,txt }).filter(x -> x.isEmpty())
					.findFirst().isPresent()) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			try {
				LocalDate.parse(birth);
			} catch (Exception e1) {
				msgErr("생년월일 형식이 올바르지 않습니다.");
				return;
			}
			if (!phone.matches("\\d{3}-\\d{4}-\\d{4}")) {
				msgErr("전화번호 형식이 올바르지 않습니다.");
				return;
			}
			if(!pw.equals(pwchk)) {
				msgErr("비밀번호를 확인해주세요.");
				return;
			}
			if(!email.matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+")) {
				msgErr("비밀번호를 확인해주세요.");
				return;
			}
			if (file == null) {
				msgErr("프로필사진을 선택해주세요.");
				return;
			}
			try {
				DB.insert("user", 0,name,id,pw,nick,birth,phone,email,txt, "");
				int uno = DB.select("select max(u_no) from user", Integer.class,null);
				try {
					Files.copy(file.toPath(), Path.of("./datafiles/profile/"+uno+".jpg"));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				msgInfo("회원가입이 완료되었습니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			
			dispose();
			previous();
		}
	}
}
