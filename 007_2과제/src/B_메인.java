import java.awt.EventQueue;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Path2D;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Control;
import javax.sound.sampled.Line;
import javax.sound.sampled.LineListener;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.Line.Info;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

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
		setBounds(100, 100, 693, 476);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("Mypage");
		label.addMouseListener(new LabelMouseListener());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label.setBounds(555, 10, 113, 34);
		getContentPane().add(label);
		
		label_1 = new JLabel("진료 선택");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 29));
		label_1.setForeground(new Color(102, 165, 217));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(22, 42, 633, 65);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBounds(22, 117, 633, 297);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 3, 10, 15));
		
		addCategory();
	}

	private void addCategory() {
		try (var rs = DB.res("select * from category")) {
			while(rs.next()) {
				CategoryPanel pp = new CategoryPanel(rs.getInt(1), rs.getString(2), String.format("icon/%d.png", rs.getInt(1)));
				panel.add(pp);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	class CategoryPanel extends JLabel{
		Color color = Color.getHSBColor((float) Math.random(), 0.1f, 0.9f);
		int cno;
		public CategoryPanel(int cno, String name, String path) {
			setHorizontalAlignment(0);
			this.cno = cno;
			setText(name);
			setHorizontalTextPosition(0);
			setVerticalTextPosition(3);
			setOpaque(true);
			setBackground(color);
			try {
				setIcon(new ImageIcon(Helper.getTransImage(path, 70, 70)));
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					setIcon(BF.getIcon(path,70,70));
					setBackground(color.WHITE);
					setBorder(new LineBorder(label_1.getForeground(),4));
				}
				@Override
				public void mouseExited(MouseEvent e) {
					setBackground(color);
					try {
						setIcon(new ImageIcon(Helper.getTransImage(path, 70, 70)));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					setBorder(null);
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					showPage(new C_의사선택(cno));
				}
			});
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new F_마이페이지());
		}
	}
}

