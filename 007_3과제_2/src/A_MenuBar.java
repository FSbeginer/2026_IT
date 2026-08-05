import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class A_MenuBar extends JPanel {
	public static List<JLabel> jls = new ArrayList<JLabel>();
	public JLabel label;
	public JPanel panel;

	/**
	 * Create the panel.
	 */
	int cno;
	String name;
	public A_MenuBar(int cno, String name) {
		this.cno = cno;
		this.name = name;
		setSize(185, 40);
		setPreferredSize(new Dimension(185, 40));
		setLayout(null);

		label = new JLabel("▶ "+name);
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label.setBounds(0, 0, 191, 40);
		add(label);

		panel = new JPanel();
		panel.setBounds(20, 40, 168, 10);
		add(panel);

		load();
	}

	private void load() {
		try (var rs = DB.res("select * from detail where cno = ?",cno)) {
			int w = 160, h = 20, i = 0;
			while(rs.next()) {
				var jl = new JLabel(rs.getString(2));
				jl.setForeground(Color.gray);
				jl.setPreferredSize(new Dimension(w,h));
				jl.setName(rs.getString(1));
				jls.add(jl);
				panel.add(jl);
				i++;
			}
			panel.setSize(168,(h+5)*i);
			panel.setPreferredSize(panel.getSize());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	boolean expand = false;
	
	public void setExpand(boolean expand) {
		this.expand = expand;
		if(expand) {
			setPreferredSize(new Dimension(185, 40+panel.getPreferredSize().height));
			label.setText("▼ "+name);
		}else {
			setPreferredSize(new Dimension(185, 40));
			label.setText("▶ "+name);
		}
	}
	
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setExpand(!expand);
		}
	}
}
