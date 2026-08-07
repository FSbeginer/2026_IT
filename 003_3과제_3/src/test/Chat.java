package test;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

import java.sql.SQLException;

import java.time.LocalDate;
import java.time.LocalDateTime;

import java.util.HashSet;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class Chat extends BF {

	private JPanel contentPane;
	private JPanel users;     // A영역 - 유저 행이 쌓이는 목록
	private JPanel list;      // B영역 - 말풍선이 쌓이는 목록
	private JScrollPane sp;
	private JTextField search, textField;
	private Row peer;         // 대화 중인 상대. null 이면 아직 아무도 안 열림
	private LocalDate last;   // 마지막으로 구분선을 넣은 날짜

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Chat frame = new Chat();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/** 상대를 지정해서 연다. 공유하기 폼, 유저 정보 폼에서 쓴다. */
	public Chat(int uno) {
		this();
		open(uno, nick(uno));
	}

	/** 가장 최근 대화 상대로 연다. D영역 메시지 아이콘에서 쓴다. */
	public Chat() {
		setTitle("메시지");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 916, 599);
		contentPane = new JPanel();
		setContentPane(contentPane);
		contentPane.setLayout(null);

		// ── 좌측: 내 프로필 / 검색 / A영역 ──────────────────
		var hdr = new Row(User.uno, 280, 40, null, nick(User.uno));
		hdr.setFont(new Font("맑은 고딕", 1, 18));
		hdr.setLocation(10, 10);
		contentPane.add(hdr);

		search = new JTextField();
		search.setBounds(10, 62, 280, 30);
		search.setBackground(new Color(240, 240, 240));
		search.addActionListener(e -> load(search.getText())); // (2-2) 엔터 검색
		contentPane.add(search);

		var lblMsg = new JLabel("메시지");
		lblMsg.setFont(new Font("맑은 고딕", 1, 13));
		lblMsg.setBounds(10, 100, 280, 25);
		contentPane.add(lblMsg);

		users = new JPanel();
		users.setLayout(null);
		contentPane.add(scroll(users, 10, 128, 280, 430));

		var sep = new JSeparator(SwingConstants.VERTICAL);
		sep.setBounds(306, 0, 2, 570);
		contentPane.add(sep);

		// ── 우측: B영역 / 입력 ─────────────────────────────
		list = new JPanel();
		list.setLayout(new BoxLayout(list, BoxLayout.Y_AXIS));
		contentPane.add(sp = scroll(list, 316, 60, 578, 430));

		Balloon.max = sp.getWidth() / 2; // setBounds 뒤에 한 번만

		textField = new JTextField();
		textField.setBounds(326, 505, 470, 34);
		textField.addActionListener(e -> send()); // (5) 엔터
		contentPane.add(textField);

		var btnSend = new JLabel("보내기", SwingConstants.CENTER);
		btnSend.setFont(new Font("맑은 고딕", 1, 13));
		btnSend.setForeground(new Color(0, 132, 255));
		btnSend.setBounds(806, 505, 80, 34);
		btnSend.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				send();
			}
		});
		contentPane.add(btnSend);

		load(""); // like '%%' 라 전체가 걸린다
	}

	/** A영역: 대화 상대별 마지막 문자 한 줄씩. keyword 는 닉네임 부분일치. */
	private void load(String keyword) {
		users.removeAll();
		var seen = new HashSet<Integer>();
		int y = 0;
		try {
			var rs = DB.res("select c.*, u.u_nick, u.u_no from chatroom c"
					+ " join user u on u.u_no = c.send_u + c.get_u - ?"
					+ " where ? in (c.send_u, c.get_u) and u.u_nick like ?"
					+ " order by c.c_no desc",
					User.uno, User.uno, "%" + keyword + "%");
			while (rs.next()) {
				int uno = rs.getInt("u_no");
				if (!seen.add(uno)) // 이미 나온 상대 = 더 옛날 대화
					continue;
				var nick = rs.getString("u_nick");
				var row = new Row(uno, 260, 50, null, nick, rs.getString("c_text"));
				row.setLocation(5, y);
				row.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						open(uno, nick); // (2-3)
					}
				});
				users.add(row);
				y += 55;
				if (peer == null) // 첫 진입 - 가장 최근 대화를 열어둔다
					open(uno, nick);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		users.setPreferredSize(new Dimension(0, y));
		users.revalidate();
		users.repaint();
	}

	/** B영역: 상대 한 명과의 대화 전체. 상대 헤더 교체까지 여기서 한다. */
	private void open(int uno, String nick) {
		if (peer != null)
			contentPane.remove(peer);
		peer = new Row(uno, 400, 40, null, nick);
		peer.setFont(new Font("맑은 고딕", 1, 15));
		peer.setLocation(320, 10);
		contentPane.add(peer);

		list.removeAll();
		last = null; // 날짜 구분선 상태 리셋
		try {
			var rs = DB.res("select c.*, p.p_content, p.p_files from chatroom c"
					+ " left join post p on p.p_no = c.c_ref"
					+ " where ? in (c.send_u, c.get_u) and c.send_u + c.get_u = ?"
					+ " order by c.c_no",
					User.uno, User.uno + uno);
			while (rs.next()) {
				var at = rs.getObject("c_date", LocalDateTime.class);
				var who = rs.getInt("send_u") == User.uno ? null : nick; // null 이면 내 말풍선
				if (rs.getInt("c_type") == 1)
					push(new Balloon(uno, who, rs.getString("p_files"), rs.getString("p_content"), at));
				else
					push(new Balloon(uno, who, rs.getString("c_text"), at));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		contentPane.repaint();
	}

	private void send() {
		if (textField.getText().isBlank()) {
			msgErr("내용이 없습니다."); // (4)
			return;
		}
		var now = LocalDateTime.now();
		try {
			DB.insert("chatroom", null, User.uno, peer.uno, 0, 0, textField.getText(), now);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		push(new Balloon(textField.getText(), now));
		textField.setText("");
		load(search.getText()); // A영역의 마지막 대화 문구 갱신
	}

	/** 메시지 하나를 목록 끝에 붙이고 맨 아래로 스크롤한다. 날짜가 바뀌면 구분선을 먼저 넣는다. */
	private void push(Balloon b) {
		if (!b.at.toLocalDate().equals(last)) {
			last = b.at.toLocalDate();
			list.add(new Balloon(last));
		}
		list.add(b);
		list.revalidate();
		SwingUtilities.invokeLater(() -> {
			var bar = sp.getVerticalScrollBar();
			bar.setValue(bar.getMaximum());
		});
	}

	private String nick(int uno) {
		try {
			return DB.select("select u_nick from user where u_no = ?", String.class, uno);
		} catch (SQLException e) {
			return "";
		}
	}

	/** 테두리 없는 스크롤 - 좌우 두 목록이 똑같이 쓴다. */
	private JScrollPane scroll(JPanel view, int x, int y, int w, int h) {
		var s = new JScrollPane(view);
		s.setBounds(x, y, w, h);
		s.setBorder(null);
		s.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		s.getVerticalScrollBar().setUnitIncrement(30);
		return s;
	}
}

class Row extends JLabel {
	final int uno;
	final String nick;

	Row(int uno, int w, int h, Color ring, String... vals) {
		this.uno = uno;
		this.nick = vals[0]; // 첫 번째 vals 는 항상 닉네임
		setSize(w, h);
		setFont(new Font("맑은 고딕", 0, 12));
		setIconTextGap(6);
		Helper.getImage("profile/" + uno + ".jpg", h, h, true, img -> setIcon(new ImageIcon(circle(img, h, ring))));

		var sb = new StringBuilder("<html>");
		for (int i = 0; i < vals.length; i++) {
			if (i == 0)
				sb.append("<b>").append(vals[i]).append("</b>");
			else if (i == vals.length - 1)
				sb.append("<br><font size='2' color='gray'>").append(vals[i]);
			else
				sb.append(" ").append(vals[i]);
		}
		setText(sb.toString());
	}

	/** 원형으로 잘려서 넘어온 사진 위에 테두리를 덧그린다. */
	private static BufferedImage circle(Image src, int size, Color ring) {
		var bi = new BufferedImage(size, size, 2);
		var g2 = bi.createGraphics();
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2.drawImage(src, 0, 0, null);
		if (ring != null) {
			g2.setColor(ring);
			g2.setStroke(new BasicStroke(2f));
			g2.draw(new Ellipse2D.Double(1, 1, size - 2, size - 2));
		}
		g2.dispose();
		return bi;
	}
}
