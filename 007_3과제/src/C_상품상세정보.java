import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class C_상품상세정보 extends BF {

	int pno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public Counter panel_1;
	public JButton button;
	public JButton button_1;
	public JLabel label_6;
	public JTextArea textArea;
	public JLabel lblad;
	public JLabel label_7;
	public JPanel panel_2;
	public C_상품상세정보(int pno) {
		setTitle("상품 상세정보");
		this.pno = pno;
		setBounds(100, 100, 595, 676);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 579, 637);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		label_7 = new JLabel("New label");
		label_7.addMouseListener(new Label_7MouseListener());
		label_7.setBounds(187, 184, 43, 35);
		panel.add(label_7);
		
		label = new JLabel("");
		label.setBounds(22, 23, 213, 202);
		panel.add(label);
		label.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label_1.setBounds(265, 23, 287, 35);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setForeground(Color.GRAY);
		label_2.setBounds(265, 70, 287, 22);
		panel.add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(263, 95, 287, 22);
		panel.add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_4.setForeground(new Color(255, 128, 0));
		label_4.setBounds(264, 123, 236, 35);
		panel.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setForeground(new Color(255, 128, 0));
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_5.setBounds(434, 159, 89, 22);
		panel.add(label_5);
		
		panel_1 = new Counter();
		panel_1.setBounds(260, 195, 125, 35);
		panel.add(panel_1);
		
		button = new RoundButton("장바구니");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(240, 240, 240));
		button.setForeground(Color.BLACK);
		button.setBounds(260, 242, 138, 41);
		panel.add(button);
		
		button_1 = new RoundButton("바로 결제");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(255, 128, 0));
		button_1.setBounds(416, 241, 138, 41);
		panel.add(button_1);
		
		label_6 = new JLabel("상세 설명");
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_6.setBounds(35, 285, 89, 21);
		panel.add(label_6);
		
		textArea = new JTextArea();
		textArea.setFocusable(false);
		textArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
		textArea.setEditable(false);
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBounds(35, 314, 514, 54);
		panel.add(textArea);
		
		lblad = new JLabel("");
		lblad.setBounds(35, 375, 524, 36);
		panel.add(lblad);
		
		panel_2 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("Dialog", 0, 30));
				var txt = "★★★★★";
				int tw = g2.getFontMetrics().stringWidth(txt);
				g2.setColor(new Color(255, 128, 0));
				g2.drawString("☆☆☆☆☆", 0, 20);
				Shape shp = new Rectangle2D.Double(0,0,(star/5.0)*tw, getHeight());
				g2.clip(shp);
				g2.drawString(txt, 0, 20);
			}
		};
		panel_2.setBounds(265, 159, 143, 22);
		panel.add(panel_2);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	double star = 0;
	private void load() {
		try (var rs = DB.res("select * from likes where pno =? and uno = ?", pno, User.uno)) {
			if(rs.next()) {
				label_7.setIcon(getIcon("heart_on.png", 35,35));
			}
			else {
				label_7.setIcon(getIcon("heart_off.png", 35,35));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select product.*, cname, dname, round(avg(rstar),1) st from product join detail using(dno) join category using(cno) left join review using(pno) where pno = ? group by pno order by pno", pno)) {
			rs.next();
			label.setIcon(getIcon("product/"+pno+".png",label.getWidth(),label.getHeight()));
			label_1.setText(rs.getString("pname"));
			label_2.setText(rs.getString("pcompany"));
			label_3.setText(String.format("종류 : %s / %s", rs.getString("cname"), rs.getString("dname")));
			label_4.setText(String.format("%,d원", rs.getInt("pprice")));
			star=rs.getDouble("st");
			panel_1.setMax(rs.getInt("pcount"));
			label_5.setText(String.format("%.1f", star));
			textArea.setText(rs.getString("pcontent"));
			try {
				var img = getIcon("info/"+pno+".png").getImage();
				lblad.setSize(lblad.getWidth(), img.getHeight(null));
				lblad.setIcon(getIcon("info/"+pno+".png", lblad.getWidth(), lblad.getHeight()));
				int toth = getHeight()+lblad.getHeight()-280;
				panel.setPreferredSize(new Dimension(0, toth));
			} catch (Exception e) {
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class Label_7MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where pno =? and uno = ?", pno, User.uno)) {
				if(!rs.next()) {
					DB.insert("likes", 0,pno, User.uno, LocalDate.now());
					label_7.setIcon(getIcon("heart_on.png", 35,35));
				}
				else {
					DB.delete("likes", "uno = ? and pno = ?", User.uno, pno);
					label_7.setIcon(getIcon("heart_off.png", 35,35));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				DB.insert("cart", 0,pno,User.uno,panel_1.getCnt());
				var r = JOptionPane.showConfirmDialog(rootPane, "장바구니로 이동하시겠습니까?", "질문", JOptionPane.YES_NO_OPTION,3);
				if(r==JOptionPane.YES_OPTION) {
					showPage(new D_장바구니());
				}
				else {
					dispose();
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				DB.insert("cart", 0,pno,User.uno,panel_1.getCnt());
				List<Integer> list = new  ArrayList<Integer>();
				var rs = DB.res("select ctno from cart order by desc limit 1");
				rs.next();
				list.add(rs.getInt(1));
				showPage(new E_결제(list,0));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
