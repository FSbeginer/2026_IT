import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class A_로그인패널 extends JPanel {
	public JLabel label;
	public JSeparator separator;
	public JLabel label_1;
	public JTextField textField;
	public JLabel label_2;
	public JPasswordField passwordField;
	public JButton button;
	public JLabel label_3;

	/**
	 * Create the panel.
	 */
	Color baseColor;
	int lno;
	public A_로그인패널(Color baseColor, String name, int no) {
		lno = no;
		setOpaque(false);
		this.baseColor = baseColor;
		setLayout(null);

		label = new JLabel(name);
		label.setFont(new Font("맑은 고딕", Font.BOLD, 16));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(86, 10, 199, 29);
		add(label);

		separator = new JSeparator();
		separator.setBounds(12, 53, 347, 2);
		add(separator);

		label_1 = new JLabel("아이디");
		label_1.setBounds(22, 65, 57, 15);
		add(label_1);

		textField = new JTextField();
		textField.setBorder(new RoundBorder(new Color(171, 173, 179)));
		textField.setOpaque(false);
		textField.setBounds(22, 93, 324, 43);
		add(textField);
		textField.setColumns(10);

		label_2 = new JLabel("비밀번호");
		label_2.setBounds(22, 146, 57, 15);
		add(label_2);

		passwordField = new JPasswordField();
		passwordField.setBorder(new RoundBorder(new Color(171, 173, 179)));
		passwordField.setOpaque(false);
		passwordField.setBounds(22, 171, 324, 40);
		add(passwordField);

		button = new RoundButton("로그인");
		button.addActionListener(new ButtonActionListener());
		button.setForeground(Color.WHITE);
		button.setBackground(Color.DARK_GRAY);
		button.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		button.setBounds(22, 245, 324, 48);
		add(button);

		label_3 = new JLabel("지도에서 지역을 선택한 후 로그인하세요");
		label_3.setHorizontalAlignment(SwingConstants.CENTER);
		label_3.setBounds(59, 303, 262, 15);
		add(label_3);

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.9f));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setPaint(new GradientPaint(new Point2D.Double(getWidth()/2,0), baseColor, new Point2D.Double(getWidth()/2,getHeight()), new Color(250,240,235)));
		g2.fillRoundRect(0, 0, getWidth()-1, getHeight()-1, 15, 15);
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var id = textField.getText();
			var pw = new String(passwordField.getPassword());
			if(id.isBlank()||pw.isBlank()) {
				BF.msgErr("빈칸이 존재합니다.");
				return;
			}
			try {
				var rs =DB.res("select * from user where id = ? and pw = ?",id,pw);
				if(rs.next()) {
					User u = DB.getUser(rs.getInt("uno"));
					if(u.lno!=lno) {
						BF.msgErr("지역을 확인하세요.");
						return;
					}
					User.uno = rs.getInt("uno");
					BF.msgInfo(u.name+"님 환영합니다.");
					((BF)SwingUtilities.getWindowAncestor(button)).showPage(new B_메인());
					return;
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			try (var rs = DB.res("select * from doctor where id = ? and pw = ?",id,pw)) {
				if(rs.next()) {
					int dlno = DB.select("select lno from doctor where dno =?", Integer.class, rs.getInt("dno"));
					if(dlno!=lno) {
						BF.msgErr("지역을 확인하세요.");
						return;
					}
					User.uno = rs.getInt("dno");
					((BF)SwingUtilities.getWindowAncestor(button)).showPage(new H_스케줄());
				}
				else {
					BF.msgErr("존재하지 않는 아이디입니다.");
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
}

class RoundBorder extends LineBorder {
	public RoundBorder(Color color, int tink) {
		super(color, tink);
	}

	public RoundBorder(Color color) {
		super(color);
	}

	@Override
	public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawRoundRect(0, 0, width - 1, height - 1, 15, 15);
	}
}

class RoundButton extends JButton {
	public RoundButton(String txt) {
		super(txt);
		setOpaque(false);
		setBorderPainted(false);
		setFocusPainted(false);
		setContentAreaFilled(false);
	}

	@Override
	protected void paintComponent(Graphics g) {
		Graphics2D g2 = (Graphics2D) g.create();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(getBackground());
		g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
		super.paintComponent(g);
	}
}