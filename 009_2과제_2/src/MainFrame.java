import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.sql.SQLException;

import javax.swing.JLabel;
import java.awt.Font;

public class MainFrame extends BF {

	private JPanel contentPane;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JPanel panel_3;

	/**
	 * Launch the application.
	 */
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

	/**
	 * Create the frame.
	 */
	public MainFrame() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 938, 665);
		contentPane = new JPanel();

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 80));
		contentPane.add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		label = new JLabel("iDelivery");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label.setIcon(getIcon("logo/logo.png",35,35));
		label.setBounds(12, 17, 168, 47);
		panel.add(label);
		
		label_1 = new JLabel("충전소");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		label_1.setIcon(getIcon("logo/cash.png",30,30));
		label_1.setBounds(717, 10, 85, 60);
		panel.add(label_1);
		
		label_2 = new JLabel(getIcon("logo/user.png",30,30));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		label_2.setBounds(814, 10, 96, 60);
		panel.add(label_2);
		
		panel_3 = new JPanel();
		panel_3.setBounds(311, 17, 239, 53);
		panel.add(panel_3);
		
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 70));
		contentPane.add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		panel_2 = new JPanel();
		contentPane.add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		updateForm();
		showPage(new B_홈(this), "홈");
		addIcon();
	}
	private void addIcon() {
		String[] paths ="home,product,setting".split(",");
		String[] names ="홈,상품,설정".split(",");
		for (int i = 0; i < 3; i++) {
			var jl = new JButton(getIcon("logo/"+paths[i]+".png",30,30));
			jl.setHorizontalTextPosition(0);
			jl.setVerticalTextPosition(3);
			jl.setFont(new Font("맑은 고딕", 1, 12));
			jl.setText(names[i]);
			int idx = i;
			jl.addMouseListener(new MouseAdapter() {
				public void mouseClicked(java.awt.event.MouseEvent e) {
					jlHandler(idx, names[idx]);
				}
			});
			panel_1.add(jl);
		}
	}
	
	private void jlHandler(int idx, String name) {
		switch (idx) {
		case 0: 
			showPage(new B_홈(this), name);
			break;
		case 1: 
			showPage(new C_상품(this), name);
			break;
		case 2: 
			showPage(new D_설정(this), name);
			break;
		}
	};

	public void showPage(BP bp, String title) {
		panel_2.removeAll();
		setTitle(title);
		panel_2.add(bp);
		panel_2.revalidate();
		panel_2.repaint();
	}
	@Override
	public void updateForm() {
		try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
			rs.next();
			label_2.setText(String.format("<html><div align = 'right'>%s<br>%,dP<br>룰렛 %d회", rs.getString("uname"),rs.getInt("point"),rs.getInt("chance")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
