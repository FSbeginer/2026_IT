import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class 요금제선텍 extends JDialog {

	private JPanel contentPane;
	public JScrollPane scrollPane;
	public JPanel panel;

	/**
	 * Create the frame.
	 */
	String service;

	public 요금제선텍(String service) {
		this.service = service;
		setModal(true);
		setTitle("요금제 선택");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 660, 390);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 0, 644, 351);
		contentPane.add(scrollPane);

		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 20));
		
		load();
		scrollPane.getHorizontalScrollBar().setUnitIncrement(30);
		setLocationRelativeTo(null);
	}

	int selrno;
	String name;
	private void load() {
		try (var rs = DB.res("select * from rateplan where service = ?", service)) {
			int w = 251, h = 298, i = 0;
			while (rs.next()) {
				var pp =new 요금제패널(rs.getString("rname"), rs.getInt("price"), rs.getString("effect").replaceAll("\"||", "").split(","));
				int rno = rs.getInt("rno");
				String tq = rs.getString("rname");
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						selrno = rno;
						name = tq;
						dispose();
					}
				});
				panel.add(pp);
				i++;
			}
			panel.setPreferredSize(new Dimension(20+271*i,0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
