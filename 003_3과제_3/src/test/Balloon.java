package test;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

public class Balloon extends JPanel {
	public static int max = 200; // 말풍선 최대 폭
	private static final int PAD = 10, PIC = 40, PST = 140; // 안쪽 여백, 아바타 지름, 공유 사진 한 변
	private static final DateTimeFormatter TIME = DateTimeFormatter.ofPattern("a h:mm", Locale.KOREA);
	private static final DateTimeFormatter DAY = DateTimeFormatter.ofPattern("yyyy년 M월 d일 EEEE", Locale.KOREA);

	/** 이 메시지가 작성된 시각. 날짜 구분선이면 null. */
	public final LocalDateTime at;

	/** 날짜 구분선 - 가운데 정렬. */
	public Balloon(LocalDate day) {
		super(new FlowLayout(FlowLayout.CENTER, 5, 6));
		at = null;
		setOpaque(false);
		setAlignmentX(LEFT_ALIGNMENT);

		var l = label(day.format(DAY), 11, Color.darkGray);
		l.setOpaque(true);
		l.setBackground(new Color(232, 238, 246));
		l.setBorder(new EmptyBorder(4, 10, 4, 10));
		add(l);
	}

	/** 내 메시지 - 오른쪽 정렬, 시간 + 파란 말풍선. */
	public Balloon(String text, LocalDateTime at) {
		this(0, null, text, at);
	}

	/** 상대 메시지 - 왼쪽 정렬, 아바타 + (닉네임 / 회색 말풍선) + 시간. */
	public Balloon(int uno, String nick, String text, LocalDateTime at) {
		super(new FlowLayout(nick == null ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 2));
		boolean mine = nick == null;
		this.at = at;
		setOpaque(false);
		setAlignmentX(LEFT_ALIGNMENT);

		var ta = new JTextArea(text);
		ta.setLineWrap(true);
		ta.setWrapStyleWord(true);
		ta.setFocusable(false);
		ta.setFont(new Font("맑은 고딕", 0, 12));
		ta.setBorder(new EmptyBorder(6, PAD, 6, PAD));
		ta.setBackground(mine ? new Color(0, 132, 255) : new Color(238, 238, 238));
		ta.setForeground(mine ? Color.white : Color.black);
		ta.setAlignmentX(LEFT_ALIGNMENT);

		// 폭을 먼저 확정한 뒤에 높이를 받아와야 줄 수가 반영된다.
		int w = Math.min(ta.getFontMetrics(ta.getFont()).stringWidth(text) + PAD * 2 + 2, max);
		ta.setSize(w, Short.MAX_VALUE);
		var d = new Dimension(w, ta.getPreferredSize().height);
		ta.setPreferredSize(d);
		ta.setMaximumSize(d);

		var box = new JPanel(); // 닉네임과 말풍선을 세로로 쌓는다.
		box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));
		box.setOpaque(false);
		if (!mine)
			box.add(label(nick, 11, Color.black));
		box.add(ta);

		var t = label(at.format(TIME), 10, Color.gray);
		if (mine) {
			add(t);
			add(box);
			return;
		}
		add(avatar(uno, box.getPreferredSize().height));
		add(box);
		add(t);
	}

	/** 게시물 공유 - 사진 위, 게시물 내용 아래. nick 이 null 이면 내가 보낸 것. */
	public Balloon(int uno, String nick, String file, String content, LocalDateTime at) {
		super(new FlowLayout(nick == null ? FlowLayout.RIGHT : FlowLayout.LEFT, 5, 2));
		this.at = at;
		setOpaque(false);
		setAlignmentX(LEFT_ALIGNMENT);

		var card = new JLabel(content, SwingConstants.CENTER);
		card.setFont(new Font("맑은 고딕", 0, 12));
		card.setVerticalTextPosition(SwingConstants.BOTTOM); // 글씨를 사진 아래로
		card.setHorizontalTextPosition(SwingConstants.CENTER);
		card.setPreferredSize(new Dimension(PST, PST + 24));
		card.setOpaque(true);
		card.setBackground(new Color(238, 238, 238));
		Helper.getImage("posts/" + file.split(",")[0] + ".jpg", PST, PST, false,
				img -> card.setIcon(new ImageIcon(img)));

		var t = label(at.format(TIME), 10, Color.gray);
		if (nick == null) {
			add(t);
			add(card);
			return;
		}
		add(avatar(uno, card.getPreferredSize().height));
		add(card);
		add(t);
	}

	/** 상대 말풍선 왼쪽에 붙는 원형 프로필. 위쪽 정렬. */
	private static JLabel avatar(int uno, int h) {
		var pic = new JLabel();
		pic.setVerticalAlignment(SwingConstants.TOP);
		pic.setPreferredSize(new Dimension(PIC, h));
		Helper.getImage("profile/" + uno + ".jpg", PIC, PIC, true, img -> pic.setIcon(new ImageIcon(img)));
		return pic;
	}

	private static JLabel label(String text, int size, Color c) {
		var l = new JLabel(text);
		l.setFont(new Font("맑은 고딕", 0, size));
		l.setForeground(c);
		l.setAlignmentX(LEFT_ALIGNMENT);
		l.setMaximumSize(l.getPreferredSize());
		return l;
	}

	/**
	 * BoxLayout 은 최대 크기를 존중하므로 세로로 늘어나지 않게 막는다.
	 * 폭에 Integer.MAX_VALUE 를 쓰면 BoxLayout 내부 계산이 넘쳐서 위치가 틀어진다.
	 */
	@Override
	public Dimension getMaximumSize() {
		return new Dimension(Short.MAX_VALUE, getPreferredSize().height);
	}
}
