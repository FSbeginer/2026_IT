import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class E_시간선택 extends BF {

	/**
	 * Create the frame.
	 * 
	 * @param selDate
	 * @param dno
	 * @param cno
	 */
	int cno, dno, ono;
	LocalDate selDate;
	public JLabel label;
	public JPanel panel;
	public JButton button;
	public JLabel label_1;

	public E_시간선택(int cno, int dno, int ono, LocalDate selDate) {
		this(cno, dno, selDate);
		this.ono = ono;
	}

	/**
	 * @wbp.parser.constructor
	 */
	public E_시간선택(int cno, int dno, LocalDate selDate) {
		setTitle("시간선택");
		this.cno = cno;
		this.dno = dno;
		this.selDate = selDate;
		setBounds(100, 100, 473, 507);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label_1 = new JLabel("");
		label_1.setForeground(Color.WHITE);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(142, 61, 172, 27);
		getContentPane().add(label_1);

		label = new JLabel(String.format("<html><div align ='center'>예약시간을 선택해주세요.<br>선택한 날짜 : %s", selDate));
		label.setVerticalAlignment(SwingConstants.TOP);
		label.setForeground(new Color(255, 255, 255));
		label.setOpaque(true);
		label.setBackground(new Color(78, 132, 233));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 18));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 457, 88);
		getContentPane().add(label);

		panel = new JPanel();
		panel.setBounds(25, 110, 403, 295);
		getContentPane().add(panel);
		panel.setLayout(new GridLayout(0, 4, 15, 15));

		button = new JButton("예약");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(Color.ORANGE);
		button.setForeground(new Color(255, 255, 255));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		button.setBounds(137, 419, 179, 39);
		getContentPane().add(button);

		getReservation();
		addTimeStamp();
	}

	private void getReservation() {
		try {
			var rs = DB.res("select * from orders where dno = ? and orderdate = ?", dno, selDate);
			while (rs.next()) {
				reservation.add(rs.getString("ordertime"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	Set<String> reservation = new HashSet<String>();
	LocalTime selTime;

	List<Object[]> pps = new ArrayList<Object[]>();

	private void addTimeStamp() {
		var time = LocalTime.of(9, 00);
		var maxTime = LocalTime.of(18, 00);
		while (!time.isAfter(maxTime)) {
			var pp = new RoundPanel();
			pp.setBackground(new Color(235, 245, 255));

			var jl = new JLabel(time.format(DateTimeFormatter.ofPattern("HH:mm")), 0);
			jl.setForeground(label.getBackground());
			pp.setLayout(new BorderLayout());
			pp.add(jl);

			if (reservation.contains(time.format(DateTimeFormatter.ofPattern("HH:mm")))
					|| (selDate.equals(LocalDate.now()) && time.isBefore(LocalTime.now()))) {
				pp.setEnabled(false);
				pp.setBackground(Color.gray);
				jl.setForeground(Color.DARK_GRAY);
			}

			final LocalTime ft = time;
			pp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if (pp.isEnabled()) {
						selTime = ft;
						for (var pp : pps) {
							if (!((JPanel) pp[0]).isEnabled())
								continue;
							((JPanel) pp[0]).setBackground(new Color(235, 245, 255));
							((JLabel) pp[1]).setForeground(label.getBackground());
						}
						pp.setBackground(new Color(58, 112, 213));
						jl.setForeground(Color.red);
						label_1.setText("선택 시간 : " + ft.format(DateTimeFormatter.ofPattern("HH:mm")));
					}
				}
			});

			panel.add(pp);
			time = time.plusMinutes(30);
			pps.add(new Object[] { pp, jl });
		}
	}

	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if (selTime == null) {
				msgErr("시간을 선택해주세요.");
				return;
			}
			try {
				DB.insert("orders", 0, selDate, "0000-00-00", User.uno,
						selTime.format(DateTimeFormatter.ofPattern("HH:mm")), cno, dno);
				msgInfo("예약이 완료되었습니다.");
				if (ono == 0) {
					dispose();
					while (!prev.isEmpty()) {
						var p = prev.pop();
						if (p instanceof B_메인) {
							p.showPage(new F_마이페이지());
							break;
						} else {
							p.dispose();
						}
					}
				}
				else {
					dispose();
					while (!prev.isEmpty()) {
						var p = prev.pop();
						if (p instanceof F_마이페이지) {
							p.updateForm();
							p.setVisible(true);
							break;
						} else {
							p.dispose();
						}
					}
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class RoundPanel extends JPanel {
	public RoundPanel() {
		setOpaque(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		g2.dispose();
	}
}
