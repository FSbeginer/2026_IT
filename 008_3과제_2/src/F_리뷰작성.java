import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.dnd.DnDConstants;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetAdapter;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.SQLException;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JScrollPane;


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
	 * @param pno 
	 */
	int pno;
	public JLabel label;
	public JTextField textField;
	public JPanel panel;
	public JLabel label_1;
	public JSlider slider;
	public JLabel label_2;
	public JTextArea textArea;
	public JButton button;
	double score;
	public F_리뷰작성(int pno) {
		setTitle("리뷰작성");
		this.pno = pno;
		setBounds(100, 100, 514, 478);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("");
		label.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		label.setBounds(22, 25, 159, 141);
		getContentPane().add(label);
		
		textField = new JTextField();
		textField.setBounds(209, 27, 277, 33);
		getContentPane().add(textField);
		textField.setColumns(10);
		
		panel = new JPanel() {
			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("Dialog", 0, 35));
				var fm = g2.getFontMetrics();
				String txt = "☆☆☆☆☆";
				String txt2 = "★★★★★";
				int mw = fm.stringWidth(txt2);
				g2.drawString(txt, 0, 30);
				g2.clip(new Rectangle2D.Double(0,0,(score/5)*mw, 1000));
				g2.setColor(Color.ORANGE);
				g2.drawString(txt2, 0, 30);
				g2.dispose();
			}
		};
		panel.setBounds(209, 70, 170, 33);
		getContentPane().add(panel);
		
		label_1 = new JLabel("별점: 0");
		label_1.setBounds(389, 73, 66, 27);
		getContentPane().add(label_1);
		
		slider = new JSlider();
		slider.addChangeListener(new SliderChangeListener());
		slider.setBackground(Color.WHITE);
		slider.setBounds(209, 113, 277, 33);
		getContentPane().add(slider);
		
		label_2 = new JLabel("내용");
		label_2.setBounds(32, 176, 57, 15);
		getContentPane().add(label_2);
		
		button = new JButton("등록");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(0, 128, 0));
		button.setForeground(Color.WHITE);
		button.setBounds(350, 404, 125, 25);
		getContentPane().add(button);

		load();
		
		new DropTarget(label, new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY);
				try {
					var file = ((List<File>)dtde.getTransferable().getTransferData(DataFlavor.javaFileListFlavor)).get(0);
					if(file.getAbsolutePath().endsWith("jfif")) {
						label.setIcon(new ImageIcon(ImageIO.read(file).getScaledInstance(label.getWidth(), label.getHeight(), 4)));
						f = file;
					}
				} catch (UnsupportedFlavorException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(118, 176, 368, 218);
		getContentPane().add(scrollPane);
		
		textArea = new JTextArea();
		scrollPane.setViewportView(textArea);
		textArea.setBorder(new LineBorder(Color.LIGHT_GRAY));
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
	}
	File f;
	public JScrollPane scrollPane;
	private void load() {
		
	}
	private class SliderChangeListener implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			int v = slider.getValue();
			score = (double)v / slider.getMaximum() * 5;
			label_1.setText(String.format("별점: %.1f", score));
			panel.repaint();
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			var txt = textField.getText();
			var txt2 = textArea.getText();
			if(txt.isBlank()) {
				msgErr("제목을 입력하세요.");
				return;
			}
			if(txt2.isBlank()) {
				msgErr("내용을 입력해주세요.");
				return;
			}
			try {
				DB.insert("star", 0,txt,txt2,score,pno);
				int rno = DB.select("select max(sno) from star", Integer.class);
				if(f!=null) {
					Files.copy(f.toPath(), Path.of("./datafiles/reiew/"+rno+".jfif"));
				}
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			msgInfo("등록이 완료되었습니다.");
			int r = JOptionPane.showConfirmDialog(null, "리뷰를 더 작성하시겠습니까?", "질문", 0, 3);
			if(r==0) {
				dispose();
				previous();
			}
			else {
				dispose();
				while (!prev.isEmpty()) {
					var p = prev.pop();
					if(p instanceof A_메인) {
						p.updateForm();
						p.setVisible(true);
						break;
					}
					else {
						p.dispose();
					}
				}
			}
		}
	}
}
