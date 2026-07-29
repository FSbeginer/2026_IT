import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.border.LineBorder;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionEvent;

public class C_상품 extends BP {
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JPanel panel_2;
	public JButton button;
	public JPanel panel_3;
	public JLabel label;
	public JComboBox comboBox;
	public JPanel panel_4;
	public JLabel label_1;
	public JComboBox comboBox_1;
	public JPanel panel_5;
	public JLabel label_2;
	public JTextField textField;

	/**
	 * Create the panel.
	 */
	public C_상품(MainFrame bf) {
		super(bf);
		setBackground(new Color(240, 240, 240));
		setLayout(null);

		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 10, 898, 81);
		add(panel);
		panel.setLayout(null);

		panel_2 = new JPanel();
		panel_2.setBounds(12, 10, 672, 61);
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(0, 3, 0, 0));

		panel_3 = new JPanel();
		panel_3.setBorder(null);
		panel_2.add(panel_3);
		panel_3.setLayout(null);

		label = new JLabel("정렬조건");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		label.setBounds(6, 3, 212, 22);
		panel_3.add(label);

		comboBox = new JComboBox();
		comboBox.addActionListener(new ComboBoxActionListener());
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "(없음)", "가격↑", "가격↓", "별점↑", "별점↓" }));
		comboBox.setBounds(9, 26, 205, 27);
		panel_3.add(comboBox);

		panel_4 = new JPanel();
		panel_4.setLayout(null);
		panel_4.setBorder(null);
		panel_2.add(panel_4);

		label_1 = new JLabel("분류조건");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		label_1.setBounds(6, 3, 212, 22);
		panel_4.add(label_1);

		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ComboBox_1ActionListener());
		comboBox_1.setModel(new DefaultComboBoxModel(new String[] { "전체" }));
		comboBox_1.setBounds(9, 26, 205, 27);
		panel_4.add(comboBox_1);

		panel_5 = new JPanel();
		panel_5.setLayout(null);
		panel_5.setBorder(null);
		panel_2.add(panel_5);

		label_2 = new JLabel("검색어");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 11));
		label_2.setBounds(6, 3, 212, 22);
		panel_5.add(label_2);

		textField = new JTextField();
		textField.setBounds(12, 28, 203, 24);
		panel_5.add(textField);
		textField.setColumns(10);

		button = new JButton("검색");
		button.addActionListener(new ButtonActionListener());
		button.setBounds(696, 10, 190, 61);
		button.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		panel.add(button);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(12, 109, 898, 357);
		add(scrollPane);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(240, 240, 240));
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);

		addCate();
		load();
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}

	private void addCate() {
		try {
			var rs = DB.res("select cname from category");
			while(rs.next()) {
				comboBox_1.addItem(rs.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		panel_1.removeAll();
		try (var rs = DB.res(
				"select product.*, sum(quantity) cnt, round(avg(review.star),1) star from product left join `order` left join review using(ono) using(pno) where true "
						+ where + like + " group by pno " + order)) {
			int w = (scrollPane.getWidth() - 41) / 4, h = 270, i = 0;
			while (rs.next()) {
				var pp = new C_패널(null, rs.getString("pname"), rs.getInt("price"), rs.getDouble("star"),
						rs.getInt("cnt"));
				Helper.getImageWorker(rs.getBytes("img"), w - 10, h - 100, x -> pp.label.setIcon(new ImageIcon(x)))
						.execute();
				pp.label.setPreferredSize(new Dimension(0, h - 100));
				pp.setSize(w, h);
				pp.setLocation((w + 7) * (i % 4), (h + 7) * (i / 4));
				int pno  =rs.getInt("pno");
				pp.addMouseListener(new MouseAdapter() {
					public void mouseClicked(java.awt.event.MouseEvent e) {
						bf.showPage(new F_상품정보(pno));
					};
				});
				panel_1.add(pp);
				i++;
			}
			i += 3;
			panel_1.setPreferredSize(new Dimension(0, (h + 7) * (i / 4)));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		panel_1.revalidate();
		panel_1.repaint();
	}

	String order = "", where = "", like = "";

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
				order = " order by star";
				break;
			case 4:
				order = " order by star desc";
				break;
			}
			load();
		}
	}

	private class ComboBox_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(change) return;
			if (comboBox_1.getSelectedIndex() == 1) {
				where = "";
			} else {
				where = " and cno = " + comboBox_1.getSelectedIndex();
			}
			load();
		}
	}

	boolean change = false;

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			like = " and replace(pname, ' ', '') like '%" + textField.getText().replaceAll(" ", "") + "%'";
			load();
			if (panel_1.getComponentCount() == 0) {
				msgErr("검색결과가 없습니다.");
				change = true;
				comboBox.setSelectedIndex(0);
				comboBox_1.setSelectedIndex(0);
				change = false;
				like = "";
				load();
			}
		}
	}
}
