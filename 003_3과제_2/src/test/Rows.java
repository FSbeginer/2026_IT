package test;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.function.BooleanSupplier;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

/** 결과 한 줄 = JLabel 하나 (왼쪽 아이콘 + 오른쪽 HTML 텍스트) */
public class Rows {

	private static final Font FONT = new Font("맑은 고딕", Font.PLAIN, 12);

	/** 유저 : 원형 프로필 + 닉네임 / 이름 */
	public static JLabel user(int u_no, String u_nick, String u_name, BooleanSupplier valid, Runnable onClick) {
		JLabel row = row("<html><b>" + u_nick + "</b><br><font color='#828282'>" + u_name + "</font></html>", onClick);
		ImageLoader.load(row, new File("datafiles/profile/" + u_no + ".jpg"), 40, 40, true, valid);
		return row;
	}

	/** 게시물 : 썸네일 + 닉네임 / 내용 / 번호 */
	public static JLabel post(int p_no, String p_files, String p_content, String u_nick, BooleanSupplier valid,
			Runnable onClick) {
		JLabel row = row("<html><b>" + u_nick + "</b><br>" + p_content
				+ "<br><font color='#969696' size='2'>게시물 #" + p_no + "</font></html>", onClick);
		// p_files 는 "4,483" 형태의 이미지 번호 목록 → 첫 장을 썸네일로
		String first = p_files == null ? "" : p_files.split(",")[0].trim();
		ImageLoader.load(row, new File("datafiles/posts/" + first + ".jpg"), 40, 40, false, valid);
		return row;
	}

	/** 구역 머리글 : 유저 / 게시물 */
	public static JLabel header(String text) {
		JLabel l = new JLabel(text, SwingConstants.CENTER);
		l.setFont(new Font("맑은 고딕", Font.BOLD, 12));
		l.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
		l.setAlignmentX(Component.CENTER_ALIGNMENT);
		l.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
		return l;
	}

	/** 안내문 : 검색어를 입력하세요. */
	public static JLabel hint(String text) {
		JLabel l = new JLabel(text);
		l.setFont(FONT);
		l.setForeground(new java.awt.Color(130, 130, 130));
		l.setBorder(BorderFactory.createEmptyBorder(12, 12, 12, 12));
		l.setAlignmentX(Component.LEFT_ALIGNMENT);
		l.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
		return l;
	}

	private static JLabel row(String html, Runnable onClick) {
		JLabel row = new JLabel(html);
		row.setFont(FONT);
		row.setIconTextGap(12);
		row.setBorder(BorderFactory.createEmptyBorder(6, 12, 6, 12));
		row.setAlignmentX(Component.LEFT_ALIGNMENT);
		row.setMaximumSize(new Dimension(Integer.MAX_VALUE, 52));
		row.setPreferredSize(new Dimension(365, 52));
		row.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				onClick.run();
			}
		});
		return row;
	}
}
