import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.BorderLayout;
import javax.swing.JTextArea;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.time.LocalDateTime;

public class E_스토리추가 extends BF {
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JTextArea textArea;
	public JButton button;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					E_스토리추가 frame = new E_스토리추가();
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
	public E_스토리추가() {
		getContentPane().setBackground(Color.BLACK);
		setTitle("스토리 추가");
		setBounds(100, 100, 401, 604);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("새 스토리");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setForeground(Color.WHITE);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(45, 10, 298, 29);
		getContentPane().add(label);
		
		panel = new MyPanel(0.2f, 20) {
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				if(file!=null) {
					try {
						var img = ImageIO.read(file);
						Graphics2D g2 = (Graphics2D) g.create();
						g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
						g2.drawImage(img, 0, 0, getWidth(), getHeight(), 0, 0, img.getWidth(), img.getHeight(), null);
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		};
		panel.addMouseListener(new PanelMouseListener());
		panel.setOpaque(false);
		panel.setBackground(Color.WHITE);
		panel.setBounds(27, 47, 329, 374);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("+");
		label_1.setForeground(Color.WHITE);
		label_1.setFont(new Font("굴림", Font.BOLD, 46));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(133, 108, 67, 47);
		panel.add(label_1);
		
		label_2 = new JLabel("사진 추가");
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("굴림", Font.PLAIN, 14));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(86, 178, 167, 21);
		panel.add(label_2);
		
		label_3 = new JLabel("클릭해서 컴퓨터에서 선택");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.GRAY);
		label_3.setFont(new Font("굴림", Font.PLAIN, 12));
		label_3.setBounds(86, 209, 167, 21);
		panel.add(label_3);
		
		panel_1 =  new MyPanel(0.2f, 0);
		panel_1.setBounds(27, 431, 329, 72);
		getContentPane().add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		textArea = new JTextArea();
		textArea.setOpaque(false);
		textArea.setForeground(Color.white);
		panel_1.add(textArea, BorderLayout.CENTER);
		
		button = new JButton("스토리 공유");
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 15));
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(118, 179, 239));
		button.setBounds(27, 513, 329, 42);
		getContentPane().add(button);
		
		button.addActionListener(e->{
			if(file==null) {
				msgErr("사진을 선택해주세요.");
				return;
			}
			try {
				int sn = getStoryNum();
				DB.insert("story", 0, User.uno, sn,textArea.getText(),LocalDateTime.now(), 0);
				Files.copy(Paths.get(file.getAbsolutePath()), Paths.get("./datafiles/story/"+sn+".jpg"));
				msgInfo("스토리가 공유되었습니다.");
				dispose();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});
	}
	
	private int getStoryNum() throws SQLException {
		var rs = DB.res("select max(s_no) from story");
		rs.next();
		return rs.getInt(1)+1;
	}

	File file;
	private class PanelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			JFileChooser jfc= new JFileChooser();
			jfc.setMultiSelectionEnabled(false);
			jfc.setAcceptAllFileFilterUsed(false);
			jfc.setFileFilter(new FileNameExtensionFilter("jpg Image", "jpg"));
			if(jfc.showOpenDialog(null)==JFileChooser.APPROVE_OPTION) {
				file = jfc.getSelectedFile();
				repaint();
			}
		}
	}
}
class MyPanel extends JPanel{
	float alpha;
	int rad;
	public MyPanel(float alpha, int rad) {
		super();
		this.alpha = alpha;
		this.rad = rad;
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.setColor(Color.white);
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, rad, rad);
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha+0.05f));
		g2.drawRoundRect(0, 0, getWidth()-1, getHeight()-1, rad, rad);
	}
}
