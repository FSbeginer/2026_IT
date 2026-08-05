import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.JComboBox;
import javax.swing.JButton;
import javax.swing.DefaultComboBoxModel;

public class D_장바구니 extends BF {
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JScrollPane scrollPane;
	public JPanel panel_2;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_장바구니 frame = new D_장바구니();
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
	public D_장바구니() {
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		
		label = new JLabel("장바구니");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(10, 12, 123, 36);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBackground(new Color(248, 248, 248));
		panel.setBorder(null);
		panel.setBounds(10, 51, 521, 380);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 547, 380);
		panel.add(scrollPane);
		
		panel_2 = new JPanel();
		panel_2.setBackground(new Color(249, 249, 249));
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(230, 230, 230)));
		panel_1.setBounds(541, 51, 268, 266);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel("주문 예상 금액");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		label_1.setBounds(9, 5, 248, 39);
		panel_1.add(label_1);
		
		label_2 = new JLabel("총 상품 가격");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_2.setBounds(10, 48, 122, 23);
		panel_1.add(label_2);
		
		label_3 = new JLabel("쿠폰 할인");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_3.setBounds(10, 76, 122, 23);
		panel_1.add(label_3);
		
		label_4 = new JLabel("쿠폰");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 13));
		label_4.setBounds(11, 107, 122, 23);
		panel_1.add(label_4);
		
		label_5 = new JLabel("");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_5.setBounds(138, 90, 122, 23);
		panel_1.add(label_5);
		
		label_6 = new JLabel("");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_6.setBounds(138, 62, 122, 23);
		panel_1.add(label_6);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ComboBoxActionListener());
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"쿠폰 선택"}));
		comboBox.setBounds(10, 131, 193, 31);
		panel_1.add(comboBox);
		
		button = new RoundButton("+");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(91, 98, 227));
		button.setForeground(Color.WHITE);
		button.setBounds(210, 129, 46, 37);
		panel_1.add(button);
		
		label_7 = new JLabel("");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 19));
		label_7.setBounds(12, 170, 248, 39);
		panel_1.add(label_7);
		
		button_1 = new RoundButton("+");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setText("");
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(new Color(91, 98, 227));
		button_1.setBounds(10, 217, 248, 37);
		panel_1.add(button_1);
		setBackground(new Color(240, 240, 240));
		setTitle("장바구니");
		setBounds(100, 100, 835, 482);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		
		addcoupon();
	}
	
	List<Coupon> rewards = new ArrayList<>();
	private void addcoupon() {
		try (var rs = DB.res("select * from coupon join reward using(reno) where uno = ? ;",User.uno)) {
			while(rs.next()) {
				var date = rs.getDate("cpdate").toLocalDate();
				if(LocalDate.now().isAfter(date.plusDays(7))) continue;
				rewards.add(new Coupon(rs.getInt("cpno"), rs.getDouble("resale")));
				comboBox.addItem(comboBox.getItemCount()+"번 쿠폰 - "+String.format("%d%%", (int)(rs.getDouble("resale")*100)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	class Coupon {
		int cpno;
		double reward;
		public Coupon(int cpno, double reward) {
			this.cpno = cpno;
			this.reward = reward;
		}
	}

	List<PayInfo> selectedPayInfo = new ArrayList<PayInfo>();
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JComboBox comboBox;
	public JButton button;
	public JLabel label_7;
	public RoundButton button_1;
	
	private void load() {
		panel_2.removeAll();
		try (var rs = DB.res("select *, sum(ctcount) cnt  from (select *, row_number() over(order by ctno desc) r from cart) sub join product using(pno) where uno = ? group by pno;", User.uno)) {
			int w =473,  h = 163, i = 0;
			while(rs.next()) {
				var pp = new D_패널(new PayInfo(rs.getInt("pno"), rs.getString("pname"), rs.getInt("cnt"), rs.getInt("pprice")));
				pp.setLocation(10, 10+173*i);
				int pno =rs.getInt("pno");
				pp.label_3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							DB.delete("cart", "pno = ? and uno = ?", pno, User.uno);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				});
				pp.checkBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(pp.checkBox.isSelected()) {
							selectedPayInfo.add(pp.info);
						}
						else {
							selectedPayInfo.remove(pp.info);
						}
						loadText();
					}
				});
				pp.checkBox.setSelected(true);
				pp.info.counter = pp.counter;
				selectedPayInfo.add(pp.info);
				panel_2.add(pp);
				i++;
			}
			panel_2.setPreferredSize(new Dimension(0,10+173*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_2.revalidate();
		panel_2.repaint();
		
		loadText();
	}
	int selectedReward = -1;
	private void loadText() {
		int sum = selectedPayInfo.stream().mapToInt(x->x.cnt*x.price).sum();
		label_5.setText(String.format("%,d원", sum));
		int sale = (int) (selectedReward==-1? 0 : sum * rewards.get(selectedReward).reward);
		label_6.setText(String.format("%,d원", sale));
		label_7.setText(String.format("%,d원", sum-sale));
		button_1.setText(String.format("총 %d개 상품 구매하러가기", selectedPayInfo.size()));
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			selectedReward = comboBox.getSelectedIndex()-1;
			loadText();
		}
	}
	private class ComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(comboBox.getSelectedIndex()==0)
				selectedReward = -1;
			loadText();
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			for (var info : selectedPayInfo) {
				try {
					var cnt = DB.select("select pcount from product where pno = ?", Integer.class, info.pno);
					if(cnt<info.cnt) {
						var name = DB.select("select pname from product where pno = ?", String.class, info.pno);
						int r= JOptionPane.showConfirmDialog(null, String.format("%s의 재고수량이 %d개입니다.\n수정하시겠습니까?", name, cnt), "경고", 0,1);
						if(r==0) {
							info.setCnt(cnt);
							loadText();
						}
						else {
							return;
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			showPage(new E_결제(selectedPayInfo, selectedReward==-1?-1 : rewards.get(selectedReward).cpno));
		}
	}
}
