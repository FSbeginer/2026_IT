import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JTable;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.awt.event.ActionEvent;

public class E_마이페이지 extends BF {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JTextField textField_4;
	public JScrollPane scrollPane;
	public JButton button;
	public JButton button_1;
	public JTable table;
	private DefaultTableModel model;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					E_마이페이지 frame = new E_마이페이지();
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
	public E_마이페이지() {
		setTitle("마이페이지");
		setBounds(100, 100, 623, 519);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("MyPage");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(155, 10, 296, 53);
		getContentPane().add(label);
		
		label_1 = new JLabel("이름");
		label_1.setBounds(42, 74, 61, 32);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("아이디");
		label_2.setBounds(42, 123, 61, 32);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("생년월일");
		label_3.setBounds(42, 178, 61, 32);
		getContentPane().add(label_3);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(115, 73, 287, 32);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(115, 129, 287, 32);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setBounds(115, 184, 110, 26);
		getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(237, 184, 75, 26);
		getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(327, 184, 75, 26);
		getContentPane().add(textField_4);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(42, 234, 518, 191);
		getContentPane().add(scrollPane);
		
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				if(column==0)
					return ImageIcon.class;
				return super.getColumnClass(column);
			}
		};
		scrollPane.setViewportView(table);
		
		button = new JButton("정보 수정");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(139, 435, 121, 35);
		getContentPane().add(button);
		
		button_1 = new JButton("리뷰작성");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(0, 128, 0));
		button_1.setForeground(Color.WHITE);
		button_1.setBounds(328, 435, 121, 35);
		getContentPane().add(button_1);
		
		setMOdel();
		load();
	}

	private void setMOdel() {
		model = new DefaultTableModel("이미지,기종,통신사,총가격,개통일".split(","), 0);
		table.setModel(model);
		
		var h=  table.getTableHeader();
		h.setReorderingAllowed(false);
		h.setResizingAllowed(false);
		h.setBackground(new Color(0,128,0));
		h.setForeground(Color.white);
		table.setRowHeight(90);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){{setHorizontalAlignment(0);}});
	}
	
	List<Integer> pnos = new ArrayList<Integer>();
	private void load() {
		try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
			rs.next();
			textField.setText(rs.getString("name"));
			textField_1.setText(rs.getString("id"));
			var birth = rs.getString("birth").split("-");
			textField_2.setText(birth[0]);
			textField_3.setText(birth[1]);
			textField_4.setText(birth[2]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select *, orders.price tot from orders join product using(pno) join rateplan using(rno) where uno = ?",User.uno)) {
			while(rs.next()) {
				model.addRow(new Object[] {getIcon("기종/"+rs.getInt("pno")+".jfif",90,90), 
						rs.getString("pname"),rs.getString("service"),String.format("%,d원", rs.getInt("tot")),rs.getString("opening_date")});
				pnos.add(rs.getInt("pno"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
				rs.next();
				var id = textField_1.getText();
				var birth = String.join("-", textField_2.getText(),textField_3.getText(),textField_4.getText());
				if(rs.getString("id").equals(id)&&rs.getString("brith").equals(birth)) {
					msgErr("수정할 내용이 없습니다.");
					return;
				}
				DB.update("user", "birth = ?, id = ?", "uno = ?", birth, id, User.uno);
				msgInfo("수정이 완료되었습니다.");
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(table.getSelectedRow()<0) {
				msgErr("선택된 정보가 없습니다.");
				return;
			}
			int pno = pnos.get(table.getSelectedRow());
			try {
				boolean write = DB.select("select 1 from star where pno = ?", Boolean.class, pno);
				if(write) {
					msgErr("리뷰가 작성되어있습니다.");
					return;
				}
				showPage(new F_리뷰작성(pno));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
