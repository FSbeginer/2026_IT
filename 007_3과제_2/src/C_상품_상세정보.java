import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.awt.geom.Ellipse2D.Double;
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
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class C_상품_상세정보 extends BF {

	/**
	 * Create the frame.
	 * @param pno 
	 */
	int pno;
	public JScrollPane scrollPane;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JPanel panel_1;
	public JLabel label_5;
	public Counter counter;
	public JButton button;
	public JButton button_1;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public C_상품_상세정보(int pno) {
		setTitle("상품 상세정보");
		this.pno = pno;
		setBounds(100, 100, 655, 729);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		panel.setPreferredSize(new Dimension(10, 1200));
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		label_9 = new JLabel("");
		label_9.addMouseListener(new Label_9MouseListener());
		label_9.setBounds(222, 218, 41, 38);
		panel.add(label_9);
		
		label = new JLabel("");
		label.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label.setBounds(20, 22, 243, 234);
		panel.add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_1.setBounds(273, 22, 339, 38);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setBounds(273, 72, 339, 21);
		panel.add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setBounds(273, 105, 339, 21);
		panel.add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_4.setForeground(new Color(255, 128, 0));
		label_4.setBounds(273, 143, 339, 21);
		panel.add(label_4);
		
		panel_1 = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				String txt = "★★★★★";
				String txt2 = "☆☆☆☆☆";
				g2.setFont(new Font("맑은고딕",0,24));
				g2.setColor(new Color(255,128,0));
				var fm = g2.getFontMetrics();
				int mw = fm.stringWidth(txt);
				g2.drawString(txt2, 0, 18);
				g2.clip(new Rectangle2D.Double(0,0,(star/5)*mw, 100));
				g2.drawString(txt, 0, 18);
				g2.dispose();
			}
		};
		panel_1.setBounds(273, 176, 114, 30);
		panel.add(panel_1);
		
		label_5 = new JLabel("0,0");
		label_5.addMouseListener(new Label_5MouseListener());
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_5.setForeground(new Color(255, 128, 0));
		label_5.setBounds(397, 172, 60, 34);
		panel.add(label_5);
		
		counter = new Counter();
		counter.setBounds(283, 226, 127, 30);
		panel.add(counter);
		
		button = new RoundButton("장바구니");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(new Color(0, 0, 0));
		button.setBounds(348, 268, 127, 38);
		panel.add(button);
		
		button_1 = new RoundButton("바로 결제");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(255, 128, 0));
		button_1.setForeground(Color.WHITE);
		button_1.setBounds(485, 268, 127, 38);
		panel.add(button_1);
		
		label_6 = new JLabel("상세 설명");
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_6.setBounds(10, 313, 85, 21);
		panel.add(label_6);
		
		label_7 = new JLabel("New label");
		label_7.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_7.setVerticalAlignment(SwingConstants.TOP);
		label_7.setBounds(10, 346, 600, 59);
		panel.add(label_7);
		
		label_8 = new JLabel("");
		label_8.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_8.setBounds(10, 417, 600, 750);
		panel.add(label_8);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	
	double star;
	private int price;
	private void load() {
		try (var rs = DB.res("select *, round(avg(rstar),1) star from product left join review using(pno) join detail using(dno) join category using(cno) where pno = ? group by pno",pno)) {
			rs.next();
			label.setIcon(getIcon("product/"+rs.getInt("pno")+".png",label.getWidth(),label.getHeight()));
			label_1.setText(rs.getString("pname"));
			label_2.setText(rs.getString("pcompany"));
			label_3.setText(String.format("종류 : %s / %s", rs.getString("cname"), rs.getString("dname")));
			label_4.setText(String.format("%,d원", rs.getInt("pprice")));
			label_5.setText(String.format("%.1f", rs.getDouble("star")));
			counter.setMax(rs.getInt("pcount"));
			price = rs.getInt("pprice");
			star = rs.getDouble("star");
			label_7.setText(rs.getString("pcontent"));
			label_8.setIcon(getIcon("info/"+pno+".png",label_8.getWidth(),label_8.getHeight()));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from likes where uno = ? and pno =?;",User.uno,pno)) {
			if(rs.next()) {
				label_9.setIcon(getIcon("heart_on.png",40,40));
			}
			else {
				label_9.setIcon(getIcon("heart_off.png",40,40));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class Label_9MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			try (var rs = DB.res("select * from likes where uno = ? and pno =?;",User.uno,pno)) {
				if(rs.next()) {
					label_9.setIcon(getIcon("heart_off.png",40,40));
					DB.delete("likes", "uno = ? and pno = ?", User.uno, pno);
				}
				else {
					label_9.setIcon(getIcon("heart_on.png",40,40));
					DB.insert("likes", 0, pno, User.uno, LocalDate.now());
				}
			} catch (SQLException e2) {
			}
		}
	}
	private class Label_5MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new I_리뷰(pno));
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var list = new ArrayList<PayInfo>();
			list.add(new PayInfo(pno, label_1.getText(),counter.getCnt(), price));
			showPage(new E_결제(list,-1));
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				DB.insert("cart", 0,pno,User.uno,counter.getCnt());
				
				int r = JOptionPane.showConfirmDialog(null, "장바구니로 이동하시겠습니까?", "질문", 0, 3);
				if(r==0) {
					showPage(new D_장바구니());
				}
				else {
					dispose();
					previous();
				}
			} catch (SQLException e1) {
			}
		}
	}
}
class PayInfo{
	int pno;
	String name;
	int cnt, price;
	public PayInfo(int pno, String name, int cnt, int price) {
		this.pno = pno;
		this.name = name;
		this.cnt = cnt;
		this.price = price;
	}
	Counter counter;
	public void setCnt(int cnt) {
		this.cnt = cnt;
		if(counter!=null) counter.setCnt(cnt);
	}
}
