import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class D_달력 extends BF {


	int dno,cno;
	public JPanel panel;
	public JPanel panel_1;
	public JPanel panel_2;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JButton button;
	
	int ono;
	
	
	public D_달력(int dno, int cno, int ono, LocalDate selDate) {
		this(dno,cno);
		this.ono = ono;
		this.selDate = selDate;
		this.basetime = selDate;
		lendering();
	}

	/**
	 * @wbp.parser.constructor
	 */
	public D_달력(int dno, int cno) {
		setTitle("달력");
		this.dno = dno;
		this.cno = cno;
		setBounds(100, 100, 511, 502);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel.setBounds(0, 0, 495, 50);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		label = new JLabel("<");
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("굴림", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(62, 10, 61, 34);
		panel.add(label);
		
		label_1 = new JLabel(">");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("굴림", Font.BOLD, 20));
		label_1.setBounds(364, 10, 61, 34);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 14));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(164, 10, 166, 30);
		panel.add(label_2);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setBounds(0, 96, 495, 298);
		getContentPane().add(panel_1);
		panel_1.setLayout(new GridLayout(6, 7, 0, 0));
		
		panel_2 = new JPanel();
		panel_2.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_2.setBounds(0, 49, 495, 47);
		getContentPane().add(panel_2);
		panel_2.setLayout(new GridLayout(0, 7, 0, 0));
		
		button = new JButton("선택");
		button.addActionListener(new ButtonActionListener());
		button.setFont(new Font("맑은 고딕", Font.PLAIN, 14));
		button.setBackground(new Color(78, 132, 233));
		button.setForeground(Color.WHITE);
		button.setBounds(92, 406, 311, 47);
		getContentPane().add(button);
		
		addLabel();
		lendering();
	}
	
	JLabel[] jls = new JLabel[42];
	Map<JLabel, LocalDate> dates = new HashMap<JLabel, LocalDate>();
	
	private void addLabel() {
		String[] week = "일,월,화,수,목,금,토".split(",");
		for (int i = 0; i < 7; i++) {
			var jl = new JLabel(week[i]);
			jl.setHorizontalAlignment(0);
			if(i%7==0)
				jl.setForeground(Color.red);
			else if(i%7==6)
				jl.setForeground(Color.blue);
			panel_2.add(jl);
		}
		for (int i = 0; i < 42; i++) {
			var jl = new JLabel();
			jl.setHorizontalAlignment(0);
			jl.setBorder(new LineBorder(Color.LIGHT_GRAY,2));
			jl.setOpaque(true);
			jl.setBackground(Color.white);
			if(i%7==0)
				jl.setForeground(Color.red);
			else if(i%7==6)
				jl.setForeground(Color.blue);
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(jl.isEnabled())
						selectjl(jl);
				}
			});
			panel_1.add(jl);
			jls[i] = jl;
		}
		
	}
	LocalDate basetime = LocalDate.now();
	LocalDate selDate = LocalDate.now();
	
	private void selectjl(JLabel jl) {
		selDate = dates.get(jl);
		System.out.println(selDate);
		lendering();
	}
	
	private void lendering() {
		LocalDate first = LocalDate.of(basetime.getYear(), basetime.getMonthValue(), 1);
		int week = first.getDayOfWeek().getValue()%7;
		label_2.setText(first.format(DateTimeFormatter.ofPattern("yyyy년 M월")));
		for (int i = 0; i < 42; i++) {
			var date = first.plusDays(i-week);
			dates.put(jls[i], date);
			
			if(date.getMonthValue()==first.getMonthValue()) {
				jls[i].setText(date.getDayOfMonth()+"");
				jls[i].setEnabled(!date.isBefore(LocalDate.now()));
				jls[i].setBackground(Color.white);
				if(selDate.equals(date))
					jls[i].setBackground(Color.orange);
			}
			else {
				jls[i].setEnabled(false);
				jls[i].setText("");
				jls[i].setBackground(Color.white);
			}
		}
	}

	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			basetime = basetime.plusMonths(1);
			lendering();
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			basetime = basetime.plusMonths(-1);
			lendering();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int dayoff=DB.select("select day_off from doctor where dno = ?", Integer.class, dno);
				if(dayoff==(selDate.getDayOfWeek().getValue()%7+1)) {
					msgErr("의사 휴무일입니다.\n다른 날짜를 선택해주세요.");
					return;
				}
				if(ono==0)
					showPage(new E_시간선택(cno, dno, selDate));
				else
					showPage(new E_시간선택(cno, dno, ono, selDate));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
