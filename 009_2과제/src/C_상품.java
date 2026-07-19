import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import javax.swing.JTextField;
public class C_상품 extends BP {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JButton button;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JPanel panel_4;
	public JLabel label;
	public JComboBox comboBox;
	public JLabel label_1;
	public JComboBox comboBox_1;
	public JLabel label_2;

	/**
	 * Create the panel.
	 */
	public C_상품() {
		setBackground(new Color(240, 240, 240));
		setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(192, 192, 192)));
		panel.setBounds(12, 14, 851, 72);
		add(panel);
		panel.setLayout(null);
		
		button = new JButton("검색");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(672, 12, 167, 46);
		panel.add(button);
		
		panel_1 = new JPanel();
		panel_1.setBounds(0, 0, 656, 72);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(0, 3, 10, 0));
		
		panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		
		label = new JLabel("정렬조건");
		label.setBounds(12, 14, 89, 15);
		panel_2.add(label);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ComboBoxActionListener());
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"(없음)", "가격↑", "가격↓", "별점↑", "별점↓"}));
		comboBox.setBounds(12, 33, 188, 25);
		panel_2.add(comboBox);
		
		panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(null);
		
		label_1 = new JLabel("분류조건");
		label_1.setBounds(12, 14, 89, 15);
		panel_3.add(label_1);
		
		comboBox_1 = new JComboBox();
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"전체"}));
		comboBox_1.addActionListener(new ComboBox_1ActionListener());
		comboBox_1.setBounds(12, 33, 188, 25);
		panel_3.add(comboBox_1);
		
		panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(null);
		
		label_2 = new JLabel("검색어");
		label_2.setBounds(12, 14, 89, 15);
		panel_4.add(label_2);
		
		textField = new JTextField();
		textField.setBounds(12, 35, 188, 23);
		panel_4.add(textField);
		textField.setColumns(10);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(12, 101, 851, 368);
		add(scrollPane);
		
		panel_5 = new JPanel();
		panel_5.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel_5);
		panel_5.setLayout(null);
		text = "상품";
		
		addcombo();
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
	}
	
	private void addcombo() {
		try (var rs = DB.res("select * from category")) {
			while(rs.next())
				comboBox_1.addItem(rs.getString(2));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	String where ="", order="order by pno",like =""; 
	public JPanel panel_5;
	public JTextField textField;
	private void load() {
		panel_5.removeAll();
		try (var rs = DB.res("select *, round(avg(star), 1)st, count(*) cnt from product p left join `order` using(pno) left join review using(ono) where true "+where+like+" group by pno "+order)) {
			int w = (811-20)/4, h = 368-50, i= 0;
			while(rs.next()) {
				var pp = new C_패널(null, rs.getString("pname"), rs.getInt("price"), rs.getDouble("st"), rs.getInt("cnt"));
				var data = rs.getBytes("img");
				int pno = rs.getInt("pno");
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						MainFrame.instance.showPage(new F_상품정보(pno));
					}
				});
				new Thread(new Runnable() {
					@Override
					public void run() {
						pp.label.setIcon(getIcon(data, 184, 193));
					}
				}).start();
				pp.setLocation(15+(w+10)*(i%4), (h+10)*(i/4));
				panel_5.add(pp);
				i++;
			}
			panel_5.setPreferredSize(new Dimension(0, (h+10)*((i+3)/4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_5.revalidate();
		panel_5.repaint();
	}
	private class ComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(change) return;
			switch (comboBox.getSelectedIndex()) {
			case 0:
				order = "order by pno";
				break;
			case 1:
				order = "order by price";
				break;
			case 2:
				order = "order by price desc";
				break;
			case 3:
				order = "order by st";
				break;
			case 4:
				order = "order by st desc";
				break;
			}
			load();
		}
	}
	private class ComboBox_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(change) return;
			if(comboBox_1.getSelectedIndex()==0) {
				where = "";
			}
			else {
				where = " and cno = "+comboBox_1.getSelectedIndex();
			}
			load();
		}
	}
	boolean change = false;
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			like = " and replace(pname, ' ', '') like '%"+textField.getText().replaceAll(" ", "")+"%'";
			load();
			if(panel_5.getComponentCount()==0) {
				msgErr("검색결과가 없습니다.");
				textField.setText("");
				change = true;
				comboBox.setSelectedIndex(0);
				change = false;
				comboBox_1.setSelectedIndex(0);
			}
		}
	}
}
