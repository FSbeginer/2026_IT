import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.Timer;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class A_메인 extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JButton button_1;
	public JButton button_2;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField textField;
	public JTextField textField_1;
	public JLabel label_4;
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
		setBounds(100, 100, 927, 536);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new RoundPanel(0.7f);
		panel.setBorder(new RoundBorder(Color.darkGray));
		panel.setBounds(129, 110, 655, 271);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new RoundPanel(0.7f);
		panel_1.setBorder(new RoundBorder(Color.darkGray));
		panel_1.setBounds(30, 64, 260, 182);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		button_2 = new RoundButton("경로검색");
		button_2.addActionListener(new Button_2ActionListener());
		button_2.setBackground(new Color(71, 94, 218));
		button_2.setBounds(10, 138, 240, 32);
		panel_1.add(button_2);
		
		label_1 = new JLabel("New label");
		label_1.setBounds(10, 12, 186, 22);
		panel_1.add(label_1);
		
		label_2 = new JLabel("출발지");
		label_2.setBounds(22, 46, 51, 22);
		panel_1.add(label_2);
		
		label_3 = new JLabel("도착지");
		label_3.setBounds(22, 93, 51, 22);
		panel_1.add(label_3);
		
		textField = new JTextField();
		textField.setBounds(81, 42, 141, 32);
		panel_1.add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(81, 85, 141, 32);
		panel_1.add(textField_1);
		
		button = new RoundButton("로그아웃");
		button.addActionListener(new ButtonActionListener());
		button.setText("로그인");
		button.setIcon(getIcon("icon/login.png",30,30));
		button.setBackground(new Color(71, 94, 218));
		button.setBounds(339, 64, 129, 182);
		panel.add(button);
		
		button_1 = new RoundButton("마이페이지");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setIcon(getIcon("icon/user.png",30,30));
		button_1.setBackground(new Color(71, 94, 218));
		button_1.setBounds(492, 64, 129, 182);
		panel.add(button_1);
		
		label = new JLabel();
		try {
			var icon = new ImageIcon(Helper.getTransImage("logo.png", 170, 40));
			label.setIcon(icon);
		} catch (IOException e) {
		}
		label.setBounds(28, 12, 174, 40);
		panel.add(label);
		
		label_4 = new JLabel(getIcon("main.png",911,497));
		label_4.setBounds(0, 0, 911, 497);
		getContentPane().add(label_4);
		
		label_1.setText(String.format("현재시간 : %02d:%02d:%02d", LocalTime.now().getHour(),LocalTime.now().getMinute(),LocalTime.now().getSecond()));
		timer = new Timer(500, e->{
			label_1.setText(String.format("현재시간 : %02d:%02d:%02d", LocalTime.now().getHour(),LocalTime.now().getMinute(),LocalTime.now().getSecond()));
		});
		timer.start();
		
		updateForm();
	}
	@Override
	public void updateForm() {
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
			if(timer!=null&&timer.isRunning()) timer.stop();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno!=-1) {
				User.uno=-1;
				textField.setText("");
				textField_1.setText("");
				updateForm();
				msgInfo("로그아웃되어있습니다.");
			}
			else {
				showPage(new B_로그인());
			}
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(User.uno==-1) return;
			showPage(new F_마이페이지());
		}
	}
	private class Button_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var start = textField.getText();
			var end = textField_1.getText();
			if(User.uno==-1) {
				msgErr("로그인이 되어있지 않습니다.");
				showPage(new B_로그인(()->findRoute(start, end)));
			}
			else {
				findRoute(start, end);
			}
		}

		private void findRoute(String start, String end) {
			try {
				if(start.isBlank()&&end.isBlank()) {
					showPage(new C_경로검색());
					return;
				}
				if(DB.select("select count(*) from station where name in(?,?)", Integer.class, start,end)<2) {
					msgErr("역명을 확인해 주세요.");
					return;
				}
				int startno = DB.select("select sno from station where name = ?", Integer.class, start);
				int endno = DB.select("select sno from station where name = ?", Integer.class, end);
				showPage(new C_경로검색(RouteService.stations.get(startno-1), RouteService.stations.get(endno-1)));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class RoundPanel extends JPanel {
	float alpha;
	public RoundPanel(float alpha) {
		setOpaque(false);
		this.alpha = alpha;
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);	
		g2.dispose();
	}
}
class RoundButton extends JButton {
	public RoundButton(String txt) {
		super(txt);
		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
		setForeground(Color.white);
	}
	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 25, 25);
		g2.dispose();
		super.paintComponent(g);
	}
}
class RoundBorder extends LineBorder {
	int rad=25;
	public RoundBorder(Color c, int rad) {
		super(c);
		this.rad = rad;
	}
	public RoundBorder(Color c) {
		super(c);
	}
	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(lineColor);
		g2.drawRoundRect(0, 0, width-1, height-1, rad, rad);
		g2.dispose();
	}
}