package test;
import javax.swing.JPanel;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class C_게시물패널 extends JPanel {
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
	
	int pno;
	public C_게시물패널(int pno) {
		this.pno = pno;
		setSize( 415, 576);
		setLayout(null);
		
		label_1 = new JLabel("로딩중...");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setOpaque(true);
		label_1.setBackground(Color.DARK_GRAY);
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(292, 83, 57, 22);
		add(label_1);
		
		label_3 = new JLabel(">");
		label_3.addMouseListener(new Label_3MouseListener());
		label_3.setForeground(Color.WHITE);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(315, 203, 34, 80);
		add(label_3);
		
		label_2 = new JLabel("<");
		label_2.addMouseListener(new Label_2MouseListener());
		label_2.setForeground(Color.WHITE);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 23));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(22, 203, 34, 80);
		add(label_2);
		
		label = new JLabel("");
		label.setBounds(12, 73, 352, 351);
		add(label);
		
		label_4 = new JLabel("");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setBounds(37, 445, 41, 36);
		add(label_4);
		
		label_5 = new JLabel("");
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setBounds(101, 445, 41, 36);
		add(label_5);
		
		label_6 = new JLabel("");
		label_6.addMouseListener(new Label_6MouseListener());
		label_6.setBounds(173, 445, 41, 36);
		add(label_6);
		
		label_7 = new JLabel("New label");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_7.setBounds(12, 491, 352, 15);
		add(label_7);
		
		label_8 = new JLabel("New label");
		label_8.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_8.setBounds(12, 516, 352, 15);
		add(label_8);
		
		label_9 = new JLabel("New label");
		label_9.setForeground(Color.GRAY);
		label_9.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		label_9.setBounds(12, 541, 352, 15);
		add(label_9);

		
		load();
	}

	List<Image> imgs = new ArrayList<Image>();
	volatile int generation = 0;
	int maxGeneration = 0;
	private void load() {
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ? ",pno)) {
			rs.next();
			label_10 = Rows.profilePost(rs.getInt("u_no"), rs.getString("u_nick"), 290, 35);
			label_10.setBounds(12, 18, 290, 36);
			add(label_10);
			
			var files = rs.getString("p_files").split(",");
			
			for (var file : files) {
				Helper.getImage("posts/"+file+".jpg", label.getWidth(), label.getHeight(), false, x->{
					imgs.add(x);
					generation++;
					if(maxGeneration==generation)
						lendering();
				});
				maxGeneration++;
			}
			
			label_5.setIcon(BF.getIcon("icons/comment.png",40,40));
			label_6.setIcon(BF.getIcon("icons/send.png",40,40));
			label_7.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			label_8.setText(rs.getString("p_content"));
			label_9.setText(rs.getString("p_date"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from likes where u_no = ? and p_no = ?",User.uno,pno)) {
			if(rs.next()) {
				label_4.setIcon(BF.getIcon("icons/heart2.png",40,40));
			}
			else {
				label_4.setIcon(BF.getIcon("icons/heart1.png",40,40));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	int idx = 0;
	private void lendering() {
		label.setIcon(new ImageIcon(imgs.get(idx)));
		label_1.setText(String.format("%d / %d", idx +1, imgs.size()));
		label_2.setVisible(idx!=0);
		label_3.setVisible(idx!=imgs.size()-1);
	}
	private class Label_2MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			idx--;
			lendering();
		}
	}
	private class Label_3MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			idx++;
			lendering();
		}
	}
	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where u_no = ? and p_no = ?",User.uno,pno)) {
				if(rs.next()) {
					DB.update("post", "p_like = p_like-1","p_no=?", pno);
					DB.delete("likes", "p_no = ? and u_no =?", pno,User.uno);
					label_4.setIcon(BF.getIcon("icons/heart1.png",40,40));
				}
				else {
					DB.update("post", "p_like = p_like+1","p_no=?", pno);
					DB.insert("likes", 0,pno, User.uno,LocalDateTime.now());
					label_4.setIcon(BF.getIcon("icons/heart2.png",40,40));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
			try (var rs = DB.res("select * from post join user using(u_no) where p_no = ? ",pno)) {
				rs.next();
				label_7.setText(String.format("좋아요 %d개", rs.getInt("p_like")));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class Label_6MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((BF)SwingUtilities.getWindowAncestor(label)).showPage(new G_공유하기(pno));
		}
	}
	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			((BF)SwingUtilities.getWindowAncestor(label)).showPage(new F_댓글(pno));
		}
	}
}
