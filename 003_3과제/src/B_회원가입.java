import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.regex.Pattern;
import java.awt.event.ActionEvent;

public class B_회원가입 extends BF {
	public JLabel label;
	public JScrollPane scrollPane;
	public JButton button;
	public JPanel panel;
	public JLabel label_1;
	public JButton button_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JTextArea textArea;
	public JLabel label_10;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_4;
	public JTextField textField_5;
	public JTextField textField_6;
	public JTextField textField_7;
	public JPasswordField passwordField;
	public JPasswordField passwordField_1;

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
		setTitle("회원가입");
		setBounds(100, 100, 498, 705);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("회원가입");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 458, 49);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 57, 458, 547);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		panel.setBackground(new Color(240, 240, 240));
		panel.setPreferredSize(new Dimension(438, 607));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("프로필 사진");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		label_1.setBounds(159, 10, 151, 147);
		panel.add(label_1);
		
		button_1 = new JButton("사진 선택");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setBackground(new Color(118, 179, 239));
		button_1.setBounds(184, 167, 97, 23);
		panel.add(button_1);
		
		label_2 = new JLabel("이름");
		label_2.setBounds(0, 198, 81, 30);
		panel.add(label_2);
		
		label_3 = new JLabel("아이디");
		label_3.setBounds(0, 231, 81, 30);
		panel.add(label_3);
		
		label_4 = new JLabel("비밀번호 확인");
		label_4.setBounds(0, 297, 81, 30);
		panel.add(label_4);
		
		label_5 = new JLabel("비밀번호");
		label_5.setBounds(0, 264, 81, 30);
		panel.add(label_5);
		
		label_6 = new JLabel("생년월일 예) 2008-05-31");
		label_6.setBounds(0, 364, 151, 30);
		panel.add(label_6);
		
		label_7 = new JLabel("닉네임");
		label_7.setBounds(0, 331, 81, 30);
		panel.add(label_7);
		
		label_8 = new JLabel("이메일");
		label_8.setBounds(0, 429, 81, 30);
		panel.add(label_8);
		
		label_9 = new JLabel("전화번호");
		label_9.setBounds(0, 396, 81, 30);
		panel.add(label_9);
		
		textArea = new JTextArea();
		textArea.setBounds(0, 480, 439, 117);
		panel.add(textArea);
		
		label_10 = new JLabel("소개글");
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setBounds(190, 465, 57, 15);
		panel.add(label_10);
		
		textField = new JTextField();
		textField.setBounds(151, 199, 276, 30);
		panel.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(151, 232, 276, 30);
		panel.add(textField_1);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(151, 331, 276, 30);
		panel.add(textField_4);
		
		textField_5 = new JTextField();
		textField_5.setColumns(10);
		textField_5.setBounds(151, 364, 276, 30);
		panel.add(textField_5);
		
		textField_6 = new JTextField();
		textField_6.setColumns(10);
		textField_6.setBounds(151, 396, 276, 30);
		panel.add(textField_6);
		
		textField_7 = new JTextField();
		textField_7.setColumns(10);
		textField_7.setBounds(151, 429, 276, 30);
		panel.add(textField_7);
		
		passwordField = new JPasswordField();
		passwordField.setBounds(151, 264, 276, 30);
		panel.add(passwordField);
		
		passwordField_1 = new JPasswordField();
		passwordField_1.setBounds(151, 297, 276, 30);
		panel.add(passwordField_1);
		
		button = new JButton("회원가입");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(118, 179, 239));
		button.setBounds(12, 615, 458, 41);
		getContentPane().add(button);

	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var name = textField.getText();
			var id = textField_1.getText();
			var pw = new String(passwordField.getPassword());
			var pwchk = new String(passwordField_1.getPassword());
			var nick = textField_4.getText();
			var birth = textField_5.getText();
			var phone = textField_6.getText();
			var email = textField_7.getText();
			var explain = textArea.getText();
			if(isEmpty(name,id,pw,pwchk,nick, birth, phone, email, explain)) {
				msgErr("빈칸이 있습니다.");
				return;
			}
			if(!pw.equals(pwchk)) {
				msgErr("비밀번호가 일치하지 않습니다.");
				return;
			}
			try {
				birth = LocalDate.parse(birth).toString();
			} catch (Exception e2) {
				msgErr("생년월일 형식이 올바르지 않습니다.");
				return;
			}
			if(!Pattern.compile("^\\d{3}-\\d{4}-\\d{4}$").matcher(phone).find()) {
				msgErr("전화번호 형식이 올바르지 않습니다.");
				return;
			}
			if(!Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$").matcher(email).find()) {
				msgErr("전화번호 형식이 올바르지 않습니다.");
				return;
			}
			if(file == null) {
				msgErr("프로필사진을 선택해주세요.");
				return;
			}
			try {
				DB.insert("user", 0,name, id, pw, nick, birth, phone, email, explain, "");
				Files.copy(Paths.get(file.getAbsolutePath()), Paths.get("./datafiles/profile/"+getmaxuserno()+".jpg"), StandardCopyOption.REPLACE_EXISTING);
				msgInfo("회원가입이 완료되었습니다.");
				dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	public boolean isEmpty(String...strings) {
		for (String string : strings) {
			if(string.isBlank()) {
				return true;
			}
		}
		return false;
	}
	File file;
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			JFileChooser jfc = new JFileChooser();
			jfc.setMultiSelectionEnabled(false);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(new FileNameExtensionFilter("jpg Image", "jpg"));
			if (jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				try {
					var img = ImageIO.read(file);
				
					label_1.setIcon(new ImageIcon(new ImageIcon(img).getImage().getScaledInstance(label_1.getWidth(), label_1.getHeight(), 4)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}

	}
	private int getmaxuserno() throws SQLException {
		var rs = DB.res("select count(*) from user");
		rs.next();
		return rs.getInt(1);
	}
}
