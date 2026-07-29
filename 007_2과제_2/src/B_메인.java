import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.JPanel;
import java.awt.GridLayout;

public class B_메인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					B_메인 frame = new B_메인();
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
	public B_메인() {
		setTitle("메인");
		setBounds(100, 100, 598, 391);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("진료 선택");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 27));
		label.setForeground(new Color(78, 132, 233));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(209, 37, 164, 38);
		getContentPane().add(label);
		
		label_1 = new JLabel("Mypage");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(483, 10, 87, 15);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBounds(12, 84, 558, 241);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(2, 3, 15, 25));
		
		load();
	}
	
	private void load() {
		try (var rs = DB.res("select * from category order by cno")) {
			while(rs.next()) {
				var jl = new MyLabel(Color.getHSBColor((float) Math.random(), 0.1f, 0.9f), rs.getString("cname"), rs.getInt("cno"));
				panel.add(jl);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new F_마이페이지());
		}
	}
	class MyLabel extends JLabel {
		public MyLabel(Color c, String name, int no) {
			var img = BF.getIcon("icon/"+no+".png",50,50);
			var transImg =new ImageIcon(Helper.getTransParentIcon(img.getImage()));
			
			setText(name);
			setHorizontalAlignment(0);
			setHorizontalTextPosition(0);
			setVerticalTextPosition(3);
			setIcon(transImg);
			setBackground(c);
			setOpaque(true);
			
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setIcon(img);
					setBorder(new LineBorder(label.getForeground(),2));
					setBackground(Color.white);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					setIcon(transImg);
					setBorder(null);
					setBackground(c);
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					showPage(new C_의사선택(no));
				}
			});
		}
	}
}

