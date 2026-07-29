import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class A_메인 extends BF {
	public JPanel panel;
	public JLabel label;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JTextField textField;
	public JTextField textField_1;
	public JButton button_2;
	private Timer timer;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					A_메인 frame = new A_메인();
					frame.setLocationRelativeTo(null);
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
		addWindowListener(new ThisWindowListener());
		setTitle("메인");
		setBounds(100, 100, 755, 469);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new RoundPanel(0.7f);
		panel.setBorder(new RoundBorder(Color.LIGHT_GRAY));
		panel.setBounds(75, 78, 572, 237);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel();
		try {
			label.setIcon(new ImageIcon(Helper.getTransferImage("logo.png", 160, 38)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		label.setBounds(12, 10, 168, 38);
		panel.add(label);
		
		panel_1 = new RoundPanel(0.7f);
		panel_1.setBorder(new RoundBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(15, 55, 264, 164);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		label_2 = new JLabel("New label");
		label_2.setBounds(12, 10, 187, 23);
		panel_1.add(label_2);
		
		label_3 = new JLabel("출발지");
		label_3.setBounds(22, 44, 57, 23);
		panel_1.add(label_3);
		
		label_4 = new JLabel("도착지");
		label_4.setBounds(22, 83, 57, 23);
		panel_1.add(label_4);
		
		textField = new JTextField();
		textField.setBounds(95, 43, 135, 23);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(95, 80, 135, 23);
		panel_1.add(textField_1);
		
		button_2 = new RoundButton("경로검색");
		button_2.addActionListener(new Button_2ActionListener());
		button_2.setBounds(20, 119, 225, 30);
		button_2.setForeground(new Color(255, 255, 255));
		button_2.setBackground(new Color(34, 44, 215));
		panel_1.add(button_2);
		
		button = new RoundButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("굴림", Font.PLAIN, 10));
		button.setIcon(getIcon("icon/login.png",25,25));
		button.setForeground(new Color(255, 255, 255));
		button.setBackground(new Color(34, 44, 215));
		button.setBounds(305, 55, 122, 157);
		panel.add(button);
		
		button_1 = new RoundButton("마이페이지");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setFont(new Font("굴림", Font.PLAIN, 10));
		button_1.setIcon(getIcon("icon/user.png",25,25));
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setBackground(new Color(34, 44, 215));
		button_1.setBounds(439, 54, 122, 157);
		panel.add(button_1);
		
		label_1 = new JLabel(getIcon("main.png",739,430));
		label_1.setBounds(0, 0, 739, 430);
		getContentPane().add(label_1);
		var t = LocalTime.now();
		label_2.setText(String.format("현재시간 : %s", t.format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
		timer = new Timer(500, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				var t = LocalTime.now();
				label_2.setText(String.format("현재시간 : %s", t.format(DateTimeFormatter.ofPattern("hh:mm:ss"))));
			}
		});
		timer.start();
		updateFrom();
	}
	@Override
	public void updateFrom() {
		if(User.uno==-1) {
			button.setText("로그인");
		}
		else {
			button.setText("로그아웃");
		}
	}
	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			timer.stop();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno==-1) {
				showpage(new B_로그인());
			}
			else {
				User.uno =-1;
				msgInfo("로그아웃되어있습니다.");
				textField.setText("");
				textField_1.setText("");
				updateFrom();
			}
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno!=-1) {
				showpage(new F_마이페이지());
			}
		}
	}
	private class Button_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var start = textField.getText();
			var end = textField_1.getText();
			
			if(User.uno==-1) {
				msgErr("로그인이 되어있지 않습니다.");
				showpage(new B_로그인(()->{
					if(start.isBlank()&&end.isBlank()) {
						showpage(new C_경로검색());
						return;
					}
					try {
						if(DB.select("select count(*) from station where name in(?,?)", Integer.class, start,end)<2) {
							msgErr("역명을 확인해주세요.");
							return;
						}
						int startno = DB.select("select sno from station where name = ?", Integer.class, start);
						int endno = DB.select("select sno from station where name = ?", Integer.class, end);
						showpage(new C_경로검색(RouteService.stations.get(startno-1), RouteService.stations.get(endno-1)));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}));
			}else {
				try {
					if(DB.select("select count(*) from station where name in(?,?)", Integer.class, start,end)<2) {
						msgErr("역명을 확인해주세요.");
						return;
					}
					int startno = DB.select("select sno from station where name = ?", Integer.class, start);
					int endno = DB.select("select sno from station where name = ?", Integer.class, end);
					showpage(new C_경로검색(RouteService.stations.get(startno-1), RouteService.stations.get(endno-1)));
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
class RoundBorder extends LineBorder{
	public RoundBorder(Color c) {
		super(c);
	}
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(lineColor);
		g2.drawRoundRect(0, 0, width-1, height-1, 20, 20);
		g2.dispose();
	}
}
class RoundButton extends JButton {
	public RoundButton(String txt) {
		super(txt);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setOpaque(false);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
		g2.dispose();
		super.paintComponent(g);
	}
}
class RoundPanel extends JPanel{
	float alpha;
	public RoundPanel(float alpha) {
		setOpaque(false);
		this.alpha = alpha;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 20, 20);
		g2.dispose();
	}
}
