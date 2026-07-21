import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class F_리뷰작성 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					F_리뷰작성 frame = new F_리뷰작성(1);
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
	private JLabel label;
	private JTextField textField;
	private JPanel panel;
	private JLabel label_1;
	private JSlider slider;
	private JLabel label_2;
	private JScrollPane scrollPane;
	private JTextArea textArea;
	private JButton button;

	public F_리뷰작성(int pno) {
		setTitle("리뷰작성");
		this.pno = pno;
		setBounds(100, 100, 524, 487);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);

		label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0)));
		label.setBounds(12, 10, 142, 140);
		getContentPane().add(label);

		textField = new JTextField();
		textField.setBounds(182, 10, 292, 29);
		getContentPane().add(textField);
		textField.setColumns(10);

		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("굴림",1,30));
				g2.setColor(Color.black);
				var empty = "☆☆☆☆☆";
				var fill = "★★★★★";
				var fm =g2.getFontMetrics();
				g2.drawString(empty, 0, fm.getHeight()-2);
				int maxWidth =fm.stringWidth(fill);
				g2.clip(new Rectangle2D.Double(0, 0, (score/5)*maxWidth, getHeight()));
				g2.setColor(Color.orange);
				g2.drawString(fill, 0, fm.getHeight()-2);
				g2.dispose();
			}
		};
		panel.setBounds(182, 49, 209, 37);
		getContentPane().add(panel);

		label_1 = new JLabel("별점: 0.0");
		label_1.setBounds(402, 59, 72, 15);
		getContentPane().add(label_1);

		slider = new JSlider();
		slider.addChangeListener(new SliderChangeListener());
		slider.setBackground(Color.WHITE);
		slider.setBounds(182, 101, 292, 22);
		getContentPane().add(slider);

		label_2 = new JLabel("내용");
		label_2.setHorizontalAlignment(SwingConstants.CENTER);
		label_2.setBounds(12, 172, 50, 15);
		getContentPane().add(label_2);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(81, 160, 393, 240);
		getContentPane().add(scrollPane);

		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);

		button = new JButton("등록");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(359, 409, 115, 31);
		getContentPane().add(button);

		new DropTarget(label, new DropTargetAdapter() {

			@Override
			public void drop(DropTargetDropEvent dtde) {
				try {
					var transfer = dtde.getTransferable();
					List<File> files;
					dtde.acceptDrop(DnDConstants.ACTION_COPY);
					files = (List<File>) transfer.getTransferData(DataFlavor.javaFileListFlavor);
					var f = files.get(0);
					if(f.getAbsolutePath().toLowerCase().endsWith(".jfif")) {
						file = f;
						Image img = ImageIO.read(file);
						label.setIcon(new ImageIcon(img.getScaledInstance(label.getWidth(), label.getHeight(), 4)));
					}
				} catch (UnsupportedFlavorException | IOException e) {
					e.printStackTrace();
				}
			}
		});
		slider.setValue(0);
	}
	private File file;
	double score = 0;
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var title = textField.getText();
			var txt = textArea.getText();
			if(title.isBlank()) {
				msgErr("제목을 입력하세요.");
				return;
			}
			if(txt.isBlank()) {
				msgErr("내용을 입력해주세요.");
				return;
			}
			try {
				DB.insert("star", 0,title,txt,score,pno);
				msgInfo("등록이 완료되었습니다.");
				int rno = DB.select("select sno  from star order by desc limit 1", Integer.class);
				try {
					Files.copy(file.toPath(),Paths.get("./review/"+rno+".jfif"), StandardCopyOption.REPLACE_EXISTING);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				int r = JOptionPane.showConfirmDialog(null, "리뷰를 더 작성하시겠습니까?", "질문", 0,3);
				if(r==0) {
					showPage(E_마이페이지.class);
				}
				else {
					showPage(A_메인.class);
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
		}
	}
	private class SliderChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			score = slider.getValue()/20.0;
			label_1.setText(String.format("별점:%.1f", score));
			panel.repaint();
		}
	}
}
