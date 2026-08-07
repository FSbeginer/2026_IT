import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.LineBorder;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
public class C_상품 extends BP {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JPanel panel_4;
	public JPanel panel_5;
	public JLabel label;
	public JComboBox comboBox;
	public JComboBox comboBox_1;
	public JLabel label_1;
	public JLabel label_2;
	public JTextField textField;
	public JButton button;

	/**
	 * Create the panel.
	 */
	public C_상품() {
		setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 933, 76);
		add(panel);
		panel.setLayout(null);
		
		panel_2 = new JPanel();
		panel_2.setBounds(0, 0, 730, 76);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(1, 0, 10, 0));
		
		panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(null);
		
		label = new JLabel("정렬조건");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label.setBounds(7, 11, 135, 21);
		panel_3.add(label);
		
		comboBox = new JComboBox();
		comboBox.addActionListener(new ComboBoxActionListener());
		comboBox.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"(없음)", "가격↑", "가격↓", "별점↑", "별점↓"}));
		comboBox.setBounds(8, 36, 210, 29);
		panel_3.add(comboBox);
		
		panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(null);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ComboBox_1ActionListener());
		comboBox_1.setFont(new Font("맑은 고딕", Font.PLAIN, 12));
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] {"전체"}));
		comboBox_1.setBounds(12, 36, 210, 29);
		panel_4.add(comboBox_1);
		
		label_1 = new JLabel("분류조건");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_1.setBounds(11, 11, 135, 21);
		panel_4.add(label_1);
		
		panel_5 = new JPanel();
		panel_2.add(panel_5);
		panel_5.setLayout(null);
		
		label_2 = new JLabel("검색어");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_2.setBounds(8, 11, 135, 21);
		panel_5.add(label_2);
		
		textField = new JTextField();
		textField.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		textField.setBounds(10, 35, 209, 30);
		panel_5.add(textField);
		textField.setColumns(10);
		
		button = new JButton("검색");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		button.setBounds(737, 5, 187, 62);
		panel.add(button);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(12, 98, 933, 393);
		add(scrollPane);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		addCate();
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	String where="",like = "", order ="";
	
	private void load() {
		panel_1.removeAll();
		try (var rs = DB.res("select *, avg(star) s, sum(`order`.quantity) cnt  from product left join `order` using(pno) left join review using(ono) where true "+where+like+" group by pno "+order)) {
			int w =(933-20-40)/4, h =(393*3/4), i = 0;
			while(rs.next()) {
				var pp = new C_패널(rs.getString("pname"), rs.getInt("price"), rs.getDouble("s"), rs.getInt("cnt"));
				pp.setLocation((w+10)*(i%4),10+(h+10)*(i/4));
				int pno = rs.getInt("pno");
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						((MainFrame)SwingUtilities.getWindowAncestor(comboBox)).showPage(new F_상품정보(pno));
					}
				});
				try {
					Helper.getImage(rs.getBytes("img"), 194, 175, x->pp.label.setIcon(new ImageIcon(x)));
				} catch (Exception e1) {
					e1.printStackTrace();
				}
				panel_1.add(pp);
				i++;
			}
			i+=3;
			panel_1.setPreferredSize(new Dimension(0, 10+(h+10)*(i/4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_1.revalidate();
		panel_1.repaint();
	}

	private void addCate() {
		try (var rs = DB.res("select * from category")) {
			while(rs.next()) {
				comboBox_1.addItem(rs.getString(2));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	boolean change = false;
	private class ComboBoxActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(change) return;
			switch (comboBox.getSelectedIndex()) {
			case 0:
				order = "";
				break;
			case 1:
				order = " order by price";
				break;
			case 2:
				order = " order by price desc";
				break;
			case 3:
				order = " order by s ";
				break;
			case 4:
				order = " order by s desc";
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
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			like = " and replace(pname,' ','') like '%"+textField.getText().replaceAll(" ", "")+"%'";
			load();
			if(panel_1.getComponentCount()==0) {
				msgErr("검색결과가 없습니다.");
				change =true;
				comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				change = false;
				like = where = order ="";
				textField.setText("");
				load();
			}
		}
	}
}
