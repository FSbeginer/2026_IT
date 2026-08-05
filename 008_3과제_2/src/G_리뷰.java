import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.MouseMotionAdapter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class G_리뷰 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					G_리뷰 frame = new G_리뷰(1);
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
	public JPanel panel;
	public JScrollPane scrollPane;
	public JPanel panel_1;
	public JLabel label_2;
	public G_리뷰(int pno) {
		setTitle("리뷰");
		this.pno = pno;
		setBounds(100, 100, 400, 592);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label_2 = new JLabel("");
		label_2.setVisible(false);
		label_2.setBounds(22, 68, 100, 100);
		getContentPane().add(label_2);
		
		label = new JLabel("New label");
		label.setForeground(new Color(0, 128, 0));
		label.setFont(new Font("굴림", Font.BOLD, 24));
		label.setBounds(12, 10, 360, 43);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setForeground(Color.GRAY);
		label_1.setFont(new Font("굴림", Font.BOLD, 16));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(73, 63, 237, 23);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBounds(12, 94, 360, 449);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(1, 0, 379, 449);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
		
		load();
	}
	private void load() {
		try (var rs = DB.res("select * from star join product using(pno) where pno = ? ",pno)) {
			int w = 355, h = 141, i =0;
			while(rs.next()) {
				var  pp =new G_패널(rs.getString("title"), rs.getDouble("scope"), rs.getString("detail").replaceAll("\"", ""));
				int rno = rs.getInt("sno");
				pp.label.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseEntered(MouseEvent e) {
						if(Files.exists(Path.of("./datafiles/review/"+rno+".jfif"))) {
							label_2.setIcon(getIcon("review/"+rno+".jfif",100,100));
							label_2.setVisible(true);
						}
					}
					@Override
					public void mouseExited(MouseEvent e) {
						label_2.setVisible(false);
					}
				});
				pp.label.addMouseMotionListener(new MouseAdapter() {
					@Override
					public void mouseMoved(MouseEvent e) {
						var p = SwingUtilities.convertPoint(pp.label, e.getPoint(), getContentPane());
						label_2.setLocation(p.x+5, p.y+5);
					}
				});
				pp.setLocation(0, 151*i);
				panel_1.add(pp);
				i++;
			}
			panel_1.setPreferredSize(new Dimension(0,151*i));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			var score = DB.select("select avg(scope) from star join product using(pno) where pno = ? ", Double.class, pno);
			var name = DB.select("select pname from product where pno = ? ", String.class, pno);
			label.setText("기종: "+name);
			label_1.setText(String.format("평점 %.2f / 5", score));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
}
