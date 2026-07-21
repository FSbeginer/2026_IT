import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import java.awt.event.MouseMotionAdapter;

public class G_리뷰 extends BF {
	private JLabel label;
	private JLabel label_1;
	private JPanel panel;
	private JScrollPane scrollPane;
	private JPanel panel_1;

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
	 */
	int pno;
	public G_리뷰(int pno) {
		this.pno = pno;
		setTitle("리뷰");
		setBounds(100, 100, 464, 612);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("New label");
		label.setForeground(new Color(0, 128, 0));
		label.setFont(new Font("굴림", Font.BOLD, 19));
		label.setBounds(12, 10, 414, 51);
		getContentPane().add(label);
		
		label_1 = new JLabel("New label");
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(165, 69, 119, 24);
		getContentPane().add(label_1);
		
		panel = new JPanel();
		panel.setBounds(12, 101, 426, 464);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setBounds(0, 0, 472, 464);
		panel.add(scrollPane);
		
		panel_1 = new JPanel();
		panel_1.addMouseMotionListener(new Panel_1MouseMotionListener());
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(null);
		
		label_2 = new JLabel("");
		label_2.setVisible(false);
		label_2.setBounds(72, 99, 108, 110);
		panel_1.add(label_2);
		
		
		try {
			var rs = DB.res("select pname, avg(scope) from product join star using(pno) where pno = ? group by pno;",pno);
			rs.next();
			label.setText(rs.getString(1));
			label_1.setText(String.format("평점 : %.2f : 5", rs.getDouble(2)));
			load();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		scrollPane.getVerticalScrollBar().setUnitIncrement(30);
	}

	List<Integer> pnos;
	private JLabel label_2;
	private void load() throws SQLException {
		pnos = new ArrayList<>();
		var rs = DB.res("select * from star where pno  =?",pno);
		int w = 422, h = 146, i =0;
		while(rs.next()) {
			var pp = new G_ReviwPanel(rs.getString("title"),rs.getString("detail"), rs.getDouble("scope"));
			pp.setLocation(0, (h+10)*i);
			int sno = rs.getInt(1);
			pp.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					if(new File("./datafiles/review/"+sno+".jfif").exists()) {
						var img = getIcon("review/"+sno+".jfif",105,105);
						label_2.setVisible(true);
						label_2.setIcon(img);
					}
				}
				@Override
				public void mouseExited(MouseEvent e) {
					label_2.setVisible(false);
				}
			});
			pp.addMouseMotionListener(new MouseAdapter() {
				@Override
				public void mouseMoved(MouseEvent e) {
					var rp = SwingUtilities.convertPoint(pp, e.getPoint(), panel_1);
					label_2.setLocation(rp);
				}
			});
			panel_1.add(pp);
			pnos.add(rs.getInt(1));
			i++;
		}
		panel_1.setPreferredSize(new Dimension(0, 156*i));
	}
	private class Panel_1MouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseMoved(MouseEvent e) {
			label_2.setLocation(e.getPoint());
		}
	}
}
