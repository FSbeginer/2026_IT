import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JDialog;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;

public class D_거주지선택 extends JDialog {
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label_1;
	public JLabel label_2;
	public JList<AreaOption> list;
	public JList<SubOption> list_1;
	public JScrollPane scrollPane;
	public JScrollPane scrollPane_1;
	private DefaultListModel<D_거주지선택.SubOption> model2;
	private DefaultListModel<D_거주지선택.AreaOption> model1;

	public D_거주지선택() {
		getContentPane().setBackground(new Color(240, 240, 240));
		getContentPane().setLayout(null);
		label = new JLabel("거주지 선택");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(97, 14, 242, 36);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBounds(12, 55, 198, 295);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("광역");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_1.setBounds(12, 14, 37, 25);
		panel.add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 46, 174, 235);
		panel.add(scrollPane);
		
		list = new JList<>();
		list.addListSelectionListener(new ListListSelectionListener());
		scrollPane.setViewportView(list);
		
		panel_1 = new JPanel();
		panel_1.setBounds(224, 55, 198, 295);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_2 = new JLabel("지역");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_2.setBounds(12, 14, 37, 25);
		panel_1.add(label_2);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 46, 174, 235);
		panel_1.add(scrollPane_1);
		
		list_1 = new JList();
		list_1.addListSelectionListener(new List_1ListSelectionListener());
		scrollPane_1.setViewportView(list_1);
		setModal(true);
		setIconImage(new ImageIcon("./datafiles/logo/logo.png").getImage());
		setTitle("거주지 선택");
		setBounds(100, 100, 450, 405);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		
		setList();
		list.setSelectedIndex(0);
	}
	
	private void setList() {
		model1 = new DefaultListModel<AreaOption>();
		model2 = new DefaultListModel<SubOption>();
		try (var rs = DB.res("select * from area")) {
			while(rs.next()) {
				model1.addElement(new AreaOption(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from sub_area")) {
			while(rs.next()) {
				model2.addElement(new SubOption(rs.getInt(1), rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		list.setModel(model1);
		list_1.setModel(model2);
	}

	class AreaOption {
		int ano;
		String aname;
		public AreaOption(int ano, String aname) {
			super();
			this.ano = ano;
			this.aname = aname;
		}
		@Override
		public String toString() {
			return aname;
		}
	}
	class SubOption {
		int sno;
		String sname;
		public SubOption(int sno, String sname) {
			super();
			this.sno = sno;
			this.sname = sname;
		}
		@Override
		public String toString() {
			return sname;
		}
	}
	private class ListListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			var s = list.getSelectedValue();
			area = s;
			try (var rs = DB.res("select * from sub_area where ano = ?", s.ano)) {
				model2.clear();
				while(rs.next()) {
					model2.addElement(new SubOption(rs.getInt(1), rs.getString(2)));
				}
			} catch (SQLException e2) {
				e2.printStackTrace();
			}
		}
	}
	AreaOption area;
	SubOption sub;
	private class List_1ListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			var s = list_1.getSelectedValue();
			sub = s;
			dispose();
		}
	}
}
