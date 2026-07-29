import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.border.LineBorder;

import com.mysql.cj.protocol.a.NativeConstants.IntegerDataType;

import test.Origional;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.util.List;
import java.util.Map;

import javax.swing.JComboBox;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JPanel;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.sql.SQLException;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;

public class C_상세정보 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					C_상세정보 frame = new C_상세정보(1);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param pno 
	 */
	int pno;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JComboBox comboBox;
	public JLabel label_6;
	public JComboBox comboBox_1;
	public JLabel label_7;
	public JComboBox comboBox_2;
	public JLabel label_8;
	public JTextField textField;
	public JButton button;
	public JButton button_1;
	private JPanel jpDrawing;
	private List<Map<String, Object>> caps;
	private List<Map<String, Object>> items;
	private List<Map<String, Object>> installments;
	public C_상세정보(int pno) {
		setTitle("상세정보");
		this.pno = pno;
		setBounds(100, 100, 585, 502);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("");
		label.addMouseListener(new LabelMouseListener());
		label.addMouseMotionListener(new LabelMouseMotionListener());
		
		jpDrawing = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				Image src = ((ImageIcon)label.getIcon()).getImage();
				g2.drawImage(src, 3, 3, 154,154, mp.x,mp.y,mp.x+30,mp.y+30,this);
				g2.dispose();
			}
		};
		jpDrawing.setBackground(new Color(240, 240, 240));
		jpDrawing.setBorder(new LineBorder(new Color(0, 0, 0)));
		jpDrawing.setVisible(false);
		jpDrawing.setBounds(82, 300, 167, 155);
		getContentPane().add(jpDrawing);
		label.setBorder(new LineBorder(Color.GRAY));
		label.setBounds(24, 28, 211, 212);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label_1.setBounds(24, 250, 211, 31);
		getContentPane().add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label_2.setBounds(268, 28, 261, 39);
		getContentPane().add(label_2);
		
		label_3 = new JLabel("별점:");
		label_3.setBounds(268, 77, 43, 15);
		getContentPane().add(label_3);
		
		label_4 = new JLabel("★");
		label_4.addMouseListener(new Label_4MouseListener());
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_4.setForeground(Color.ORANGE);
		label_4.setBounds(340, 77, 138, 15);
		getContentPane().add(label_4);
		
		label_5 = new JLabel("용량");
		label_5.setBounds(268, 121, 57, 15);
		getContentPane().add(label_5);
		
		comboBox = new JComboBox();
		comboBox.setBounds(267, 139, 262, 31);
		getContentPane().add(comboBox);
		
		label_6 = new JLabel("통신사");
		label_6.setBounds(269, 192, 57, 15);
		getContentPane().add(label_6);
		
		comboBox_1 = new JComboBox();
		comboBox_1.addActionListener(new ComboBox_1ActionListener());
		comboBox_1.setBounds(268, 210, 262, 31);
		getContentPane().add(comboBox_1);
		
		label_7 = new JLabel("할부");
		label_7.setBounds(269, 261, 57, 15);
		getContentPane().add(label_7);
		
		comboBox_2 = new JComboBox();
		comboBox_2.setBounds(268, 279, 262, 31);
		getContentPane().add(comboBox_2);
		
		label_8 = new JLabel("요금제");
		label_8.setBounds(268, 320, 57, 15);
		getContentPane().add(label_8);
		
		textField = new JTextField();
		textField.setHorizontalAlignment(SwingConstants.CENTER);
		textField.setText("요금제 선택 안됨");
		textField.setBackground(new Color(255, 255, 255));
		textField.setFocusable(false);
		textField.setEditable(false);
		textField.setBounds(268, 345, 190, 31);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		button = new JButton("+");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(255, 255, 255));
		button.setBorder(new LineBorder(new Color(0, 128, 0), 3, true));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 22));
		button.setForeground(new Color(0, 128, 0));
		button.setBounds(470, 345, 62, 31);
		getContentPane().add(button);
		
		button_1 = new JButton("결제하러 가기");
		button_1.addActionListener(new Button_1ActionListener());
		button_1.setForeground(Color.WHITE);
		button_1.setBackground(new Color(0, 128, 0));
		button_1.setBounds(268, 396, 261, 43);
		getContentPane().add(button_1);
		
		
		try {
			load();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	private void load() throws Exception {
		String name = DB.select("select pname from product where pno = ?", String.class, pno);
		label_2.setText(name);
		label_1.setText(String.format("%,d원 / 월", Origional.getPrice(pno)/12));
		caps=Origional.getCapaties(pno);
		for (Map<String, Object> cap : caps) {
			comboBox.addItem(cap.get("value"));
		}
		items = Origional.getItems(pno);
		for (Map<String, Object> map : items) {
			comboBox_1.addItem(map.get("type"));
		}
		installments = Origional.getInstallments(pno);
		for (Map<String, Object> map : installments) {
			comboBox_2.addItem(map.get("month"));
		}
		label.setIcon(getIcon("기종/"+pno+".jfif",label.getWidth(),label.getHeight()));
		var star = DB.select("select round(avg(scope)) from star where pno = ? group by pno;", Integer.class, pno);
		label_4.setText("★".repeat(star)+"☆".repeat(5-star));
	}
	private class Button_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(rno==-1) {
				msgErr("요금제를 선택해주세요.");
				return;
			}
			int cprice = (int) caps.get(comboBox.getSelectedIndex()).get("price");
			int sprice = (int) items.get(comboBox_1.getSelectedIndex()).get("price");
			var info = new PayInfo(pno, rno, cprice, comboBox.getSelectedItem().toString(), comboBox_1.getSelectedItem().toString(), comboBox_2.getSelectedItem().toString(), sprice);
			showPage(new D_결제(info));
		}
	}
	int rno = -1;
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String type = (String) comboBox_1.getSelectedItem();
//			통신사 선택
			var d = new C_요금제선택(type);
			d.addWindowListener(new WindowAdapter() {
				@Override
				public void windowClosed(WindowEvent e) {
					if(d.rno != -1) {
						C_상세정보.this.rno = d.rno;
						try {
							String name = DB.select("select rname from rateplan where rno = ?", String.class, rno);
							textField.setText(name);
						} catch (SQLException e1) {
							e1.printStackTrace();
						}
					}
				}
			});
			d.setVisible(true);
		}
	}
	Point mp;
	private class LabelMouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			mp = e.getPoint();
			var loc = SwingUtilities.convertPoint(label, mp, getContentPane());
			jpDrawing.setLocation(loc.x+5,loc.y+5);
			jpDrawing.repaint();
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseEntered(MouseEvent e) {
			jpDrawing.setVisible(true);
		}
		@Override
		public void mouseExited(MouseEvent e) {
			jpDrawing.setVisible(false);
		}
	}
	private class ComboBox_1ActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			rno = -1;
			textField.setText("요금제 선택 안됨");
		}
	}
	private class Label_4MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			showPage(new G_리뷰(pno));
		}
	}
}
class PayInfo{
	int pno,rno,cprice, sprice; // 상품, 요금제, 용량가
	String capName,servieNmae, install;
	public PayInfo(int pno, int rno, int cprice, String capName, String servieNmae, String install, int sprice) {
		super();
		this.pno = pno;
		this.rno = rno;
		this.sprice = sprice;
		this.cprice = cprice;
		this.capName = capName;
		this.servieNmae = servieNmae;
		this.install = install;
	}
}
