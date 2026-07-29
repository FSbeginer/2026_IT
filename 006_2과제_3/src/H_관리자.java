import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JPopupMenu;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class H_관리자 extends BF {
	public JLabel label;
	public JScrollPane scrollPane;
	public JTable table;
	private DefaultTableModel model;
	public JPopupMenu popupMenu;
	public JMenuItem menuItem;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					H_관리자 frame = new H_관리자();
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
	public H_관리자() {
		setTitle("관리자");
		setBounds(100, 100, 527, 463);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("관리자 페이지");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setForeground(new Color(255, 255, 255));
		label.setOpaque(true);
		label.setBackground(new Color(34, 44, 215));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 511, 70);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 97, 487, 312);
		getContentPane().add(scrollPane);
		
		
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if(row==table.getSelectedRow())
					comp.setBackground(Color.ORANGE);
				else if(row%2==0)
					comp.setBackground(new Color(235,245,255));
				else
					comp.setBackground(Color.white);
				return comp;
			}
		};
		popupMenu = new JPopupMenu();
		addPopup(table, popupMenu);
		
		menuItem = new JMenuItem("삭제");
		menuItem.addActionListener(new MenuItemActionListener());
		menuItem.setForeground(new Color(255, 0, 0));
		popupMenu.add(menuItem);
		scrollPane.setViewportView(table);
		
		tabelSEt();
		load();
	}

	private void tabelSEt() {
		model = new DefaultTableModel("번호,이름,출발지,도착지,잔액".split(","),0);
		table.setModel(model);
		var h = table.getTableHeader();
		h.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel jl =  (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
				jl.setForeground(Color.white);
				jl.setOpaque(true);
				jl.setBackground(label.getBackground());
				jl.setHorizontalAlignment(0);
				jl.setFont(new Font("맑은 고딕",1,13));
				return jl;
			}
		});
		h.setPreferredSize(new Dimension(0,40));
		h.setReorderingAllowed(false);
		h.setResizingAllowed(false);
		table.setRowHeight(30);
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0,0));
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{setHorizontalAlignment(0);}});
	}

	List<Integer> rnos;
	private void load() {
		model.setRowCount(0);
		rnos = new ArrayList<Integer>();
		try (var rs = DB.res("select * from reservation r join user u using(uno) order by rdate desc, rno;")) {
			while(rs.next()) {
				var start = DB.select("select name from station where sno= ?", String.class, rs.getInt("start_sno"));
				var end = DB.select("select name from station where sno= ?", String.class, rs.getInt("end_sno"));
				model.addRow(getarr(model.getRowCount()+1,rs.getString("name"),start,end,String.format("%,d", rs.getInt("price"))));
				rnos.add(rs.getInt("rno"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[] getarr(Object...objects) {
		return objects;
	}
	
	private void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				int row = table.getSelectedRow();
				int mrow = table.rowAtPoint(e.getPoint());
				if (e.isPopupTrigger()&&mrow==row) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				int row = table.getSelectedRow();
				if(e.getClickCount()==2&&row>=0) {
					var rno = rnos.get(row);
					try {
						int uno = DB.select("select uno from reservation where  rno = ?", Integer.class, rno);
						showpage(new I_차트(uno));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}
		});
	}
	private class MenuItemActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			int row = table.getSelectedRow();
			if(row>=0) {
				var rno = rnos.get(row);
				try {
					DB.delete("reservation", "rno = ?", rno);
					load();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
		}
	}
}
