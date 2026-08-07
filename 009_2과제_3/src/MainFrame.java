import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainFrame extends BF {
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label;
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
		setBounds(100, 100, 973, 690);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 80));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		label = new JLabel("  iDelivery");
		label.setIcon(getIcon("logo/logo.png",50,50));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 21));
		label.setBounds(12, 10, 174, 60);
		panel.add(label);
		
		label_1 = new JLabel("");
		label_1.setBounds(320, 10, 316, 60);
		panel.add(label_1);
		
		label_2 = new JLabel("충전소");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setIcon(getIcon("logo/cash.png",50,50));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(671, 10, 130, 60);
		panel.add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setIcon(getIcon("logo/user.png",50,50));
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_3.setHorizontalAlignment(SwingConstants.RIGHT);
		label_3.setBounds(815, 10, 130, 60);
		panel.add(label_3);
		
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 70));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		panel_1.setLayout(new GridLayout(0, 3, 0, 0));
		
		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		
		addIcon();
		updateForm();
		showPage(new B_홈(),"홈");
	}
	@Override
	public void updateForm() {
		try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
			rs.next();
			label_3.setText(String.format("<html><div align = 'right'>%s<br>%,dP<br>룰렛 %d회", rs.getString("uname"),rs.getInt("point"),rs.getInt("chance")));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private void addIcon() {
		String[] path = "home,product,setting".split(",");
		String[] name = "홈,상품,설정".split(",");
		for (int i = 0; i < 3; i++) {
			var btn = new JButton(name[i]);
			int idx = i;
			btn.setIcon(getIcon("logo/"+path[idx]+".png",30,30));
			btn.addActionListener(e->{btnHandle(idx);});
			btn.setFont(new Font("맑은 고딕",1,12));
			btn.setHorizontalTextPosition(0);
			btn.setVerticalTextPosition(3);
			panel_1.add(btn);
		}
	}
	private void btnHandle(int idx) {
		switch (idx) {
		case 0:
			if(getTitle().equals("홈")) return;
			showPage(new B_홈(),"홈");
			break;
		case 1:
			if(getTitle().equals("상품")) return;
			showPage(new C_상품(),"상품");
			break;
		case 2:
			if(getTitle().equals("설정")) return;
			showPage(new D_설정(),"설정");
			break;
		}
	}
	public void showPage(BP bp,String title) {
		panel_2.removeAll();
		setTitle(title);
		panel_2.add(bp);
		panel_2.revalidate();
		panel_2.repaint();
	}
	
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new E_충전소());
		}
	}
}
