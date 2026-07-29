import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;

import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class F_마이페이지 extends BF {
	public JLabel label;
	public JPanel panel;
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
					F_마이페이지 frame = new F_마이페이지();
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
	public F_마이페이지() {
		setTitle("마이페이지");
		setBounds(100, 100, 785, 559);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("마이페이지");
		label.setForeground(new Color(78, 132, 233));
		label.setFont(new Font("굴림", Font.BOLD, 29));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(234, 10, 301, 51);
		getContentPane().add(label);

		panel = new JPanel();
		panel.setBackground(new Color(78, 132, 233));
		panel.setBounds(0, 66, 769, 3);
		getContentPane().add(panel);

		label_1 = new JLabel("New label");
		label_1.setBounds(654, 32, 103, 29);
		getContentPane().add(label_1);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 71, 747, 426);
		getContentPane().add(scrollPane);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Class<?> getColumnClass(int column) {
				if (column == 4)
					return ImageIcon.class;
				return super.getColumnClass(column);
			}
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if(row%2==0)
					comp.setBackground(Color.white);
				else
					comp.setBackground(new Color(240,245,255));
				return comp;
			}
		};
		table.addMouseListener(new TableMouseListener());
		scrollPane.setViewportView(table);

		setmodel();

		load();
		
		try {
			var name = DB.select("select name from user where uno = ?", String.class, User.uno);
			label_1.setText(String.format("<html>%s님<br>환영합니다.",name));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	@Override
	public void updateForm() {
		load();
	}
	List<Integer> onos;
	private void load() {
		model.setRowCount(0);
		onos = new ArrayList<Integer>();
		try (var rs = DB.res("select * from orders o join category c using(cno) join doctor d using(dno)  where uno = ? order by orderdate desc, ordertime;", User.uno)) {
			while (rs.next()) {
				var orderdate = rs.getDate("orderdate").toLocalDate();
				String txt = "변경";
				if (LocalDate.now().equals(orderdate))
					txt = "-";
				if (LocalDate.now().isAfter(orderdate) && rs.getString("paydate").equals("0000-00-00"))
					txt = "진료완료";
				else if (LocalDate.now().isAfter(orderdate) && !rs.getString("paydate").equals("0000-00-00"))
					txt = "결제완료";
				
				String price = "??";
				if(LocalDate.now().isBefore(orderdate))
					price = "";
				onos.add(rs.getInt("ono"));
				model.addRow(new Object[] { orderdate.toString(), rs.getString("paydate").equals("0000-00-00")?"-":rs.getString("paydate"),  rs.getString("dname"), rs.getString("ordertime"), getIcon("icon/"+rs.getString("cname")+".png", 80,50), price, txt});
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setmodel() {
		model = new DefaultTableModel("예약일,결제일,의사,시간,진료항목,금액,비고".split(","), 0);
		table.setModel(model);

		var h = table.getTableHeader();
		h.setReorderingAllowed(false);
		h.setResizingAllowed(false);
		h.setBorder(new LineBorder(Color.white));
		h.setForeground(Color.white);
		h.setBackground(label.getForeground());
		h.setPreferredSize(new Dimension(0, 40));
		table.setRowHeight(80);
		table.setShowVerticalLines(false);
		table.setGridColor(Color.LIGHT_GRAY);
		table.setIntercellSpacing(new Dimension(0,1));
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			{
				setHorizontalAlignment(0);
			}
		});
	}

	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.rowAtPoint(e.getPoint());
			int col = table.columnAtPoint(e.getPoint());
			if(col==6) {
				String value = table.getValueAt(row, col).toString();
				if(value.equals("변경")) {
					try {	
						var rs = DB.res("select * from orders where ono = ?",onos.get(row));
						rs.next();
						showPage(new D_달력(rs.getInt("dno"), rs.getInt("cno"), rs.getInt("ono"),rs.getDate("orderdate").toLocalDate()));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if(value.equals("진료 완료")) {
					showPage(new G_결제(onos.get(row)));
				}
				else if(value.equals("결제 완료")) {
					new JDialog().setVisible(true);
				}
			}
		}
	}
}
