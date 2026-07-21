import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.Icon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class E_마이페이지 extends BF {
	private JLabel label;
	private JLabel label_1;
	private JLabel label_2;
	private JLabel label_3;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JTextField textField_4;
	private JScrollPane scrollPane;
	private JTable table;
	private JButton button;
	private JButton button_1;
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
		setBounds(100, 100, 735, 566);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("My Page");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 27));
		label.setForeground(Color.DARK_GRAY);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(12, 22, 697, 75);
		getContentPane().add(label);
		
		label_1 = new JLabel("이름");
		label_1.setBounds(73, 107, 98, 34);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("아이디");
		label_2.setBounds(73, 151, 98, 34);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("생년월일");
		label_3.setBounds(73, 195, 98, 34);
		getContentPane().add(label_3);
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.setBounds(183, 111, 242, 27);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setColumns(10);
		textField_1.setBounds(183, 158, 242, 27);
		getContentPane().add(textField_1);
		
		textField_2 = new JTextField();
		textField_2.setColumns(10);
		textField_2.setBounds(183, 202, 68, 27);
		getContentPane().add(textField_2);
		
		textField_3 = new JTextField();
		textField_3.setColumns(10);
		textField_3.setBounds(263, 202, 68, 27);
		getContentPane().add(textField_3);
		
		textField_4 = new JTextField();
		textField_4.setColumns(10);
		textField_4.setBounds(343, 202, 68, 27);
		getContentPane().add(textField_4);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(58, 260, 607, 152);
		getContentPane().add(scrollPane);
		
		table = new JTable() {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
			@Override
			public Class<?> getColumnClass(int column) {
				if(column==0)
					return Icon.class;
				else
					return super.getColumnClass(column);
			}
		};
		scrollPane.setViewportView(table);
		
		button = new JButton("정보 수정");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0, 128, 0));
		button.setBounds(136, 457, 148, 34);
		getContentPane().add(button);
		
		button_1 = new JButton("리뷰작성");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(new Color(0, 128, 0));
		button_1.setBounds(433, 457, 148, 34);
		getContentPane().add(button_1);

		load();
		setTableMode();
		addRows();
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(20);
	}
	private void setTableMode() {
		model = new DefaultTableModel("이미지,기종,통신사,총가격,개통일".split(","),0);
		table.setModel(model);
		
		var header = table.getTableHeader();
		header.setReorderingAllowed(false);
		header.setResizingAllowed(false);
		header.setBackground(new Color(0,128,0));
		header.setForeground(Color.white);
		table.setRowHeight(120);
		table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer(){{setHorizontalAlignment(0);}});
	}

	@Override
	public void updateForm() {
		addRows();
	}
	
	List<Integer> pnos ;
	private void addRows() {
		model.setRowCount(0);
		pnos = new ArrayList<>();
		try (var rs = DB.res("select pno,p.pname,r.service, o.price, o.opening_date  from orders o join product p using(pno) join rateplan r using(rno) where uno = ?;",User.uno)) {
			while(rs.next()) {
				model.addRow(getArr(getIcon("기종/"+rs.getInt(1)+".jfif",120,120),rs.getString(2),rs.getString(3),String.format("%,d원", rs.getInt(4)),rs.getString(5)));
				pnos.add(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private Object[] getArr(Object...objects) {
		return objects;
	}

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
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try (var rs = DB.res("select * from user where uno = ?",User.uno)) {
				rs.next();
				var id = rs.getString("id");
				var birth = rs.getString("birth");
				var idInput = textField_1.getText();
				var birthIput = String.join("-", textField_2.getText(), textField_3.getText(), textField_4.getText());
				if(id.equals(idInput)&&birthIput.equals(birth)) {
					msgErr("수정한 내용이 없습니다.");
				}
				else {
					DB.update("user", "id=?,birth=?", "uno=?", id,birth,User.uno);
					msgInfo("수정이 완료되었습니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var row = table.getSelectedRow();
			if(row==-1) {
				msgErr("선택된 정보가 없습니다.");
				return;
			}
			showPage(new F_리뷰작성(pnos.get(row)));
		}
	}
}
