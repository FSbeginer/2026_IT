import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class C_게시물패널 extends JPanel {
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JLabel label_10;

	/**
	 * Create the panel.
	 */
	int pno;
	public C_게시물패널(int pno) {
		this.pno = pno;
		setSize(371, 495);
		setLayout(null);
		
		label_3 = new JLabel("<");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(19, 174, 28, 59);
		add(label_3);
		
		label_4 = new JLabel(">");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setForeground(Color.WHITE);
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label_4.setHorizontalAlignment(SwingConstants.CENTER);
		label_4.setBounds(293, 174, 28, 59);
		add(label_4);
		
		label_2 = new JLabel("New label");
		label_2.setOpaque(true);
		label_2.setBackground(Color.DARK_GRAY);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(264, 81, 57, 22);
		add(label_2);
		
		panel = new JPanel();
		panel.setLayout(new BorderLayout());
		panel.setBounds(11, 20, 45, 43);
		add(panel);
		
		label = new JLabel("New label");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label.setBounds(67, 26, 180, 33);
		add(label);
		
		label_1 = new JLabel("");
		label_1.setBounds(21, 69, 310, 280);
		add(label_1);
		
		label_5 = new JLabel("");
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setBounds(11, 359, 45, 33);
		add(label_5);
		
		label_6 = new JLabel("");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setBounds(67, 359, 45, 33);
		add(label_6);
		
		label_7 = new JLabel("");
		label_7.addMouseListener(new Label_7MouseListener());
		label_7.setBounds(124, 359, 45, 33);
		add(label_7);
		
		label_8 = new JLabel("New label");
		label_8.setBounds(12, 399, 268, 22);
		add(label_8);
		
		label_9 = new JLabel("New label");
		label_9.setBounds(12, 431, 268, 22);
		add(label_9);
		
		label_10 = new JLabel("New label");
		label_10.setBounds(12, 463, 268, 22);
		add(label_10);
		
		load();
		SwingUtilities.invokeLater(()-> lendering());
	}
	private void lendering() {
		label_3.setVisible(idx!=0);
		label_4.setVisible(idx!=imgs.size()-1);
		label_2.setText(String.format("%d / %d", idx+1,imgs.size()));
		label_1.setIcon(new ImageIcon(imgs.get(idx)));
	}
	List<Image> imgs;
	int idx = 0;
	private void load() {
		imgs = new ArrayList<>();
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?",pno)) {
			rs.next();
			panel.add(new Profile("profile/"+rs.getInt("u_no")+".jpg", panel.getWidth(), panel.getHeight(),true));
			label.setText(rs.getString("u_nick"));
			var files = rs.getString("p_files").split(",");
			for (String file : files) {
				Helper.getImage("posts/"+file+".jpg", label_1.getWidth(), label_1.getHeight(), x->imgs.add(x));
			}
			label_8.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			label_9.setText(rs.getString("p_content"));
			label_10.setText(rs.getString("p_date"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from likes where u_no = ? and p_no =?;",User.uno, pno)) {
			if(rs.next()) {
				label_5.setIcon(BF.getIcon("icons/heart2.png",30,30));
			}
			else {
				label_5.setIcon(BF.getIcon("icons/heart1.png",30,30));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		label_6.setIcon(BF.getIcon("icons/comment.png",30,30));
		label_7.setIcon(BF.getIcon("icons/send.png",30,30));
	}

	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			idx++;
			lendering();
		}
	}
	private class Label_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			idx--;
			lendering();
		}
	}
	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where u_no = ? and p_no =?;",User.uno, pno)) {
				if(rs.next()) {
					DB.update("post", "p_like=p_like-1", "p_no=?", pno);
					DB.delete("likes", "p_no =? and u_no = ?", pno,User.uno);
					label_5.setIcon(BF.getIcon("icons/heart1.png",30,30));
				}
				else {
					DB.update("post", "p_like=p_like+1", "p_no=?", pno);
					DB.insert("likes", 0,pno,User.uno,LocalDate.now());
					label_5.setIcon(BF.getIcon("icons/heart2.png",30,30));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?",pno)) {
				rs.next();
				label_8.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			} catch (SQLException e2) {
			}
		}
	}
	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((BF)SwingUtilities.getWindowAncestor(label)).showPage(new F_댓글(pno, imgs));
		}
	}
	private class Label_7MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((BF)SwingUtilities.getWindowAncestor(label)).showPage(new G_공유하기(pno));
		}
	}
}
