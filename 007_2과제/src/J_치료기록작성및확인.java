import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.ListSelectionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class J_치료기록작성및확인 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					J_치료기록작성및확인 frame = new J_치료기록작성및확인(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param integer 
	 */
	int ono;
	public JPanel panel;
	public JPanel panel_1;
	public JLabel label;
	public JLabel label_1;
	public JPanel panel_2;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JLabel label_9;
	public JLabel label_10;
	public JTextArea textArea;
	public JLabel label_11;
	public JTextArea textArea_1;
	public JButton button;
	public JButton button_1;
	public JScrollPane scrollPane;
	public JList list;
	private DefaultListModel<ReportsOption> model;
	public J_치료기록작성및확인(int ono) {
		getContentPane().setBackground(new Color(177, 207, 237));
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(36, 36, 642, 514);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("ㅁ치료 기록 작성");
		label.setBounds(21, 17, 104, 17);
		panel.add(label);
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBackground(new Color(210, 210, 210));
		panel_2.setBounds(21, 44, 592, 84);
		panel.add(panel_2);
		panel_2.setLayout(null);
		
		label_2 = new JLabel("날짜");
		label_2.setBounds(12, 10, 39, 15);
		panel_2.add(label_2);
		
		label_3 = new JLabel("시간");
		label_3.setBounds(12, 59, 39, 15);
		panel_2.add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setBounds(200, 10, 83, 15);
		panel_2.add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setBounds(200, 59, 83, 15);
		panel_2.add(label_5);
		
		label_6 = new JLabel("환자");
		label_6.setBounds(367, 10, 39, 15);
		panel_2.add(label_6);
		
		label_7 = new JLabel("치료 유형");
		label_7.setBounds(367, 59, 62, 15);
		panel_2.add(label_7);
		
		label_8 = new JLabel("New label");
		label_8.setHorizontalAlignment(SwingConstants.RIGHT);
		label_8.setBounds(486, 10, 69, 15);
		panel_2.add(label_8);
		
		label_9 = new JLabel("New label");
		label_9.setHorizontalAlignment(SwingConstants.RIGHT);
		label_9.setBounds(486, 59, 69, 15);
		panel_2.add(label_9);
		
		label_10 = new JLabel("치료 내용");
		label_10.setBounds(21, 138, 57, 15);
		panel.add(label_10);
		
		textArea = new JTextArea();
		textArea.addKeyListener(new TextAreaKeyListener());
		textArea.setBorder(new LineBorder(new Color(0, 0, 0)));
		textArea.setBounds(21, 164, 592, 159);
		panel.add(textArea);
		
		label_11 = new JLabel("특이 사항");
		label_11.setBounds(21, 340, 57, 15);
		panel.add(label_11);
		
		textArea_1 = new JTextArea();
		textArea_1.addKeyListener(new TextArea_1KeyListener());
		textArea_1.setBounds(21, 365, 592, 102);
		panel.add(textArea_1);
		textArea_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		button = new JButton("저장");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(77, 153, 215));
		button.setForeground(new Color(255, 255, 255));
		button.setBounds(412, 477, 90, 27);
		panel.add(button);
		
		button_1 = new JButton("취소");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(204, 119, 106));
		button_1.setForeground(new Color(255, 255, 255));
		button_1.setBounds(523, 477, 90, 27);
		panel.add(button_1);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(701, 38, 239, 509);
		getContentPane().add(panel_1);
		panel_1.setLayout(null);
		
		label_1 = new JLabel("ㅁ등록된 치료 기록");
		label_1.setBounds(12, 10, 104, 17);
		panel_1.add(label_1);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(12, 43, 215, 444);
		panel_1.add(scrollPane);
		
		list = new JList();
		list.addMouseListener(new ListMouseListener());
		list.addListSelectionListener(new ListListSelectionListener());
		scrollPane.setViewportView(list);
		setTitle("치료 기록 작성및 확인");
		this.ono = ono;
		setBounds(100, 100, 974, 616);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		model = new DefaultListModel<ReportsOption>();
		list.setModel(model);
		
		uiLoad();
		load();
	}
	private void uiLoad() {
		try (var rs = DB.res("select * from orders join user using(uno) join category using(cno) where dno = ?;",User.uno)) {
			rs.next();
			label_4.setText(rs.getString("orderdate"));
			label_5.setText(rs.getString("ordertime"));
			label_8.setText(rs.getString("name"));
			label_9.setText(rs.getString("cname"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void load() {
		model.removeAllElements();
		try (var rs = DB.res("select * from report join category using(cno) join user using(uno) where dno =? and date = curdate()", User.uno)) {
			while(rs.next()) {
				ReportsOption op = new ReportsOption(String.format("[%s] %s", rs.getString("cname"), rs.getString("name")), rs.getInt("ono"), rs.getInt("rno"));
				model.addElement(op);
				if(op.ono==ono)
					list.setSelectedIndex(model.getSize()-1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
		}
	}
	private class TextAreaKeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if(textArea.getText().length()>250)
				textArea.setText(textArea.getText().substring(0,250));
		}
	}
	private class TextArea_1KeyListener extends KeyAdapter {
		@Override
		public void keyReleased(KeyEvent e) {
			if(textArea_1.getText().length()>500)
				textArea_1.setText(textArea_1.getText().substring(0,500));
		}
	}
	class ReportsOption {
		String txt;
		int ono, rno;
		public ReportsOption(String txt, int ono, int rno) {
			super();
			this.txt = txt;
			this.ono = ono;
			this.rno = rno;
		}
		@Override
		public String toString() {
			return txt;
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				var rs = DB.res("select * from orders where ono = ?",ono);
				rs.next();
				DB.insert("report", 0,rs.getInt("cno"),ono,User.uno,rs.getInt("uno"),LocalDate.now(),textArea.getText(),textArea_1.getText());
				load();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class ListListSelectionListener implements ListSelectionListener {
		public void valueChanged(ListSelectionEvent e) {
			System.out.println(1);
		}

	}
	private void load(ReportsOption op) {
		try (var rs = DB.res("select * from report  join user using(uno)  join category using(cno)  join  orders using(ono) where rno = ?", op.rno)) {
			rs.next();
			label_4.setText(rs.getString("orderdate"));
			label_5.setText(rs.getString("ordertime"));
			label_8.setText(rs.getString("name"));
			label_9.setText(rs.getString("cname"));
			textArea.setText(rs.getString("context_1"));
			textArea_1.setText(rs.getString("context_2"));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private class ListMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			load((ReportsOption)list.getSelectedValue());
		}
	}
}
