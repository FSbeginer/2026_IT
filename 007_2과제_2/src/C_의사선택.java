import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JScrollPane;
import javax.swing.JPanel;

public class C_의사선택 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					C_의사선택 frame = new C_의사선택(1);
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
	int cno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public C_의사선택(int cno) {
		setTitle("의사선택");
		this.cno = cno;
		setBounds(100, 100, 583, 398);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("전문의를 선택해주세요");
		label.setOpaque(true);
		label.setForeground(Color.WHITE);
		label.setBackground(new Color(78, 132, 233));
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 567, 69);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 69, 567, 290);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);
		
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		
		load();
	}
	private void load() {
		try (var rs = DB.res("SELECT d.*, count(ono) cnt FROM doctor d left join (select * from orders where orderdate > curdate()) o using(dno) where d.cno = ? and lno = ? group by dno;", cno, User.lno)) {
			int w = 236, h =158, i=  0;
			while(rs.next()) {
				String[] week = "일,월,화,수,목,금,토".split(",");
				var pp = new C_패널(getIcon("doctor/"+rs.getInt("dno")+".png",188, 112), rs.getString("dname"), rs.getInt("cnt"), week[rs.getInt("day_off")-1]);
				pp.setLocation(30+(w+20)*(i%2), 20+(h+10)*(i/2));
				panel.add(pp);
				int dno = rs.getInt("dno");
				pp.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if(e.getClickCount()==2) {
							showPage(new D_달력(dno,cno));
						}
					}
					@Override
					public void mouseEntered(MouseEvent e) {
						pp.jl.setVisible(true);
						pp.setBorder(new LineBorder(new Color(78, 132, 233),2));
					}
					@Override
					public void mouseExited(MouseEvent e) {
						pp.jl.setVisible(false);
						pp.setBorder(new LineBorder(Color.lightGray));
					}
				});
				i++;
			}
			i++;
			panel.setPreferredSize(new Dimension(0, 20+(h+10)/i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
