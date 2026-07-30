import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.RoundRectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.JTextArea;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class E_스토리_추가 extends BF {
	public JLabel label;
	public JButton button;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JTextArea textArea;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					E_스토리_추가 frame = new E_스토리_추가();
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
	public E_스토리_추가() {
		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);
		
		label = new JLabel("새 스토리");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setForeground(Color.WHITE);
		label.setBounds(38, 10, 297, 33);
		getContentPane().add(label);
		
		button = new JButton("스토리 공유");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 255));
		button.setForeground(Color.WHITE);
		button.setBounds(12, 508, 349, 30);
		getContentPane().add(button);
		
		label_1 = new JLabel() {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				((Graphics2D) g).clip(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 15,15));
				super.paintComponent(g);
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
				g2.setColor(Color.white);
				g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
				g2.setColor(Color.lightGray);
				g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
				g2.dispose();
			}
		};
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setBounds(12, 53, 349, 366);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("+");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("굴림", Font.BOLD, 32));
		label_2.setBounds(142, 160, 89, 58);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("사진 추가");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("굴림", Font.BOLD, 15));
		label_3.setBounds(142, 228, 89, 25);
		getContentPane().add(label_3);
		
		label_4 = new JLabel("클릭해서 컴퓨터에서 선택");
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setForeground(Color.LIGHT_GRAY);
		label_4.setFont(new Font("굴림", Font.PLAIN, 11));
		label_4.setBounds(65, 263, 242, 25);
		getContentPane().add(label_4);
		
		textArea = new JTextArea() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.15f));
				g2.setColor(Color.white);
				g2.fillRect(0, 0, getWidth()-1, getHeight()-1);
				g2.setColor(Color.lightGray);
				g2.drawRect(0, 0, getWidth()-1, getHeight()-1);
				g2.dispose();
			}
		};
		textArea.setForeground(Color.WHITE);
		textArea.setOpaque(false);
		textArea.setBounds(12, 429, 349, 68);
		getContentPane().add(textArea);
		setTitle("스토리 추가");
		setBounds(100, 100, 389, 587);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}
	File file;
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(file==null) {
				msgErr("파일을 선택해주세요.");
				return;
			}
			try {
				int sno = DB.select("select max(s_no) from story", Integer.class, null)+1;
				DB.insert("story", 0,User.uno,sno,textArea.getText(),LocalDateTime.now(),0);
				Files.copy(file.toPath(), Path.of("./datafiles/story/"+sno+".jpg"));
				msgInfo("스토리가 공유되었습니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			var jfc = new JFileChooser();
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(new FileNameExtensionFilter("jpg image", "jpg"));
			jfc.setMultiSelectionEnabled(false);
			if (jfc.showOpenDialog(null) == jfc.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				try {
					label_1.setIcon(new ImageIcon(
							ImageIO.read(file).getScaledInstance(label_1.getWidth(), label_1.getHeight(), 4)));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
