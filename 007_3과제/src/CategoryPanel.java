import javax.swing.JPanel;
import javax.swing.JLabel;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CategoryPanel extends JPanel {
	public JPanel panel;
	public JLabel label;

	/**
	 * Create the panel.
	 */
	int cno;
	String name;
	public CategoryPanel(int cno, String name, List<DetailLabel> bundle) {
		this.cno = cno;
		this.name = name;
		addMouseListener(new ThisMouseListener());
		setSize(204, 36);
		setPreferredSize(getSize());
		setLayout(null);
		
		panel = new JPanel();
		panel.setBounds(31, 38, 173, 3);
		add(panel);
		panel.setLayout(null);
		
		label = new JLabel("▶ "+name);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setBounds(12, 5, 180, 22);
		add(label);
		
		load(bundle);
	}
	
	Dimension maxsize, minsize;
	
	private void load(List<DetailLabel> bundle) {
		try (var rs = DB.res("select * from detail where cno = ?", cno)) {
			int h = 17, i= 0;
			while(rs.next()) {
				var jl = new DetailLabel(rs.getInt(1),rs.getString("dname"));
				jl.setLocation(0, (h+7)*i);
				bundle.add(jl);
				panel.add(jl);
				i++;
			}
			panel.setSize(panel.getWidth(), (h+7)*i);
			minsize = getSize();
			maxsize = new Dimension(getWidth(), getHeight()+panel.getHeight());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	public boolean isExpanded = false;
	public void setExpanded(boolean isExpanded) {
		this.isExpanded = isExpanded;
		if(isExpanded) {
			setPreferredSize(maxsize);
			label.setText("▼ "+name);
		}
		else {
			setPreferredSize(minsize);
			label.setText("▶ "+name);
		}
	}
	private class ThisMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			setExpanded(!isExpanded);
		}
	}
}
class DetailLabel extends JLabel{
	int dno;
	boolean isSelected = false;
	
	public void setSelected(boolean isSelected) {
		this.isSelected = isSelected;
		if(isSelected)
			setForeground(new Color(255,100,100));
		else 
			setForeground(Color.gray);
	}
	
	public DetailLabel(int dno, String txt) {
		super(txt);
		this.dno = dno;
		setSize(new Dimension(173, 17));
		setForeground(Color.gray);
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				setSelected(!isSelected);
			}
		});
	}
}
