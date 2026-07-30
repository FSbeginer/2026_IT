package test;

import java.awt.Color;
import java.awt.EventQueue;
import java.sql.ResultSet;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.BooleanSupplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.border.MatteBorder;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class 검색 extends JFrame {

	private JPanel contentPane;
	private JTextField textField;

	private String keyword;

	// ── 핵심 : 검색 쿼리 ──────────────────────────────────────────────────────
	/** 닉네임·이름으로 유저 검색 */
	private static final String SQL_USER =
			"select u_no, u_nick, u_name from user "
			+ "where u_nick like ? or u_name like ? order by u_no";
	/** 작성자(닉네임·이름) 기준으로 게시물 검색 */
	private static final String SQL_POST =
			"select p.p_no, p.p_files, p.p_content, u.u_nick from post p "
			+ "join user u on u.u_no = p.u_no "
			+ "where u.u_nick like ? or u.u_name like ? order by p.p_no";

	/** 결과가 쌓이는 패널 */
	private JPanel panel_1;
	/** 검색 쿼리 실행용(직렬) — 늦게 끝난 이전 검색이 최신 결과를 덮지 않도록 단일 스레드 */
	private final ExecutorService queryPool = Executors.newSingleThreadExecutor();
	/** 검색 세대 — 최신 검색의 결과/이미지만 화면에 반영한다 */
	private volatile int generation;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					검색 frame = new 검색();
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
	public 검색() {
		setTitle("검색");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 399, 546);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 383, 70);
		panel.setBorder(new MatteBorder(0, 0, 1, 0, (Color) new Color(128, 128, 128)));
		panel.setBackground(new Color(255, 255, 255));
		contentPane.add(panel);
		panel.setLayout(null);

		JLabel lblNewLabel = new JLabel("<");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(12, 13, 38, 42);
		panel.add(lblNewLabel);

		textField = new JTextField();

		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				super.keyTyped(e);
				// keyTyped 시점엔 텍스트가 아직 반영 전이라, 반영 직후로 미뤄 실제 값으로 검색한다
				SwingUtilities.invokeLater(() -> {
					keyword = textField.getText();
					search();
				});
			}
		});
		textField.setBounds(62, 14, 309, 42);
		panel.add(textField);
		textField.setColumns(10);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(0, 69, 383, 438);
		scrollPane.setBorder(null);
		scrollPane.getVerticalScrollBar().setUnitIncrement(16);
		contentPane.add(scrollPane);

		panel_1 = new JPanel();
		panel_1.setBackground(new Color(255, 255, 255));
		scrollPane.setViewportView(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.Y_AXIS));

		search();
	}

	/** 실시간 검색 : 쿼리는 백그라운드, 화면 구성은 EDT, 이미지는 행 단위 비동기 */
	private void search() {
		final int my = ++generation;
		final BooleanSupplier live = () -> my == generation;	// 최신 검색인가
		final String kw = keyword == null ? "" : keyword.trim();

		panel_1.removeAll();
		if (kw.isEmpty()) {
			panel_1.add(Rows.hint("검색어를 입력하세요."));
			refresh();
			return;
		}
		refresh();

		queryPool.execute(() -> {
			try {
				String like = "%" + kw + "%";

				List<Object[]> users;
				try (ResultSet rs = DB.res(SQL_USER, like, like)) {
					users = rows(rs, r -> new Object[] { r.getInt("u_no"), r.getString("u_nick"), r.getString("u_name") });
				}

				List<Object[]> posts;
				try (ResultSet rs = DB.res(SQL_POST, like, like)) {
					posts = rows(rs, r -> new Object[] { r.getInt("p_no"), r.getString("p_files"),
							r.getString("p_content"), r.getString("u_nick") });
				}

				if (!live.getAsBoolean()) return;
				SwingUtilities.invokeLater(() -> show(live, users, posts));
			} catch (Exception e) {
				e.printStackTrace();
			}
		});
	}

	/** 조회 결과를 화면에 붙인다 (EDT) */
	private void show(BooleanSupplier live, List<Object[]> users, List<Object[]> posts) {
		if (!live.getAsBoolean()) return;
		panel_1.removeAll();

		if (!users.isEmpty()) {
			panel_1.add(Rows.header("유저"));
			users.forEach(u -> {
				int u_no = (Integer) u[0];
				panel_1.add(Rows.user(u_no, (String) u[1], (String) u[2], live, () -> {
					// TODO 유저 정보 폼[그림10-1]으로 이동 : new 유저정보(u_no).setVisible(true); dispose();
				}));
			});
		}
		if (!posts.isEmpty()) {
			panel_1.add(Rows.header("게시물"));
			posts.forEach(p -> {
				int p_no = (Integer) p[0];
				panel_1.add(Rows.post(p_no, (String) p[1], (String) p[2], (String) p[3], live, () -> {
					// TODO 댓글 폼[그림6-1]으로 이동 : new 댓글(p_no).setVisible(true); dispose();
				}));
			});
		}
		refresh();
	}

	/** ResultSet을 Stream으로 흘려 매핑 (커서 소진까지 lazy) */
	private static <T> List<T> rows(ResultSet rs, RowMapper<T> mapper) {
		return Stream.generate(() -> {
			try {
				return rs.next() ? mapper.map(rs) : null;
			} catch (Exception e) {
				return null;
			}
		}).takeWhile(Objects::nonNull).collect(Collectors.toList());
	}

	private interface RowMapper<T> {
		T map(ResultSet rs) throws Exception;
	}

	private void refresh() {
		panel_1.revalidate();
		panel_1.repaint();
	}
}
