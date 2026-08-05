import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import javax.swing.SwingConstants;
import java.awt.Color;
import javax.swing.JButton;

public class F_패널 extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	public JButton button;

	/**
	 * Create the panel.
	 */
	int ono;
	public F_패널(int ono) {
		this.ono = ono;
		setSize(772, 131);
		setLayout(null);
		
		label = new JLabel("");
		label.setBounds(10, 12, 108, 107);
		add(label);
		
		label_1 = new JLabel("New label");
		label_1.setBounds(128, 12, 135, 21);
		add(label_1);
		
		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_2.setBounds(128, 35, 215, 35);
		add(label_2);
		
		label_3 = new JLabel("New label");
		label_3.setBounds(129, 67, 135, 21);
		add(label_3);
		
		label_4 = new JLabel("New label");
		label_4.setFont(new Font("맑은 고딕", Font.BOLD, 17));
		label_4.setBounds(130, 87, 215, 35);
		add(label_4);
		
		label_5 = new JLabel("New label");
		label_5.setOpaque(true);
		label_5.setBackground(Color.ORANGE);
		label_5.setForeground(Color.WHITE);
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setBounds(657, 8, 90, 25);
		add(label_5);
		
		button = new RoundButton("상세보기");
		button.setForeground(Color.BLACK);
		button.setBounds(611, 85, 144, 34);
		add(button);
		
		load();
	}

	private void load() {
		try (var rs = DB.res("select * from orders join product using(pno) where ono = ?",ono)) {
			rs.next();
			label.setIcon(BF.getIcon("product/"+rs.getInt("pno")+".png",100,100));
			label_1.setText(rs.getString("odate"));
			label_2.setText(rs.getString("pname"));
			label_3.setText(String.format("수량 %d개", rs.getInt("ocount")));
			label_4.setText(String.format("%,d원", rs.getInt("oprice")));
			
			var date = rs.getDate("odate").toLocalDate();
			long day = ChronoUnit.DAYS.between(date, LocalDate.now());
			if(day == 0) {
				label_5.setText("배송준비중");
			}else if(day==1) {
				label_5.setText("배송중");
				label_5.setBackground(Color.yellow);
			}else {
				label_5.setText("배송완료");
				label_5.setBackground(Color.green);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

}
