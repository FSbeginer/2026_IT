import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;

import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class D_달력 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					D_달력 frame = new D_달력(1);
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
	int dno;
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	/**
	 * @wbp.parser.constructor
	 */
	public D_달력(int dno) {
		setTitle("달력");
		this.dno = dno;
		setBounds(100, 100, 490, 477);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(0, 0, 474, 350);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel_1.setBounds(0, 40, 474, 310);
		panel.add(panel_1);
		panel_1.setLayout(new GridLayout(7, 7, 0, 0));
		
		label = new JLabel("<");
		label.addMouseListener(new LabelMouseListener());
		label.setFont(new Font("굴림", Font.BOLD, 15));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 109, 41);
		panel.add(label);
		
		label_1 = new JLabel(">");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("굴림", Font.BOLD, 15));
		label_1.setBounds(365, 0, 109, 41);
		panel.add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(121, 0, 231, 41);
		panel.add(label_2);
		
		button = new JButton("선택");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(102, 165, 217));
		button.setForeground(Color.WHITE);
		button.setBounds(71, 369, 319, 45);
		getContentPane().add(button);
		
		labelAdd();
		dateLoad();
	}
	
	boolean change = false;
	int ono;
	public D_달력(int dno, int ono,LocalDate changeDate) {
		this(dno);
		this.ono = ono;
		target = changeDate;
		baseDate = changeDate;
		dateLoad();
		change = true;
	}

	Map<JLabel, LocalDate> dates;
	private void dateLoad() {
		dates = new HashMap<>();
		var first = LocalDate.of(baseDate.getYear(), baseDate.getMonthValue(), 1);
		int week = first.getDayOfWeek().getValue()%7;
		label_2.setText(first.format(DateTimeFormatter.ofPattern("yyyy년 M월")));
		for (int i = 0; i < jls.length; i++) {
			var date = first.plusDays(i-week);
			jls[i].setText(date.getMonthValue()==first.getMonthValue()?date.getDayOfMonth()+"":"");
			jls[i].setEnabled(!date.isBefore(LocalDate.now())&&date.getMonthValue()==first.getMonthValue());
			dates.put(jls[i], date);
			if(date.equals(target)) jls[i].setBackground(Color.ORANGE);
			else jls[i].setBackground(Color.white);
		}
	}

	JLabel[] jls = new JLabel[42];
	
	LocalDate baseDate = LocalDate.now();
	LocalDate target = LocalDate.now();
	private void labelAdd() {
		String[] wtxt = "일,월,화,수,목,금,토".split(",");
		for (int i = 0; i < 7; i++) {
			var jl = new JLabel(wtxt[i], 0);
			if(i==0)
				jl.setForeground(Color.red);
			else if(i==6)
				jl.setForeground(Color.blue);
			jl.setBorder(new MatteBorder(0, 0, 2, 0, Color.black));
			panel_1.add(jl);
		}
		for (int i = 0; i < 42; i++) {
			var jl = new JLabel();
			jl.setOpaque(true);
			jl.setBackground(Color.white);
			jl.setHorizontalAlignment(0);
			jl.setBorder(new LineBorder(Color.LIGHT_GRAY,1));
			
			if(i%7==0)
				jl.setForeground(Color.red);
			else if(i%7==6)
				jl.setForeground(Color.blue);
			
			jl.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent e) {
					if(jl.isEnabled()) {
						selected(jl);
					}
				}
			});
			panel_1.add(jls[i] = jl);
		}
	}
	private void selected(JLabel selected) {
		for (var jl : jls) {
			jl.setBackground(Color.white);
		}
		selected.setBackground(Color.orange);
		target = dates.get(selected);
		dateLoad();
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			baseDate = baseDate.plusMonths(-1);
			dateLoad();
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			baseDate = baseDate.plusMonths(1);
			dateLoad();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			try {
				int dayOff = DB.select("select day_off from doctor  where dno = ?", Integer.class, dno);
				if(dayOff==target.getDayOfWeek().getValue()) {
					msgErr("의사 휴무일입니다.\n다른 날짜를 선택해주세요.");
					return;
				}
				if(change)
					showPage(new E_시간선택(dno, ono,target, true));
				else
					showPage(new E_시간선택(dno,target));
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}
