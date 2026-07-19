package n003_3;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class C_패널 extends JPanel {
	public Profile panel;
	public JLabel label;
	public JPanel panel_1;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	/**
	 * Create the panel.
	 */
	int pno;

	public C_패널(int pno) {
		this.pno = pno;
		setSize(350, 479);
		setLayout(null);

		panel = new Profile(true);
		panel.setBounds(12, 10, 40, 42);
		add(panel);

		label = new JLabel();
		label.setBounds(64, 20, 231, 21);
		add(label);

		panel_1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				if (imgs!=null && imgs[idx] != null) {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					g2.drawImage(imgs[idx], 0, 0, null);
					g2.setColor(Color.black);
					int w = 50, h = 25;
					g2.translate(getWidth() - w - 20, 20);
					g2.fillRect(0, 0, w, h);
					var txt = String.format("%d / %d", idx + 1, imgs.length);
					g2.setColor(Color.white);
					g2.drawString(txt, (w - g2.getFontMetrics().stringWidth(txt)) / 2, h - 8);
				}
			}
		};
		panel_1.setBounds(12, 62, 310, 316);
		add(panel_1);
		panel_1.setLayout(null);

		label_7 = new JLabel("   <");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_7.setForeground(Color.WHITE);
		label_7.setBounds(0, 109, 22, 50);
		panel_1.add(label_7);

		label_8 = new JLabel(">");
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_8.setForeground(Color.WHITE);
		label_8.setBounds(273, 109, 22, 50);
		panel_1.add(label_8);

		label_1 = new JLabel(BF.getIcon("icon/heart2.png", 30, 25));
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setBounds(12, 388, 40, 28);
		add(label_1);

		label_2 = new JLabel(BF.getIcon("icon/comment.png", 30, 25));
		label_2.setBounds(65, 388, 40, 28);
		add(label_2);

		label_3 = new JLabel(BF.getIcon("icon/send.png", 30, 25));
		label_3.setBounds(120, 388, 40, 28);
		add(label_3);

		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_4.setBounds(12, 425, 144, 15);
		add(label_4);

		label_5 = new JLabel("New label");
		label_5.setBounds(13, 443, 210, 18);
		add(label_5);

		label_6 = new JLabel("New label");
		label_6.setForeground(Color.GRAY);
		label_6.setBounds(12, 463, 210, 18);
		add(label_6);

		load();
	}

	int uno;
	int idx;
	String[] files;
	Image[] imgs;
	
	public void loadImage() {
		if(imgs != null) return;
		BF.getImage(String.format("profile/%d.jpg", uno), 38, 38, x -> panel.setImage(x)).execute();
		imgs = new Image[files.length];
		int i = 0;
		for (var file : files) {
			int idx = i;
			BF.getImage(String.format("posts/%s.jpg", file), panel_1.getWidth(), panel_1.getHeight(), x -> {
				imgs[idx] = x;
				panel_1.repaint();
			}).execute();
			i++;
		}
	}

	private void load() {
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?", pno)) {
			rs.next();
			label.setText(rs.getString("u_nick"));
			label_4.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			label_5.setText(rs.getString("p_content"));
			label_6.setText(rs.getString("p_date"));
			label_2.setIcon(BF.getIcon("icons/comment.png",30,30));
			label_3.setIcon(BF.getIcon("icons/send.png",30,30));
			uno = rs.getInt("u_no");
			files = rs.getString("p_files").split(",");
		} catch (SQLException e1) {
			e1.printStackTrace();
		}
		
		try (var rs = DB.res("select * from likes where p_no = ? and u_no = ?", pno,User.uno)) {
			if(rs.next()) {
				label_1.setIcon(BF.getIcon("icons/heart2.png",30,30));
			}else {
				label_1.setIcon(BF.getIcon("icons/heart1.png",30,30));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where p_no = ? and u_no = ?", pno,User.uno)) {
				if(!rs.next()) {
					DB.insert("likes", 0,pno,User.uno,LocalDateTime.now());
					label_1.setIcon(BF.getIcon("icons/heart2.png",30,30));
				}else {
					DB.delete("likes", "p_no = ? and u_no = ?", pno, User.uno);
					label_1.setIcon(BF.getIcon("icons/heart1.png",30,30));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
}
