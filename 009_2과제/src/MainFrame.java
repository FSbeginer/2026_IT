import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalTime;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.GridLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class MainFrame extends BF {

	private JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label;
	public JPanel panel_3;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	private Timer timer;
	public static MainFrame instance;
	public JButton button;
	public JButton button_1;
	public JButton button_2;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame frame = new MainFrame();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public MainFrame() {
		instance = this;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 891, 672);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 80));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		label = new JLabel("iDelivery");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setIcon(getIcon("logo/logo.png",40,40));
		label.setBounds(10, 12, 163, 56);
		panel.add(label);
		
		panel_3 = new JPanel();
		panel_3.setBounds(308, 12, 266, 56);
		panel.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		label_3 = new JLabel("New label");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		panel_3.add(label_3, BorderLayout.CENTER);
		
		label_1 = new JLabel("충전소");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setIcon(getIcon("logo/cash.png",40,40));
		label_1.setBounds(640, 14, 106, 54);
		panel.add(label_1);
		
		label_2 = new JLabel(getIcon("logo/user.png", 40, 40));
		label_2.setBounds(758, 14, 106, 54);
		panel.add(label_2);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(227, 227, 227));
		panel_1.setPreferredSize(new Dimension(10, 70));
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		button = new JButton("홈");
		button.addActionListener(new ButtonActionListener());
		button.setIcon(getIcon("logo/home.png",25,25));
		button.setVerticalTextPosition(3);
		button.setHorizontalTextPosition(0);
		panel_1.add(button);
		
		button_1 = new JButton("상품");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setIcon(getIcon("logo/product.png", 25, 25));
		button_1.setVerticalTextPosition(3);
		button_1.setHorizontalTextPosition(0);
		panel_1.add(button_1);
		
		button_2 = new JButton("설정");
		button_2.addActionListener(new Button_2ActionListener());
		button_2.setIcon(getIcon("logo/setting.png", 25, 25));
		button_2.setVerticalTextPosition(3);
		button_2.setHorizontalTextPosition(0);
		panel_1.add(button_2);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(227, 227, 227));
		contentPane.add(panel_2, BorderLayout.CENTER);
		
		updateForm();
		timer();
		B_홈 b_홈 = new B_홈();
		b_홈.panel_4.setBackground(new Color(240, 240, 240));
		b_홈.setBackground(new Color(240, 240, 240));
		showPage(b_홈);
	}
	public void showPage(BP bp) {
		panel_2.removeAll();
		panel_2.setLayout(new BorderLayout(0, 0));
		panel_2.add(bp);
		setTitle(bp.text);
		panel_2.revalidate();
		panel_2.repaint();
	}
	private void timer() {
		label_3.setText(String.format("%01d%01d:%01d%01d:%01d%01d", LocalTime.now().getHour()/10,LocalTime.now().getHour()%10,
				LocalTime.now().getMinute()/10, LocalTime.now().getMinute()%10,
				LocalTime.now().getSecond()/10, LocalTime.now().getSecond()%10));
		timer = new Timer(500, (e)->{
			label_3.setText(String.format("%01d%01d:%01d%01d:%01d%01d", LocalTime.now().getHour()/10,LocalTime.now().getHour()%10,
					LocalTime.now().getMinute()/10, LocalTime.now().getMinute()%10,
					LocalTime.now().getSecond()/10, LocalTime.now().getSecond()%10));
		});
		timer.start();
	}

	@Override
	public void updateForm() {
		try {
			var rs = DB.res("select * from user where uno = ?", User.uno);
			if(rs.next()) {
				label_2.setText(String.format("<html><div align = 'right'> %s <br>%,dP<br>룰렛 %d회", rs.getString("uname"), rs.getInt("point"), rs.getInt("chance")));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new E_충전소());
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showPage(new B_홈());
		}
	}
	private class Button_2ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showPage(new D_설정());
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			showPage(new C_상품());
		}
	}
}
