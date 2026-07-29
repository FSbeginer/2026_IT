import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseMotionAdapter;
import java.sql.SQLException;
import java.time.LocalDate;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class F_마이페이지 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JScrollPane scrollPane;
	public JTable table;
	private DefaultTableModel model;
	public JLabel label_2;

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
		setBounds(100, 100, 716, 501);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label_2 = new JLabel("New label");
		label_2.setVisible(false);
		label_2.setBackground(Color.BLACK);
		label_2.setForeground(Color.ORANGE);
		label_2.setOpaque(true);
		label_2.setHorizontalAlignment(SwingConstants.RIGHT);
		label_2.setBounds(10, 59, 176, 44);
		getContentPane().add(label_2);

		label_1 = new JLabel("충전하기 →");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setForeground(Color.ORANGE);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(602, 16, 87, 28);
		getContentPane().add(label_1);

		label = new JLabel("마이페이지");
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setOpaque(true);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		label.setForeground(Color.WHITE);
		label.setBackground(new Color(34, 44, 215));
		label.setBounds(0, 0, 700, 63);
		getContentPane().add(label);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(22, 84, 651, 370);
		getContentPane().add(scrollPane);

		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}

			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component comp = super.prepareRenderer(renderer, row, column);
				if (row % 2 == 0)
					comp.setBackground(new Color(235, 245, 255));
				else
					comp.setBackground(Color.white);
				return comp;
			}
		};
		scrollPane.setViewportView(table);

		tabelSEt();
		load();
	}

	private void load() {
		model.setRowCount(0);
		try (var rs = DB.res("select * from reservation where uno = ?", User.uno)) {
			int sum = 0;
			while (rs.next()) {
				var start = RouteService.stations.get(rs.getInt("start_sno")-1);
				var end= RouteService.stations.get(rs.getInt("end_sno")-1);
				var routeInfo = RouteService.getRouteInfo(start, end);
				int time = (int) (Math.round(routeInfo.dist*0.1)/2.0*3);
				int price = routeInfo.cost * 500;
				model.addRow(getarr(model.getRowCount()+1,start.name,end.name,time+"분",String.format("%,d원", price), rs.getString("rdate")));
				sum += price;
			}
			label_2.setText(String.format("합계: %,d원  ", sum));

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[] getarr(Object... objects) {
		return objects;
	}

	private void tabelSEt() {
		model = new DefaultTableModel("번호,출발지,도착지,소요시간,결제금액,날짜".split(","), 0);
		table.setModel(model);
		var h = table.getTableHeader();
		h.setDefaultRenderer(new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				JLabel jl = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);
				jl.setForeground(Color.white);
				jl.setOpaque(true);
				jl.setBackground(label.getBackground());
				jl.setHorizontalAlignment(0);
				jl.setFont(new Font("맑은 고딕", 1, 13));
				return jl;
			}
		});
		h.setPreferredSize(new Dimension(0, 40));
		h.setReorderingAllowed(false);
		h.setResizingAllowed(false);
		h.addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				var p = SwingUtilities.convertPoint(h, e.getPoint(), getContentPane());
				label_2.setLocation(p.x + 10, p.y + 10);
				int col = table.columnAtPoint(e.getPoint());
				if (col == 4) {
					label_2.setVisible(true);
				} else
					label_2.setVisible(false);
			}
		});
		table.setRowHeight(30);
		table.setShowGrid(false);
		table.setIntercellSpacing(new Dimension(0, 0));
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			{
				setHorizontalAlignment(0);
			}
		});
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showpage(new G_충전());
		}
	}
}
