import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;

public class L_상품수정 extends BF {

	private JPanel contentPane;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JLabel label_6;
	public JLabel label_7;
	public JLabel label_8;
	public JTextField textField;
	public JTextField textField_1;
	public JTextField textField_2;
	public JTextField textField_3;
	public JTextArea textArea;
	public JButton button;
	public JButton button_1;
	public JComboBox comboBox;
	public JComboBox comboBox_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					L_상품수정 frame = new L_상품수정(1);
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
	int pno;

	public L_상품수정(int pno) {
		this.pno = pno;
		setTitle("상품 수정");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 648);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(240, 240, 240));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(null);

		panel = new RoundPanel();
		panel.setBounds(19, 32, 392, 513);
		contentPane.add(panel);
		panel.setLayout(null);

		label = new JLabel("상품 수정");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(97, 12, 197, 34);
		panel.add(label);

		label_1 = new JLabel("이미지 선택");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setOpaque(true);
		label_1.setBorder(new LineBorder(new Color(230, 230, 230)));
		label_1.setHorizontalTextPosition(SwingConstants.CENTER);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(107, 61, 171, 155);
		panel.add(label_1);

		label_2 = new JLabel("상품명");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_2.setBounds(23, 221, 51, 23);
		panel.add(label_2);

		label_3 = new JLabel("카테고리");
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_3.setBounds(23, 256, 51, 23);
		panel.add(label_3);

		label_4 = new JLabel("상세분류");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_4.setBounds(23, 291, 51, 23);
		panel.add(label_4);

		label_5 = new JLabel("제조사");
		label_5.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_5.setBounds(23, 326, 51, 23);
		panel.add(label_5);

		label_6 = new JLabel("가격");
		label_6.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_6.setBounds(23, 361, 51, 23);
		panel.add(label_6);

		label_7 = new JLabel("재고");
		label_7.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_7.setBounds(23, 396, 51, 23);
		panel.add(label_7);

		label_8 = new JLabel("설명");
		label_8.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_8.setBounds(23, 431, 51, 23);
		panel.add(label_8);

		textField = new JTextField();
		textField.setBorder(new LineBorder(new Color(230, 230, 230)));
		textField.setBounds(117, 222, 205, 22);
		panel.add(textField);
		textField.setColumns(10);

		textField_1 = new JTextField();
		textField_1.setBorder(new LineBorder(new Color(230, 230, 230)));
		textField_1.setColumns(10);
		textField_1.setBounds(117, 327, 205, 22);
		panel.add(textField_1);

		textField_2 = new NumberText();
		textField_2.setBorder(new LineBorder(new Color(230, 230, 230)));
		textField_2.setColumns(10);
		textField_2.setBounds(117, 362, 205, 22);
		panel.add(textField_2);

		textField_3 = new NumberText();
		textField_3.setBorder(new LineBorder(new Color(230, 230, 230)));
		textField_3.setColumns(10);
		textField_3.setBounds(117, 397, 205, 22);
		panel.add(textField_3);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setBorder(new LineBorder(new Color(230, 230, 230)));
		textArea.setBounds(118, 432, 204, 48);
		panel.add(textArea);

		comboBox = new JComboBox();
		comboBox.setBackground(new Color(244, 247, 255));
		comboBox.setBounds(114, 256, 207, 24);
		panel.add(comboBox);

		comboBox_1 = new JComboBox();
		comboBox_1.setBackground(new Color(244, 247, 255));
		comboBox_1.setBounds(115, 289, 207, 24);
		panel.add(comboBox_1);

		button = new RoundButton("수정하기");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(103, 103, 237));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		button.setBounds(99, 558, 107, 33);
		contentPane.add(button);

		button_1 = new RoundButton("취소");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setBackground(new Color(230, 230, 230));
		button_1.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		button_1.setForeground(Color.BLACK);
		button_1.setBounds(219, 558, 107, 33);
		contentPane.add(button_1);

		load();
	}

	List<Category> cates = new ArrayList<>();
	Map<Category, List<Detail>> detailByCategory = new HashMap<>();

	private void load() {
		try (var rs = DB.res("select * from category")) {
			while (rs.next()) {
				var cate = new Category(rs.getInt(1), rs.getString(2));
				var detail = DB.res("select * from detail where cno = ?", rs.getInt(1));
				var list = new ArrayList<Detail>();
				while (detail.next())
					list.add(new Detail(detail.getInt(1), detail.getString(2)));
				cates.add(cate);
				detailByCategory.put(cate, list);
				comboBox.addItem(cate);
			}
			comboBox.addItem("직접입력");
			comboBox.addActionListener(e -> {
				comboBox_1.removeAllItems();
				var list = detailByCategory.get(comboBox.getSelectedItem());
				if (list != null)
					list.stream().forEach(comboBox_1::addItem);
				comboBox_1.addItem("직접입력");
			});
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try (var rs = DB.res("select * from product join detail using(dno) join category using(cno) where pno = ?",
				pno)) {
			rs.next();
			label_1.setIcon(getIcon("product/" + pno + ".png", 150, 150));
			label_1.setText("");
			textField.setText(rs.getString("pname"));
			textField_1.setText(rs.getString("pcompany"));
			textField_2.setText(rs.getString("pprice"));
			textField_3.setText(rs.getString("pcount"));
			textArea.setText(rs.getString("pcontent"));
			String cate = rs.getString("cname");
			String detail = rs.getString("dname");
			comboBox.setSelectedItem(cates.stream().filter(x->x.cname.equals(cate)).findAny().get());
			comboBox_1.setSelectedItem(detailByCategory.get(comboBox.getSelectedItem()).stream().filter(x->x.dname.equals(detail)).findAny().get());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			dispose();
			previous();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var name = textField.getText();
			var company = textField_1.getText();
			int price = Integer.parseInt(textField_2.getText());
			int count = Integer.parseInt(textField_3.getText());
			var content = textArea.getText();
			
			var cate = comboBox.getSelectedItem();
			var detail = comboBox_1.getSelectedItem();
		}
	}
	
	private File imgFile;
	private class Label_1MouseListener extends MouseAdapter {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()==2) {
				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(false);
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileFilter(new FileNameExtensionFilter("jpg, png, gif Image", "jpg", "png", "gif"));
				if(jfc.showOpenDialog(null)==jfc.APPROVE_OPTION) {
					try {
						imgFile = jfc.getSelectedFile();
						label_1.setIcon(new ImageIcon(ImageIO.read(imgFile).getScaledInstance(label_1.getWidth(), label_1.getHeight(), 4)));
						label_1.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
}

class NumberText extends JTextField {
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		var txt = getText();
		txt = txt.replaceAll("\\D", "");
		if(txt.isBlank()) {
			setText("");
			return;
		}
		if(txt.length()>10)
			txt = txt.substring(0,10);
		setText(txt);
	}
}

class Category {
	int cno;
	String cname;

	public Category(int cno, String cname) {
		this.cno = cno;
		this.cname = cname;
	}

	@Override
	public String toString() {
		return cname;
	}
}

class Detail {
	int dno;
	String dname;

	public Detail(int dno, String dname) {
		this.dno = dno;
		this.dname = dname;
	}

	@Override
	public String toString() {
		return dname;
	}
}