import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class I_의사메인 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JScrollPane scrollPane;
	public JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_의사메인 frame = new I_의사메인();
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
	public I_의사메인() {
		setTitle("의사메인");
		setBounds(100, 100, 639, 483);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("의사 페이지");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setForeground(new Color(78, 132, 233));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 599, 48);
		getContentPane().add(label);

		label_1 = new JLabel("ㅁㄴㅇㅁㄴㅇ");
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(449, 68, 162, 15);
		getContentPane().add(label_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 97, 599, 337);
		getContentPane().add(scrollPane);

		popupMenu = new JPopupMenu();

		menuItem = new JMenuItem("삭제");
		menuItem.addActionListener(new MenuItemActionListener());
		popupMenu.add(menuItem);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 3)
					return ImageIcon.class;
				return super.getColumnClass(column);
			}
		};
		scrollPane.setViewportView(table);

		setmodel();
		load();

		addPopup(table, popupMenu);

		try {
			var name = DB.select("select dname from doctor where dno = ?", String.class, User.uno);
			label_1.setText(name + "님 환영합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	List<Integer> onos;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;

	private void load() {
		model.setRowCount(0);
		onos = new ArrayList<>();
		try (var rs = DB.res("select * from orders join category using(cno) join user using(uno) where dno = ?;",
				User.uno)) {
			while (rs.next()) {
				String paydate = rs.getString("paydate").equals("0000-00-00") ? "-" : rs.getString("paydate");
				model.addRow(new Object[] { paydate, rs.getString("orderdate"), rs.getString("ordertime"),
						getIcon("icon/" + rs.getString("cname") + ".png", 80, 40), rs.getString("name") });
				onos.add(rs.getInt("ono"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setmodel() {
		model = new DefaultTableModel("payDate,visitDate,time,category,name".split(","), 0);
		table.setModel(model);

		var h = table.getTableHeader();
		h.setReorderingAllowed(false);
		h.setResizingAllowed(false);
		h.setBorder(new LineBorder(Color.white));
		h.setForeground(Color.white);
		h.setBackground(label.getForeground());
		h.setPreferredSize(new Dimension(0, 40));
		table.setRowHeight(60);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			{
				setHorizontalAlignment(0);
			}
		});
	}

	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.rowAtPoint(e.getPoint());
				if (e.isPopupTrigger() && row == table.getSelectedRow()) {
					showMenu(e);
				}
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(e.getClickCount()==2) {
					try {
						var orderdate = DB.select("select orderdate from orders where ono = ?", LocalDate.class, onos.get(table.getSelectedRow()));
						if(orderdate.isAfter(LocalDate.now())) {
							msgErr("예약 당일에 처리가능합니다.");
						}
						else{
							showPage(new  J_치료기록작성및확인(onos.get(table.getSelectedRow())));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}

	private class MenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int r = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "질문", 0, 3);
			if (r == 0) {
				int ono = onos.get(table.getSelectedRow());
				try {
					DB.delete("report", "ono = ?", ono);
					DB.delete("orders", "ono = ?", ono);
					load();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
