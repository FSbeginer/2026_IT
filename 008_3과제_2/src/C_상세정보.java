import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class C_상세정보 extends BF {


	/**
	 * Create the frame.
	 * @param info 
	 */
	Product info;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JComboBox comboBox;
	public JLabel label_6;
	public JComboBox comboBox_1;
	public JLabel label_7;
	public JComboBox comboBox_2;
	public JLabel label_8;
	public JLabel label_9;
	public JLabel label_10;
	public JButton button;
	public JPanel panel;
	public C_상세정보(Product product) {
		setTitle("상세정보");
		this.info = product;
		setBounds(100, 100, 547, 459);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				try {
					Graphics2D g2 = (Graphics2D) g.create();
					g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
					var src = ((ImageIcon)label.getIcon()).getImage();
					g2.drawImage(src, 0, 0, 150, 150, tp.x-15, tp.y-15, tp.x+15, tp.y+15, null);
					g2.dispose();
				} catch (Exception e) {
				}
			}
		};
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBackground(new Color(192, 192, 192));
		panel.setVisible(false);
		panel.setBounds(55, 71, 157, 150);
		getContentPane().add(panel);
		
		label = new JLabel("");
		label.addMouseMotionListener(new LabelMouseMotionListener());
		label.addMouseListener(new LabelMouseListener());
		label.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label.setBounds(12, 10, 239, 214);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("굴림", Font.BOLD, 19));
		label_1.setBounds(12, 234, 194, 34);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("굴림", Font.BOLD, 19));
		label_2.setBounds(263, 10, 239, 34);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("벌점:");
		label_3.setBounds(263, 54, 43, 15);
		getContentPane().add(label_3);
		
		label_4 = new JLabel("");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_4.setForeground(Color.ORANGE);
		label_4.setBounds(321, 54, 151, 15);
		getContentPane().add(label_4);
		
		label_5 = new JLabel("용량");
		label_5.setBounds(265, 91, 57, 15);
		getContentPane().add(label_5);
		
		comboBox = new JComboBox();
		comboBox.setBounds(265, 110, 239, 34);
		getContentPane().add(comboBox);
		
		label_6 = new JLabel("통신사");
		label_6.setBounds(265, 151, 57, 15);
		getContentPane().add(label_6);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setBounds(265, 170, 239, 34);
		getContentPane().add(comboBox_1);
		
		label_7 = new JLabel("할부");
		label_7.setBounds(265, 216, 57, 15);
		getContentPane().add(label_7);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(265, 235, 239, 34);
		getContentPane().add(comboBox_2);
		
		label_8 = new JLabel("요금제");
		label_8.setBounds(266, 284, 57, 15);
		getContentPane().add(label_8);
		
		label_9 = new JLabel("요금제 선택 안됨");
		label_9.setBorder(new LineBorder(Color.LIGHT_GRAY));
		label_9.setHorizontalAlignment(SwingConstants.CENTER);
		label_9.setBounds(267, 308, 171, 37);
		getContentPane().add(label_9);
		
		label_10 = new JLabel("+");
		label_10.addMouseListener(new Label_10MouseListener());
		label_10.setForeground(new Color(0, 128, 0));
		label_10.setFont(new Font("굴림", Font.BOLD, 19));
		label_10.setBorder(new LineBorder(new Color(0, 128, 0), 2, true));
		label_10.setHorizontalAlignment(SwingConstants.CENTER);
		label_10.setBounds(451, 308, 59, 36);
		getContentPane().add(label_10);
		
		button = new JButton("결제하러 가기");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(266, 355, 253, 46);
		getContentPane().add(button);
		
		load();
	}
	private void load() {
		info.capacities.stream().map(x->x.value).forEach(comboBox::addItem);
		info.items.stream().map(x->x.type).forEach(comboBox_1::addItem);
		info.installments.stream().map(x->x.month).forEach(comboBox_2::addItem);
		label.setIcon(getIcon("기종/"+info.pno+".jfif",label.getWidth(),label.getHeight()));
		calPrice();
		label_2.setText(info.name);
		try {
			int star = DB.select("select round(avg(scope),0) star from product join star using(pno) where pno = ? group by pno;", Integer.class, info.pno);
			label_4.setText("★".repeat(star)+"☆".repeat(5-star));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		comboBox.addActionListener(e->calPrice());
		comboBox_1.addActionListener(e->calPrice());
		comboBox_2.addActionListener(e->calPrice());
	}
	private void calPrice() {
		int price = (info.capacities.get(comboBox.getSelectedIndex()).price + info.items.get(comboBox_1.getSelectedIndex()).price)/info.installments.get(comboBox_2.getSelectedIndex()).month;
		label_1.setText(String.format("%,d원 / 월", price));
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			panel.setVisible(true);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			panel.setVisible(false);
		}
	}
	Point tp;
	private class LabelMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			tp = e.getPoint();
			var p = SwingUtilities.convertPoint(label, tp, getContentPane());
			panel.setLocation(p.x+5, p.y+5);
		}
	}
	int rateplan;
	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new G_리뷰(info.pno));
		}
	}
	int rno;
	private class Label_10MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			var f = new 요금제선텍(info.items.get(comboBox_1.getSelectedIndex()).type);
			f.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					rno = f.selrno;
					label_9.setText(f.name);
				}
			});
			f.setVisible(true);
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(rno==0) {
				msgErr("요금제를 선택해주세요.");
				return;
			}
			Capacity cap = info.capacities.get(comboBox.getSelectedIndex());
			Item item = info.items.get(comboBox_1.getSelectedIndex());
			Installment ins = info.installments.get(comboBox_2.getSelectedIndex());
			showPage(new D_결제(new PayInfo(info.pno, info.name, cap, item, ins, rno)));
		}
	}
}
class PayInfo{
	int pno;
	String pname;
	Capacity cap;
	Item item;
	Installment installment;
	int rtno;
	public PayInfo(int pno, String pname, Capacity cap, Item item, Installment installment, int rtno) {
		this.pno = pno;
		this.pname = pname;
		this.cap = cap;
		this.item = item;
		this.installment = installment;
		this.rtno = rtno;
	}
}
