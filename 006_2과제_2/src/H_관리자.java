import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JPopupMenu;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class H_관리자 extends BF {
	public JLabel label;
	public JScrollPane scrollPane;
	public JTable table;
	private DefaultTableModel model;

	public static void main(String[] args) {
		new H_관리자().setVisible(true);
	}

	public H_관리자() {
		setTitle("관리자");
		setBounds(100, 100, 503, 508);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("관리자 페이지");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setOpaque(true);
		label.setBackground(new Color(71, 94, 218));
		label.setForeground(new Color(255, 255, 255));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 487, 79);
		getContentPane().add(label);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 467, 366);
		getContentPane().add(scrollPane);

		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (row % 2 == 0)
					comp.setBackground(Color.white);
				else
					comp.setBackground(new Color(225, 235, 255));
				if (targetRow == row) {
					comp.setBackground(Color.orange);
				}
				return comp;
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(table);

		popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);

		menuItem = new JMenuItem("삭제");
		menuItem.addActionListener(new MenuItemActionListener());
		menuItem.setForeground(Color.RED);
		popupMenu.add(menuItem);

		settable();
		load();
	}

	int sum;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;
	int targetRow = -1;
	List<Integer> rnos ;
	private void load() {
		model.setRowCount(0);
		rnos = new ArrayList<Integer>();
		try (var rs = DB.res("select * from reservation join user using(uno) order by rdate desc, rno desc;")) {
			while (rs.next()) {
				var start = RouteService.stations.get(rs.getInt("start_sno")-1);
				var end = RouteService.stations.get(rs.getInt("end_sno")-1);
				model.addRow(getArr(model.getRowCount()+1,rs.getString("name"),start.name,end.name,String.format("%,d",  rs.getInt("price"))));
				rnos.add(rs.getInt("rno"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[] getArr(Object... objects) {
		return objects;
	}

	private void settable() {
		model = new DefaultTableModel("번호,이름,출발지,도착지,잔액".split(","), 0);
		var head = table.getTableHeader();
		head.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				jl.setFont(new Font("맑은 고딕", 0, 15));
				jl.setHorizontalAlignment(0);
				jl.setForeground(Color.white);
				jl.setOpaque(true);
				jl.setBackground(new Color(71, 94, 218));
				jl.setPreferredSize(new Dimension(0, 50));
				return jl;
			}
		});
		head.setReorderingAllowed(false);
		head.setResizingAllowed(false);

		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setRowHeight(40);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			{
				setHorizontalAlignment(0);
				setFont(new Font("맑은 고딕",0,13));
			}
		});
		table.setModel(model);
	}

	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if(!e.isPopupTrigger()) targetRow = table.rowAtPoint(e.getPoint());
				if (e.isPopupTrigger()) {
					int current = table.rowAtPoint(e.getPoint());
					if(current==targetRow)
						showMenu(e);
				}
				repaint();
			}

			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				try {
					if(e.getClickCount()==2) {
						var uno = DB.select("select uno from reservation where rno = ?", Integer.TYPE, rnos.get(targetRow));
						showPage(new I_차트(uno));
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		});
	}
	private class MenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				DB.delete("reservation", "where rno = ?", rnos.get(targetRow));
				load();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
