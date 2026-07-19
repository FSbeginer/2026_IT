import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

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
		setBounds(100, 100, 593, 419);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("의사 페이지");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setForeground(new Color(57, 140, 215));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 553, 51);
		getContentPane().add(label);
		
		label_1 = new JLabel("ㅇㄴ");
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(427, 62, 120, 24);
		getContentPane().add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 96, 553, 274);
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
				if(column==3)return ImageIcon.class;
				return super.getColumnClass(column);
			}
		};
		addPopup(table, popupMenu);
		scrollPane.setViewportView(table);
		tableSet();
		try {
			label_1.setText(DB.select("select dname from doctor where dno = ?", String.class, User.uno)+"님 환영합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		load();
	}
	@Override
	public void updateForm() {
		load();
	}
	List<Integer> onos;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;
	private void load() {
		model.setRowCount(0);
		onos = new ArrayList<Integer>();
		try (var rs = DB.res("select * from orders join user using(uno) join category using(cno) where dno = ? order by orderdate desc, ordertime, ono;", User.uno)) {
			while(rs.next()) {
				var paydate = rs.getString("paydate").equals("0000-00-00")?"-":rs.getString("paydate");
				var visitdate = rs.getString("orderdate");
				var time = rs.getString("ordertime");
				var icon = getIcon("icon/"+rs.getString("cname")+".png",80,60);
				var uname = rs.getString("name");
				model.addRow(getArr(paydate, visitdate, time, icon, uname));
				onos.add(rs.getInt("ono"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[] getArr(Object...objects) {
		return objects;
	}

	private void tableSet() {
		model = new DefaultTableModel("payDate,visitDate,time,category,name".split(","),0);
		table.setModel(model);
		var head = table.getTableHeader();
		head.setPreferredSize(new Dimension(0, 40));
		head.setForeground(Color.white);
		head.setBackground(label.getForeground());
		head.setBorder(new LineBorder(Color.white));
		head.setReorderingAllowed(false);
		head.setResizingAllowed(false);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{setHorizontalAlignment(0);}});
		table.setRowHeight(70);
	}
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					targetrRow = table.rowAtPoint(e.getPoint());
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
						var date = DB.select("select orderdate from orders where ono = ?", Date.class, onos.get(table.rowAtPoint(e.getPoint()))).toLocalDate();
						if(LocalDate.now().isBefore(date)) {
							msgErr("예약 당일에 처리가능합니다.");
						}
						else {
							showPage(new J_치료기록작성및확인(onos.get(table.rowAtPoint(e.getPoint()))));
						}
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	int targetrRow;
	private class MenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int ono = onos.get(targetrRow);
			int r = JOptionPane.showConfirmDialog(null, "삭제하시겠습니까?", "질문",0,3);
			if(r==0) {
				try {
					DB.delete("orders", "ono = ?", ono);
					load();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
