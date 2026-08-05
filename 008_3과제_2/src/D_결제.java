import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JWindow;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class D_결제 extends BF {

	/**
	 * Create the frame.
	 * 
	 * @param payInfo
	 */
	PayInfo info;
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
	public JLabel label_9;
	public JLabel label_10;
	public JLabel label_11;
	public JLabel label_12;
	public JLabel label_13;
	public JLabel label_14;
	public JLabel label_15;
	public JPanel panel_1;
	public JPanel panel_2;
	public JPanel panel_3;
	public JButton button;
	public JLabel label_16;
	public JLabel label_17;
	public JLabel label_19;
	public JLabel label_20;
	public JLabel label_21;
	public JLabel label_22;
	public JLabel label_23;
	public JLabel label_24;
	public JLabel label_26;
	public JLabel label_27;
	public JLabel label_29;
	public JLabel label_30;
	public JLabel label_31;

	public D_결제(PayInfo payInfo) {
		getContentPane().setBackground(new Color(243, 252, 245));
		getContentPane().setLayout(null);

		panel = new JPanel();
		panel.setBounds(12, 64, 397, 373);
		getContentPane().add(panel);
		panel.setLayout(null);

		label_2 = new JLabel("▶ 기기 정보");
		label_2.setForeground(new Color(0, 128, 0));
		label_2.setFont(new Font("굴림", Font.BOLD, 14));
		label_2.setBounds(11, 9, 194, 24);
		panel.add(label_2);

		label_3 = new JLabel("기종");
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(11, 36, 142, 17);
		panel.add(label_3);

		label_4 = new JLabel("선택 용량");
		label_4.setForeground(Color.GRAY);
		label_4.setBounds(11, 61, 142, 17);
		panel.add(label_4);

		label_5 = new JLabel("통신사");
		label_5.setForeground(Color.GRAY);
		label_5.setBounds(11, 85, 142, 17);
		panel.add(label_5);

		label_6 = new JLabel("할부 기간");
		label_6.setForeground(Color.GRAY);
		label_6.setBounds(11, 110, 142, 17);
		panel.add(label_6);

		label_7 = new JLabel("개통일");
		label_7.setForeground(Color.GRAY);
		label_7.setBounds(11, 136, 142, 17);
		panel.add(label_7);

		label_8 = new JLabel("약정 종료일");
		label_8.setForeground(Color.GRAY);
		label_8.setBounds(11, 159, 142, 17);
		panel.add(label_8);

		label_9 = new JLabel("▶ 요금제 정보");
		label_9.setForeground(new Color(0, 128, 0));
		label_9.setFont(new Font("굴림", Font.BOLD, 14));
		label_9.setBounds(11, 190, 194, 24);
		panel.add(label_9);

		label_10 = new JLabel("선택 요금제");
		label_10.setForeground(Color.GRAY);
		label_10.setBounds(11, 213, 142, 17);
		panel.add(label_10);

		label_11 = new JLabel("요금제 금액");
		label_11.setForeground(Color.GRAY);
		label_11.setBounds(11, 239, 142, 17);
		panel.add(label_11);

		label_12 = new JLabel("▶ 기기 정보");
		label_12.setForeground(new Color(0, 128, 0));
		label_12.setFont(new Font("굴림", Font.BOLD, 14));
		label_12.setBounds(11, 264, 194, 24);
		panel.add(label_12);

		label_13 = new JLabel("용량 가격");
		label_13.setForeground(Color.GRAY);
		label_13.setBounds(11, 292, 142, 17);
		panel.add(label_13);

		label_14 = new JLabel("통신사 가격");
		label_14.setForeground(Color.GRAY);
		label_14.setBounds(11, 317, 142, 17);
		panel.add(label_14);

		label_15 = new JLabel("단말기 총 출고가");
		label_15.setFont(new Font("굴림", Font.BOLD, 12));
		label_15.setForeground(new Color(0, 128, 0));
		label_15.setBounds(11, 346, 142, 17);
		panel.add(label_15);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(180, 226, 182));
		panel_1.setBounds(11, 180, 365, 1);
		panel.add(panel_1);

		panel_2 = new JPanel();
		panel_2.setBackground(new Color(180, 226, 182));
		panel_2.setBounds(13, 256, 365, 1);
		panel.add(panel_2);

		label_19 = new JLabel("기종");
		label_19.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_19.setHorizontalAlignment(SwingConstants.RIGHT);
		label_19.setForeground(Color.BLACK);
		label_19.setBounds(244, 31, 142, 17);
		panel.add(label_19);

		label_20 = new JLabel("선택 용량");
		label_20.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_20.setHorizontalAlignment(SwingConstants.RIGHT);
		label_20.setForeground(Color.BLACK);
		label_20.setBounds(244, 56, 142, 17);
		panel.add(label_20);

		label_21 = new JLabel("통신사");
		label_21.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_21.setHorizontalAlignment(SwingConstants.RIGHT);
		label_21.setForeground(Color.BLACK);
		label_21.setBounds(244, 80, 142, 17);
		panel.add(label_21);

		label_22 = new JLabel("할부 기간");
		label_22.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_22.setHorizontalAlignment(SwingConstants.RIGHT);
		label_22.setForeground(Color.BLACK);
		label_22.setBounds(244, 105, 142, 17);
		panel.add(label_22);

		label_23 = new JLabel("개통일");
		label_23.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_23.setHorizontalAlignment(SwingConstants.RIGHT);
		label_23.setForeground(Color.BLACK);
		label_23.setBounds(244, 131, 142, 17);
		panel.add(label_23);

		label_24 = new JLabel("약정 종료일");
		label_24.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_24.setHorizontalAlignment(SwingConstants.RIGHT);
		label_24.setForeground(Color.BLACK);
		label_24.setBounds(244, 154, 142, 17);
		panel.add(label_24);

		label_26 = new JLabel("선택 요금제");
		label_26.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_26.setHorizontalAlignment(SwingConstants.RIGHT);
		label_26.setForeground(Color.BLACK);
		label_26.setBounds(244, 208, 142, 17);
		panel.add(label_26);

		label_27 = new JLabel("요금제 내역");
		label_27.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_27.setHorizontalAlignment(SwingConstants.RIGHT);
		label_27.setForeground(Color.BLACK);
		label_27.setBounds(244, 234, 142, 17);
		panel.add(label_27);

		label_29 = new JLabel("용량 가격");
		label_29.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_29.setHorizontalAlignment(SwingConstants.RIGHT);
		label_29.setForeground(Color.BLACK);
		label_29.setBounds(244, 287, 142, 17);
		panel.add(label_29);

		label_30 = new JLabel("통신사 가격");
		label_30.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_30.setHorizontalAlignment(SwingConstants.RIGHT);
		label_30.setForeground(Color.BLACK);
		label_30.setBounds(244, 312, 142, 17);
		panel.add(label_30);

		label_31 = new JLabel("단말기 총 출고가");
		label_31.setHorizontalAlignment(SwingConstants.RIGHT);
		label_31.setForeground(new Color(0, 128, 0));
		label_31.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		label_31.setBounds(244, 341, 142, 17);
		panel.add(label_31);

		label = new JLabel("결제 상세 내역");
		label.setFont(new Font("굴림", Font.BOLD, 14));
		label.setForeground(new Color(0, 128, 0));
		label.setBounds(12, 10, 194, 24);
		getContentPane().add(label);

		label_1 = new JLabel("New label");
		label_1.setForeground(Color.GRAY);
		label_1.setBounds(14, 38, 158, 18);
		getContentPane().add(label_1);

		panel_3 = new JPanel();
		panel_3.setBackground(new Color(0, 128, 0));
		panel_3.setBounds(12, 447, 397, 72);
		getContentPane().add(panel_3);
		panel_3.setLayout(null);

		label_16 = new JLabel("월 납부금 (단말 할부 + 요금제)");
		label_16.setForeground(Color.WHITE);
		label_16.setBounds(12, 10, 243, 16);
		panel_3.add(label_16);

		label_17 = new JLabel("New label");
		label_17.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_17.setForeground(Color.WHITE);
		label_17.setBounds(13, 36, 276, 31);
		panel_3.add(label_17);

		button = new JButton("결제하기");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(Color.WHITE);
		button.setBackground(new Color(0, 128, 0));
		button.setBounds(12, 529, 397, 43);
		getContentPane().add(button);
		setTitle("결제");
		info = payInfo;
		setBounds(100, 100, 437, 621);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		load();
	}

	int ratePlanPrice;
	JWindow balloon;

	private void load() {
		label_19.setText(info.pname);
		label_20.setText(info.cap.value.replaceAll("\\D", ""));
		label_21.setText(info.item.type);
		label_22.setText(info.installment.month + "");
		label_23.setText(LocalDate.now() + "");
		label_24.setText(LocalDate.now().plusMonths(info.installment.month).toString());
		try (var rs = DB.res("select * from rateplan where rno = ?", info.rtno)) {
			rs.next();
			label_26.setText(rs.getString("rname"));
			ratePlanPrice = rs.getInt("price");
			label_27.setText(String.format("%,d원 / 월", ratePlanPrice));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		label_29.setText(String.format("%,d원", info.cap.price));
		label_30.setText(String.format("%,d원", info.item.price));
		label_31.setText(String.format("%,d원", info.item.price + info.cap.price));
		int tot = info.item.price + info.cap.price;
		label_17.setText(String.format("%,d원 / 월", tot));
		label_1.setText(LocalDate.now() + " 기준");
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var rand = new Random();
			String txt = "";
			for (int i = 0; i < 6; i++) {
				txt += (char) (rand.nextInt(26) + 'A');
			}
			showPasswordBalloon(txt);
			var f = new Passworad(txt, () -> {
				try {
					DB.insert("orders", 0, info.item.price, info.cap.price,
							info.item.price + info.cap.price + ratePlanPrice,
							(info.item.price + info.cap.price + ratePlanPrice) / info.installment.month,
							LocalDate.now(), User.uno, info.pno, info.rtno);
					msgInfo("결제 완료");
					dispose();
					while (!prev.isEmpty()) {
						var p = prev.pop();
						if (p instanceof A_메인) {
							p.updateForm();
							p.setVisible(true);
							break;
						} else {
							p.dispose();
						}
					}
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			});
			f.setVisible(true);
		}
	}

	private void showPasswordBalloon(String pass) {
		if (balloon != null)
			balloon.dispose();

		balloon = new JWindow();
		balloon.setAlwaysOnTop(true);
		balloon.setSize(320, 86);

		JPanel bg = new JPanel(null);
		bg.setBackground(new Color(43, 43, 43));
		balloon.setContentPane(bg);

		JPanel line = new JPanel();
		line.setBackground(new Color(76, 175, 80));
		line.setBounds(0, 0, 4, 86);
		bg.add(line);

		JLabel circle = new JLabel("P", SwingConstants.CENTER) {
			@Override
			protected void paintComponent(Graphics g) {
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setColor(new Color(76, 175, 80));
				g2.fillOval(0, 0, getWidth(), getHeight());
				g2.dispose();
				super.paintComponent(g);
			}
		};
		circle.setOpaque(false);
		circle.setForeground(Color.WHITE);
		circle.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		circle.setBounds(14, 28, 34, 34);
		bg.add(circle);

		JLabel title = new JLabel("비밀번호 안내");
		title.setForeground(new Color(210, 210, 210));
		title.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		title.setBounds(58, 18, 180, 24);
		bg.add(title);

		JLabel text = new JLabel("pass: " + pass);
		text.setForeground(Color.WHITE);
		text.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		text.setBounds(58, 43, 200, 24);
		bg.add(text);

		JLabel close = new JLabel("×", SwingConstants.CENTER);
		close.setForeground(new Color(150, 150, 150));
		close.setFont(new Font("맑은 고딕", Font.PLAIN, 18));
		close.setBounds(286, 12, 26, 26);
		close.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				balloon.dispose();
			}
		});
		bg.add(close);

		balloon.setLocationRelativeTo(null);
		balloon.setVisible(true);

		Timer timer = new Timer(5000, e -> {
			if (balloon != null)
				balloon.dispose();
			((Timer) e.getSource()).stop();
		});
		timer.setRepeats(false);
		timer.start();
	}
}
