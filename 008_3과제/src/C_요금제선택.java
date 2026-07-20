import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.util.Arrays;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;

public class C_요금제선택 extends JDialog {

	String type ;
	private JScrollPane scrollPane;
	private JPanel panel;
	public C_요금제선택(String type) {
		this.type = type;
		setTitle("요금제 선택");
		setBounds(100, 100, 844, 391);
		getContentPane().setLayout(new BorderLayout());
		
		scrollPane = new JScrollPane();
		getContentPane().add(scrollPane, BorderLayout.CENTER);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		scrollPane.getHorizontalScrollBar().setUnitIncrement(30);
		
		load();
		setModal(true);
	}
	int rno = -1;
	private void load() {
		try (var rs = DB.res("SELECT * FROM smartdb.rateplan where service = ?;",type)) {
			int w = 236, h = 296, i = 0;
			while(rs.next()) {
				var pp = new C_TypePanel(rs.getString("rname"), String.format("%,d원 / 월", rs.getInt("price")), rs.getString("effect").replaceAll("\"", "").split(","));
				pp.setLocation(20+(w+20)*i, 20);
				int r = rs.getInt(1);
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						rno = r;
						dispose();
					}
				});
				panel.add(pp);
				i++;
			}
			panel.setPreferredSize(new Dimension(20+(w+20)*i, 0));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
