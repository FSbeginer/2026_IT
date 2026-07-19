import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
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
		setBounds(100, 100, 746, 508);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label_1 = new JLabel("충전하기→");
		label_1.addMouseListener(new Label_1MouseListener());
		
		label_2 = new JLabel("New label");
		label_2.setVisible(false);
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBackground(Color.BLACK);
		label_2.setOpaque(true);
		label_2.setForeground(Color.ORANGE);
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(531, 103, 165, 50);
		getContentPane().add(label_2);
		label_1.setFont(new Font("맑은 고딕", Font.PLAIN, 17));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setForeground(Color.ORANGE);
		label_1.setBounds(602, 12, 118, 39);
		getContentPane().add(label_1);
		
		label = new JLabel("마이페이지");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setOpaque(true);
		label.setBackground(new Color(71, 94, 218));
		label.setForeground(new Color(255, 255, 255));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 730, 79);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 91, 710, 366);
		getContentPane().add(scrollPane);
		
		table = new JTable() {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if(row%2==0)
					comp.setBackground(Color.white);
				else
					comp.setBackground(new Color(230,230, 250));
				return comp;
			}
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};
		scrollPane.setViewportView(table);
		
		settable();
		load();
	}

	int sum;
	public JLabel label_2;
	private void load() {
		try (var rs = DB.res("select * from reservation where uno = ? order by rdate, rno",User.uno)) {
			while(rs.next()) {
				var start = RouteService.stations.get(rs.getInt("start_sno")-1);
				var end = RouteService.stations.get(rs.getInt("end_sno")-1);
				var routeInfo = RouteService.findRoute(start, end);
				int time = (int) (Math.round(routeInfo.dist*0.1)/2.0*3);
				int price = routeInfo.cost * 500;
				var u = DB.getUser(User.uno);
				int age = LocalDate.now().getYear() - u.birth.getYear();
				if (LocalDate.now().isBefore(u.birth.plusYears(age))) {
					age--;
				}
				if (age >= 12 && age < 19)
					price = price / 2;
				else if (age >= 65)
					price = (int) (price * 0.8);
				var date = rs.getDate("rdate").toLocalDate();
				model.addRow(getArr(model.getRowCount()+1, start.name, end.name, time+"분",String.format("%,d원", price), date.toString()));
				sum += price;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[] getArr(Object...objects) {
		return objects;
	}

	private void settable() {
		model = new DefaultTableModel("번호,출발지,도착지,소요시간,결제금액,날짜".split(","), 0);
		var head = table.getTableHeader();
		head.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
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
		head.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int col = table.columnAtPoint(e.getPoint());
				if(col==4) {
					label_2.setText(String.format("합계: %,d원", sum));
					label_2.setLocation(SwingUtilities.convertPoint(head, e.getPoint(), getContentPane()));
					label_2.setVisible(true);
				}
			}
			@Override
			public void mouseExited(MouseEvent e) {
				label_2.setVisible(false);
			}
		});
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0,0));
		table.setRowHeight(40);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {{setHorizontalAlignment(0);}});
		table.setModel(model);
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new G_충전());
		}
	}
}
