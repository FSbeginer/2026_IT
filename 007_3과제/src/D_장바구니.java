import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;

public class D_장바구니 extends BF {
	public JLabel label;
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JComboBox comboBox;
	public JButton button;
	public JLabel label_5;
	public JButton button_1;
	public JLabel label_6;
	public JLabel label_7;
	
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
		setTitle("장바구니");
		setBounds(100, 100, 794, 529);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("장바구니");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBounds(16, 6, 106, 40);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBounds(11, 49, 479, 386);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 508, 386);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(250, 250, 250));
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		panel_2 = new JPanel();
		panel_2.setBounds(500, 49, 268, 296);
		getContentPane().add(panel_2);
		panel_2.setLayout(null);
		
		label_1 = new JLabel("주문 예상 금액");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_1.setBounds(10, 0, 173, 40);
		panel_2.add(label_1);
		
		label_2 = new JLabel("총 상품 가격");
		label_2.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_2.setBounds(10, 53, 97, 23);
		panel_2.add(label_2);
		
		label_3 = new JLabel("쿠폰 할인");
		label_3.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_3.setBounds(10, 88, 97, 23);
		panel_2.add(label_3);
		
		label_4 = new JLabel("쿠폰");
		label_4.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		label_4.setBounds(10, 123, 97, 23);
		panel_2.add(label_4);
		
		comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"쿠폰 선택"}));
		comboBox.setBounds(10, 158, 189, 30);
		panel_2.add(comboBox);
		
		button = new RoundButton("New button");
		button.addActionListener(new ButtonActionListener());
		button.setText("+");
		button.setBackground(new Color(113, 116, 221));
		button.setBounds(209, 158, 49, 30);
		panel_2.add(button);
		
		label_5 = new JLabel("New label");
		label_5.setHorizontalAlignment(SwingConstants.RIGHT);
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_5.setBounds(120, 200, 138, 30);
		panel_2.add(label_5);
		
		button_1 = new RoundButton("New button");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(113, 116, 221));
		button_1.setBounds(10, 242, 248, 42);
		panel_2.add(button_1);
		
		label_6 = new JLabel("-0원");
		label_6.setHorizontalAlignment(SwingConstants.RIGHT);
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_6.setBounds(130, 88, 128, 23);
		panel_2.add(label_6);
		
		label_7 = new JLabel("0원");
		label_7.setHorizontalAlignment(SwingConstants.RIGHT);
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_7.setBounds(130, 53, 128, 23);
		panel_2.add(label_7);
		
		reload();
		load();
		changePrice();
		addcoupon();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	List<Double> reward ;
	private void addcoupon() {
		reward = new ArrayList<>();
		try (var rs = DB.res("select * from coupon where uno = ? and cpdate >= curdate() - interval 7 day", User.uno)) {
			while(rs.next()) {
				reward.add(rs.getDouble("resale"));
				comboBox.addItem(String.format("%d번 쿠폰 - %d%%", comboBox.getItemCount(),(int)(rs.getDouble("resale")*100)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void reload() {
		try (var rs = DB.res("select ctno, sum(ctcount) from cart where uno = ? group by pno",User.uno)) {
			List<String> ctnos = new ArrayList<>();
			while(rs.next()) {
				ctnos.add(rs.getString(1));
				DB.update("cart", "ctcount = ?", "ctno = ?", rs.getInt(2),rs.getInt(1));
			}
			DB.delete("cart", "ctno not in ("+String.join(",", ctnos)+") and uno = ?", User.uno);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	Map<D_패널, Integer> prices;
	Map<D_패널, Integer> ctnos;
	
	private void load() {
		panel_1.removeAll();
		try (var rs = DB.res("select * from cart join product using(pno) where uno = ?", User.uno)) {
			int i=0;
			prices = new HashMap<D_패널, Integer>();
			ctnos = new HashMap<D_패널, Integer>();
			while(rs.next()) {
				var pp = new D_패널(getIcon("product/"+rs.getInt("pno")+".png",110,110), rs.getString("pname"), rs.getInt("pprice"), rs.getInt("ctcount"));
				int ctno = rs.getInt("ctno");
				int price = rs.getInt("pprice");
				pp.label_3.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						try {
							DB.delete("cart", "ctno = ?", ctno);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
						load();
					}
				});
				pp.checkBox.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						changePrice();
					}
				});
				pp.panel.label_2.addPropertyChangeListener(new PropertyChangeListener() {
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						try {
							DB.update("cart", "ctcount = ?", "ctno = ?",pp.panel.getCnt(), ctno);
						} catch (SQLException e) {
							e.printStackTrace();
						}
						pp.label_2.setText(String.format("%,d원", price*pp.panel.getCnt()));
						changePrice();
					}
				});
				prices.put(pp, rs.getInt("pprice"));
				ctnos.put(pp, ctno);
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0, 5+(149)*i));
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel.revalidate();
		panel.repaint();
	}
	int sum=0,cnt=0,discount=0;
	List<String> selCtnos;
	private void changePrice() {
		sum = cnt = 0;
		selCtnos = new ArrayList<>();
		for (var comp : panel_1.getComponents()) {
			if(comp instanceof D_패널) {
				D_패널 pp = (D_패널) comp;
				if(pp.checkBox.isSelected()) {
					sum += prices.get(pp) * pp.panel.getCnt();
					selCtnos.add(ctnos.get(pp)+"");
					cnt++;
				}
			}
		}
		label_7.setText(String.format("%,d원", sum));
		button_1.setText(String.format("총 %d개 상품 구매하러가기", cnt));
		label_6.setText(String.format("-%,d원", discount));
		label_5.setText(String.format("%,d원", sum-discount));
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(comboBox.getSelectedIndex()==0) return;
			double sale = sum * reward.get(comboBox.getSelectedIndex()-1);
			discount = (int) sale;
			changePrice();
		}
	}
	@Override
	public void updateForm() {
		load();
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try (var rs = DB.res("select * from cart join product using(pno) where ctno in("+String.join(",", selCtnos)+") and uno = ?", User.uno)) {
				while(rs.next()) {
					if(rs.getInt("pcount")<rs.getInt("ctcount")) {
						String msg = rs.getString("pname")+"재고 수량이 "+rs.getInt("pcount")+"개입니다. 수정하시겠습니까?";
						int r = JOptionPane.showConfirmDialog(rootPane, msg, "경고", 0, 3);
						if(r==0) {
							DB.update("cart", "ctcount = ?", "ctno = ?", rs.getInt("pcount"), rs.getInt("ctno"));
							System.out.println(rs.getInt("pcount")+" "+ rs.getInt("ctno"));
						}
						else {
							load();
							return;
						}
					}
				}
				showPage(new E_결제(selCtnos.stream().mapToInt(x->Integer.parseInt(x)).boxed().collect(Collectors.toList()), discount));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	class MyPanel extends D_패널{
		public MyPanel(ImageIcon img, String txt, int price, int cnt) {
			super(img, txt, price, cnt);
			
		}
		
	}
}
