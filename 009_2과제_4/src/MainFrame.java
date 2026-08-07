import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.BorderLayout;
import javax.swing.JPanel;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;

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
		setBounds(100, 100, 928, 649);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(new BorderLayout(0, 0));
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 80));
		getContentPane().add(panel, BorderLayout.NORTH);
		panel.setLayout(null);
		
		label = new JLabel("  iDelivery");
		label.setIcon(getIcon("logo/logo.png",40,40));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setBounds(11, 7, 207, 61);
		panel.add(label);
		
		label_1 = new JLabel("  충전소");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setIcon(getIcon("logo/cash.png",40,40));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);
		label_1.setBounds(595, 11, 148, 58);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setIcon(getIcon("logo/user.png",40,40));
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(764, 10, 148, 58);
		panel.add(label_2);
		
		panel_1 = new JPanel();
		panel_1.setPreferredSize(new Dimension(10, 70));
		getContentPane().add(panel_1, BorderLayout.SOUTH);
		
		panel_2 = new JPanel();
		getContentPane().add(panel_2, BorderLayout.CENTER);
		panel_2.setLayout(null);
		updateForm();
		addIcon();
	}
	private void addIcon() {
		String[] path="home,product,setting".split(",");
		String[] name="홈,상품,설정".split(",");
		for (int i = 0; i < 3; i++) {
			var btn = new JButton(getIcon("logo/"+path[i]+".png",40,40));
			btn.setText(name[i]);
			btn.setHorizontalTextPosition(0);
			btn.setVerticalTextPosition(3);
			int idx = i;
			btn.addActionListener(e->btnHandle(idx));
			panel_1.add(btn);
		}
	}

	private void btnHandle(int idx) {
		switch (idx) {
		case 0:
			if(getTitle().equals("홈")) return;
			showpage(new B_홈(), "홈");
			break;
		case 1:
			if(getTitle().equals("상품")) return;
			showpage(new C_상품(), "상품");
			break;
		case 2:
			if(getTitle().equals("설정")) return;
			showpage(new D_설정(), "설정");
			break;

		}
	}

	private void showpage(BP bp, String string) {
		panel_2.removeAll();
		setTitle(string);
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
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showpage(new E_충전소());
		}
	}
}
