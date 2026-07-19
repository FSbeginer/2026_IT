import java.awt.AlphaComposite;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;

import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class A_메인 extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JPanel panel_2;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField textField;
	public JTextField textField_1;
	public JButton button;
	public JButton button_1;
	public JButton button_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_메인 frame = new A_메인();
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
	public A_메인() {
		setTitle("메인");
		setBounds(100, 100, 882, 549);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.drawImage(getIcon("main.png", getWidth(), getHeight()).getImage(), 0, 0, null);
			}
		};
		getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(null);

		panel_1 = new RoundPanel(0.6f);
		panel_1.setBorder(new RoundBorder(new Color(192,192,192).brighter()));
		panel_1.setBounds(69, 92, 732, 312);
		panel.add(panel_1);
		panel_1.setLayout(null);

		label = new JLabel(getTransferImage("logo.png",179, 54));
		label.setBounds(12, 10, 179, 54);
		panel_1.add(label);

		panel_2 = new RoundPanel(0.6f);
		panel_2.setBorder(new RoundBorder(new Color(192,192,192).brighter()));
		panel_2.setBounds(32, 74, 271, 205);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		label_1 = new JLabel("New label");
		label_1.setBounds(12, 10, 180, 24);
		panel_2.add(label_1);
		
		label_2 = new JLabel("출발지");
		label_2.setBounds(23, 58, 64, 24);
		panel_2.add(label_2);
		
		label_3 = new JLabel("도착지");
		label_3.setBounds(23, 97, 64, 24);
		panel_2.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(99, 57, 149, 25);
		panel_2.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(99, 96, 149, 25);
		panel_2.add(textField_1);
		
		button = new RoundButton("경로검색");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(33, 107, 245));
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(12, 147, 247, 43);
		panel_2.add(button);
		
		button_1 = new RoundButton("로그아웃");
		button_1.setText("로그인");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(33, 107, 245));
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setIcon(getIcon("icon/login.png",30,30));
		button_1.setBounds(375, 75, 151, 204);
		panel_1.add(button_1);
		
		button_2 = new RoundButton("마이페이지");
		button_2.addActionListener(new Button_2ActionListener());
		button_2.setIcon(getIcon("icon/user.png",30,30));
		button_2.setBackground(new Color(33, 107, 245));
		button_2.setForeground(new Color(255, 255, 255));
		button_2.setBounds(548, 75, 151, 204);
		panel_1.add(button_2);
		
		label_1.setText(String.format("현재시간: %01d%01d:%01d%01d:%01d%01d", LocalTime.now().getHour()/10,LocalTime.now().getHour()%10,
				LocalTime.now().getMinute()/10,LocalTime.now().getMinute()%10,
				LocalTime.now().getSecond()/10,LocalTime.now().getSecond()%10));
		new Timer(500, e->{
			label_1.setText(String.format("현재시간: %01d%01d:%01d%01d:%01d%01d", LocalTime.now().getHour()/10,LocalTime.now().getHour()%10,
					LocalTime.now().getMinute()/10,LocalTime.now().getMinute()%10,
					LocalTime.now().getSecond()/10,LocalTime.now().getSecond()%10));
		}).start();
		updateForm();
	}
	@Override
	public void updateForm() {
		if(User.uno==-1) {
			button_1.setText("로그인");
		}
		else {
			button_1.setText("로그아웃");
		}
	}
	
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno==-1)
				showPage(new B_로그인());
			else {
				User.uno=-1;
				msgInfo("로그아웃되어있습니다.");
				updateForm();
			}
		}
	}
	private class Button_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno==-1) {
				return;
			}
			showPage(new F_마이페이지());
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno==-1) {
				msgErr("로그인이 되어있지 않습니다.");
				showPage(new B_로그인(()->{
					check();
				}));
			}
			else {
				check();
			}
		}

		private void check() {
			var start = textField.getText();
			var end = textField_1.getText();
			if(start.isBlank()&&end.isBlank()) {
				showPage(new C_경로검색());
				return;
			}
			try (var rs = DB.res("select count(*) from station where name in(?,?)",start,end)) {
				if(rs.next()&&rs.getInt(1)==2){
					showPage(new C_경로검색(new SearchDTO(start, end)));
				}
				else {
					msgErr("역명을 확인해주세요.");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
class RoundButton extends JButton{
	public RoundButton(String txt) {
		super(txt);
		setContentAreaFilled(false);
		setFocusPainted(false);
		setBorderPainted(false);
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
		super.paintComponent(g);
	}
}
class RoundPanel extends JPanel {
	float alpha;

	public RoundPanel(float alpha) {
		super();
		this.alpha = alpha;
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 20, 20);
	}
}

class RoundBorder extends LineBorder {
	public RoundBorder(Color color) {
		super(color);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawRoundRect(0, 0, width-1, height-1, 20, 20);
	}
}