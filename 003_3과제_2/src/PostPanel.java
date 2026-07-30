import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Image;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class PostPanel extends JPanel {
	public JLabel label;
	public JLabel label_1;
	public JLabel label_2;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	int pno;
	public PostPanel(int pno) {
		addMouseListener(new ThisMouseListener());
		this.pno = pno;
		setSize(500, 70);
		setLayout(null);
		
		label = new JLabel();
		label.setBounds(0, 0, 70, 70);
		add(label);
		
		label_1 = new JLabel();
		label_1.setFont(new Font("굴림", Font.BOLD, 13));
		label_1.setBounds(80, 7, 245, 20);
		add(label_1);
		
		label_2 = new JLabel();
		label_2.setBounds(80, 27, 245, 20);
		add(label_2);
		
		label_3 = new JLabel();
		label_3.setForeground(Color.GRAY);
		label_3.setBounds(80, 48, 245, 20);
		add(label_3);
		
		load();
		SwingUtilities.invokeLater(()->{label.setIcon(new ImageIcon(imgs.get(0).getScaledInstance(65, 65, 2)));});
	}
	List<Image> imgs = new ArrayList<Image>();
	private String[] files;
	private void load() {
		try (var rs = DB.res("select * from post join user using(u_no) where p_no = ?",pno)) {
			rs.next();
			files = rs.getString("p_files").split(",");
			Helper.getImage("posts/"+files[0]+".jpg", x->imgs.add(x));
			label_1.setText(rs.getString("u_nick"));
			label_2.setText(rs.getString("p_content"));
			label_3.setText(String.format("게시물 #%d", pno));
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private class ThisMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			for (int i = 1; i < files.length; i++) {
				Helper.getImage("posts/"+files[i]+".jpg", x->imgs.add(x));	
			}
			((BF)SwingUtilities.getWindowAncestor(label)).showPage(new F_댓글(pno, imgs));
		}
	}
}
