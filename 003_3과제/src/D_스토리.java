import java.awt.EventQueue;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import java.sql.SQLException;
import java.util.List;

import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.JPanel;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class D_스토리 extends BF {

	public JLabel label;
	public JLabel label_1;
	public Profile panel;
	public JLabel label_2;
	public JLabel label_3;
	public JLabel label_4;
	public JLabel label_5;
	List<Integer> unos;
	int idx;

	public D_스토리(List<Integer> unos, int idx) {
		addWindowListener(new ThisWindowListener());
		this.unos = unos;
		this.idx = idx;

		getContentPane().setBackground(Color.BLACK);
		getContentPane().setLayout(null);

		label = new JLabel("<");
		label.addMouseListener(new LabelMouseListener());
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setForeground(Color.WHITE);
		label.setBounds(12, 199, 23, 67);
		getContentPane().add(label);

		label_1 = new JLabel(">");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label_1.setForeground(Color.WHITE);
		label_1.setBounds(351, 199, 23, 67);
		getContentPane().add(label_1);

		panel = new Profile(true);
		panel.setCircleColor(Color.white);
		panel.setBackground(Color.BLACK);
		panel.setBounds(12, 10, 51, 50);
		getContentPane().add(panel);

		label_2 = new JLabel("New label");
		label_2.setFont(new Font("굴림", Font.BOLD, 13));
		label_2.setForeground(Color.WHITE);
		label_2.setBounds(75, 10, 232, 27);
		getContentPane().add(label_2);

		label_3 = new JLabel("New label");
		label_3.setForeground(Color.WHITE);
		label_3.setBounds(74, 47, 300, 15);
		getContentPane().add(label_3);

		label_4 = new JLabel("");
		label_4.setBounds(50, 78, 289, 403);
		getContentPane().add(label_4);

		label_5 = new JLabel("New label");
		label_5.setHorizontalAlignment(SwingConstants.CENTER);
		label_5.setForeground(Color.GRAY);
		label_5.setBounds(145, 491, 101, 23);
		getContentPane().add(label_5);
		setBackground(Color.BLACK);
		setTitle("스토리");
		setBounds(100, 100, 402, 563);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		load(unos.get(idx));
	}

	int max, storyIdx;
	private Timer timer;
	private void load(int uno) {
		if(timer!=null&&timer.isRunning()) timer.stop();
		getImage("profile/"+uno+".jpg", 48, 48, x->panel.setImage(x)).execute();
		storyIdx = 0;
		max = getStoryCount(uno)-1;
		storychange();
		timer = new Timer(1000, e->{
			storyIdx++;
			if(storyIdx>max) {
				storyIdx = 0;
			}
			storychange();
		});
		timer.start();
	}
	private void storychange() {
		try (var rs = DB.res("select * from story left join user using(u_no) where u_no = ? limit ?,1", unos.get(idx),storyIdx)) {
			rs.next();
			label_2.setText(rs.getString("u_nick"));
			label_3.setText(rs.getString("s_content"));
			getImage("story/"+rs.getInt("s_no")+".jpg", label_4.getWidth(), label_4.getHeight(), x->label_4.setIcon(new ImageIcon(x))).execute();
			label_5.setText(String.format("스토리 %d / %d", storyIdx+1,max+1));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	private int getStoryCount(int uno) {
		try (var rs = DB.res("select count(*) from story where u_no = ?", uno)) {
			rs.next();
			return rs.getInt(1);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return 0;
	}


	private class ThisWindowListener extends WindowAdapter {
		@Override
		public void windowClosed(WindowEvent e) {
			timer.stop();
		}
	}
	private class LabelMouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(idx>0) {
				idx--;
				load(unos.get(idx));
			}
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(idx<unos.size()-1) {
				idx++;
				load(unos.get(idx));
			}
		}
	}
}
