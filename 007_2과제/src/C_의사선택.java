import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

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
	 * @param cno 
	 */
	int cno;
	public JLabel label;
	public JScrollPane scrollPane;
	public JPanel panel;
	public C_의사선택(int cno) {
		setTitle("의사선택");
		this.cno = cno;
		setBounds(100, 100, 577, 405);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("전문의를 선택해주세요");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 20));
		label.setForeground(Color.WHITE);
		label.setOpaque(true);
		label.setBackground(new Color(102, 165, 217));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 561, 60);
		getContentPane().add(label);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 58, 561, 308);
		getContentPane().add(scrollPane);
		
		panel = new JPanel();
		scrollPane.setViewportView(panel);
		panel.setLayout(null);

		try {
			load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}
	private void load() throws SQLException {
		int lno = DB.select("select lno from user where uno =?", Integer.class, User.uno);
		var rs = DB.res("select d.*, count(*) cnt from doctor d left join (select * from orders where curdate()>=orderdate) o on d.cno = o.cno and o.dno = d.dno where d.cno = ? and lno = ? group by dno;", lno, cno);
		int i = 0,w =234, h=186;
		while(rs.next()) {
			var pp = new C_패널(getIcon("doctor/"+rs.getInt("dno")+".png",186, 130),rs.getString("dname"), rs.getInt("cnt"), rs.getInt("day_off"));
			int dno = rs.getInt("dno");
			pp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					pp.setBorder(new LineBorder(new Color(102, 165, 217),2));
					pp.over.setVisible(true);
				}
				@Override
				public void mouseExited(MouseEvent e) {
					pp.setBorder(new LineBorder(Color.LIGHT_GRAY));
					pp.over.setVisible(false);
				}
				@Override
				public void mouseClicked(MouseEvent e) {
					if(e.getClickCount()==2) {
						showPage(new D_달력(dno));
					}
				}
			});
			pp.setLocation(30+(w+15)*(i%2), 20+(h+5)*(i/2));
			panel.add(pp);
			i++;
		}
		i++;
		panel.setPreferredSize(new Dimension(0, 20+(h+5)*(i/2)));
	}
}
