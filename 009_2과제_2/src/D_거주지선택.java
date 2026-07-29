import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.sql.SQLException;

import javax.swing.DefaultListModel;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.JScrollPane;

public class D_거주지선택 extends JDialog {

	private JPanel contentPane;
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JList list;
	public JList list_1;
	public JLabel label_1;
	public JLabel label_2;
	private DefaultListModel<AreaOption> model;
	private DefaultListModel<SubAreaOption> model2;
	public JScrollPane scrollPane;
	public JScrollPane scrollPane_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_거주지선택 frame = new D_거주지선택();
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
	public D_거주지선택() {
		setIconImage(BF.getIcon("logo/logo.png").getImage());
		setTitle("거주지 선택");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 365);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		label = new JLabel("거주지 선택");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 10, 410, 44);
		contentPane.add(label);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel.setBounds(12, 64, 199, 251);
		contentPane.add(panel);
		panel.setLayout(null);
		
		label_1 = new JLabel("광역");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_1.setBounds(12, 12, 97, 15);
		panel.add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 39, 175, 202);
		panel.add(scrollPane);
		
		list = new JList();
		scrollPane.setViewportView(list);
		list.addListSelectionListener(new ListListSelectionListener());
		list.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		list.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		list.setSelectedIndex(0);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		panel_1.setBounds(223, 64, 199, 251);
		contentPane.add(panel_1);
		panel_1.setLayout(null);
		
		label_2 = new JLabel("지역");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setBounds(12, 10, 97, 15);
		panel_1.add(label_2);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(12, 43, 175, 198);
		panel_1.add(scrollPane_1);
		
		list_1 = new JList();
		list_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		scrollPane_1.setViewportView(list_1);
		list_1.addListSelectionListener(new List_1ListSelectionListener());
		list_1.setBorder(new LineBorder(Color.LIGHT_GRAY));
		
		setModal(true);
		
		model = new DefaultListModel<AreaOption>();
		try (var rs = DB.res("select * from area")) {
			while(rs.next()) {
				model.addElement(new AreaOption(rs.getInt(1),rs.getString(2)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		model2 = new DefaultListModel<SubAreaOption>();
		list.setModel(model);
		list_1.setModel(model2);
		list.setSelectedIndex(0);
	}

	int sno = -1;
	private class List_1ListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			var sub = (SubAreaOption)list_1.getSelectedValue();
			sno = sub.sno;
			dispose();
		}
	}
	private class ListListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			try (var rs = DB.res("select * from sub_area where ano = ?", ((AreaOption)list.getSelectedValue()).ano)) {
				model2.clear();
				while(rs.next()) {
					model2.addElement(new SubAreaOption(rs.getInt(1), rs.getString(2)));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class AreaOption {
	int ano;
	String name;
	public AreaOption(int ano, String name) {
		this.ano = ano;
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}
class SubAreaOption {
	int sno;
	String name;
	public SubAreaOption(int sno, String name) {
		this.sno = sno;
		this.name = name;
	}
	@Override
	public String toString() {
		return name;
	}
}

