import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class F_마이페이지 extends BF {
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
		setBounds(100, 100, 805, 545);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("마이페이지");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setBorder(new MatteBorder(0, 0, 2, 0, (Color) new Color(66, 120, 210)));
		label.setForeground(new Color(66, 120, 210));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 789, 78);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setBounds(687, 36, 102, 42);
		getContentPane().add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 767, 408);
		getContentPane().add(scrollPane);
		
		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp =super.prepareRenderer(renderer, row, column);
				if(row%2==0)
					comp.setBackground(Color.white);
				else
					comp.setBackground(new Color(230,240,250));
				return comp;
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				if(column==4)return ImageIcon.class;
				return super.getColumnClass(column);
			}
		};
		table.addMouseListener(new TableMouseListener());
		scrollPane.setViewportView(table);
		
		try {
			String name = DB.select("select name from user where uno = ?", String.class, User.uno);
			label_1.setText("<html>"+name+"님<br>환영합니다.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		tableSet();
		addRow();
	}
	@Override
	public void updateForm() {
		addRow();
	}

	List<Integer> onos;
	private void addRow() {
		onos= new ArrayList<Integer>();
		model.setRowCount(0);
		try (var rs = DB.res("select * from orders left join doctor d using(dno) join category c on d.cno = c.cno where uno = ? order by orderdate desc, ordertime, ono",User.uno)) {
			while(rs.next()) {
				int ono = rs.getInt("ono");
				var orderdate = rs.getString("orderdate");
				var paydate = rs.getString("paydate");
				model.addRow(getArr(orderdate,paydate,rs.getString("dname"),rs.getString("ordertime"),getIcon("icon/"+rs.getString("cname")+".png",80,60), "", getState(orderdate, paydate)));
				onos.add(ono);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private String getState(String txt, String txt2) {
		LocalDate orderdate = LocalDate.parse(txt);
		if(orderdate.equals(LocalDate.now())) return "-";
		else if(LocalDate.now().isBefore(orderdate)) return "변경";
		else if(LocalDate.now().isAfter(orderdate)&&txt2!=null) return "결제완료";
		else if(LocalDate.now().isAfter(orderdate)) return "진료완료";
		return null;
	}

	private Object[] getArr(Object...objects) {
		return objects;
	}

	private void tableSet() {
		model = new DefaultTableModel("예약일,결제일,의사,시간,진료항목,금액,비고".split(","),0);
		table.setModel(model);
		var head = table.getTableHeader();
		head.setPreferredSize(new Dimension(0, 40));
		head.setForeground(Color.white);
		head.setBackground(label.getForeground());
		head.setBorder(new LineBorder(Color.white));
		head.setReorderingAllowed(false);
		head.setResizingAllowed(false);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{setHorizontalAlignment(0);}});
		table.setIntercellSpacing(new Dimension(0,1));
		table.setShowVerticalLines(false);
		table.setRowHeight(70);
		table.setGridColor(new Color(210,220,250));
		int[] s = {80,80,60,50,100,80,70};
		for (int i = 0; i < s.length; i++) {
			table.getColumnModel().getColumn(i).setPreferredWidth(s[i]);
		}
	}

	private class TableMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			int row = table.rowAtPoint(e.getPoint());
			int col = table.columnAtPoint(e.getPoint());
			if(col==6&&row!=-1) {
				var value = table.getValueAt(row, col);
				if(value.equals("진료 완료")) {
					showPage(new G_결제(onos.get(row)));
				}
				else if(value.equals("변경")) {
					try {
						LocalDate changeDate = DB.select("select orderdate from orders where ono = ?", Date.class, onos.get(row)).toLocalDate();
						int dno = DB.select("select dno from orders where ono = ?", Integer.class, onos.get(row));
						showPage(new D_달력(dno, onos.get(row),changeDate));
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
				else if(value.equals("결제완료")) {
					// reports
				}
			}
		}
	}
}
