import java.awt.EventQueue;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;

import javax.swing.JScrollPane;

public class I_리뷰 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					I_리뷰 frame = new I_리뷰(4);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * 
	 * @param pno
	 */
	int pno;
	public JPanel panel;
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;
	public JPanel panel_1;
	public JScrollPane scrollPane;
	public JPanel panel_2;

	public I_리뷰(int pno) {
		getContentPane().setBackground(new Color(236, 238, 244));
		getContentPane().setLayout(null);

		panel = new RoundPanel();
		panel.setBounds(10, 12, 414, 534);
		getContentPane().add(panel);
		panel.setLayout(null);

		label = new JLabel("New label");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 24));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(129, 12, 155, 56);
		panel.add(label);

		label_1 = new JLabel("New label");
		label_1.setForeground(Color.WHITE);
		label_1.setOpaque(true);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label_1.setBackground(new Color(103, 103, 237));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(139, 65, 128, 34);
		panel.add(label_1);

		label_2 = new JLabel("New label");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setForeground(Color.GRAY);
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(45, 112, 324, 22);
		panel.add(label_2);

		label_3 = new JLabel("작성된 리뷰가 없습니다.");
		label_3.setVisible(false);
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setForeground(Color.GRAY);
		label_3.setFont(new Font("맑은 고딕", Font.BOLD, 15));
		label_3.setBounds(45, 293, 324, 34);
		panel.add(label_3);

		panel_1 = new JPanel();
		panel_1.setBounds(24, 144, 360, 378);
		panel.add(panel_1);
		panel_1.setLayout(null);

		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 400, 378);
		panel_1.add(scrollPane);

		panel_2 = new JPanel();
		scrollPane.setViewportView(panel_2);
		panel_2.setLayout(null);
		setTitle("리뷰");
		this.pno = pno;
		setBounds(100, 100, 450, 597);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		load();
	}

	private void load() {
		try (var rs = DB.res("select * from review join product using(pno) join user using(uno) where pno = ?", pno)) {
			int i = 0, w = 360, h = 147;
			while (rs.next()) {
				String path = "";
				if(Files.exists(Path.of("./datafiles/review/"+rs.getInt("rno")+".png")))
					path = "review/"+rs.getInt("rno")+".png";
				else if(Files.exists(Path.of("./datafiles/review/"+rs.getInt("rno")+".jpg")))
					path = "review/"+rs.getInt("rno")+".jpg";
				else if(Files.exists(Path.of("./datafiles/review/"+rs.getInt("rno")+".gif")))
					path = "review/"+rs.getInt("rno")+".gif";
				var pp = new I_패널(getIcon(path, 75, 75), rs.getString("pname"),
						rs.getString("rcontent"), rs.getString("uname"), rs.getDouble("rstar"));
				pp.setLocation(0, 10 + 157 * i);
				panel_2.add(pp);
				i++;
			}
			panel_2.setPreferredSize(new Dimension(0, 10 + 157 * i));
			
			var avg = DB.select("select avg(rstar) from review where pno = ?", Double.class, pno);
			label_1.setText(String.format("평점 %.1f", avg));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		int cnt = panel_2.getComponentCount();
		if (cnt > 0) {
			label.setText(String.format("리뷰 %d건", cnt));
			label_2.setText(String.format("%d개의 리뷰가 작성되었습니다.", cnt));
		} else {
			label.setText("리뷰 0건");
			label_1.setText("평점 0.0");
			label_2.setText("첫 리뷰를 기다리고 있습니다.");
			label_3.setVisible(true);
		}
	}
}
