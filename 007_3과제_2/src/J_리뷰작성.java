import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
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
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.filechooser.FileNameExtensionFilter;

import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class J_리뷰작성 extends BF {

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					J_리뷰작성 frame = new J_리뷰작성(1);
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
	public JLabel label;
	public JPanel panel;
	public JPanel panel_1;
	public JButton button;
	public JLabel label_1;
	public JTextField textField;
	public JPanel panel_2;
	public JLabel label_2;
	public JTextArea textArea;
	double score = 5.0;
	private int mw;
	
	public J_리뷰작성(int pno) {
		setTitle("리뷰 작성");
		this.pno = pno;
		setBounds(100, 100, 450, 449);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		label = new JLabel("리뷰 작성");
		label.setFont(new Font("맑은 고딕", Font.BOLD, 30));
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBounds(0, 0, 434, 77);
		getContentPane().add(label);
		
		panel = new JPanel();
		panel.setBackground(new Color(236, 238, 244));
		panel.setBounds(0, 89, 434, 361);
		getContentPane().add(panel);
		panel.setLayout(null);
		
		panel_1 = new JPanel();
		panel_1.setBounds(10, 12, 414, 301);
		panel.add(panel_1);
		panel_1.setLayout(null);
		
		button = new JButton("리뷰 등록");
		button.addActionListener(new ButtonActionListener());
		button.setBackground(new Color(103, 103, 237));
		button.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		button.setForeground(Color.WHITE);
		button.setBounds(10, 246, 394, 43);
		panel_1.add(button);
		
		label_1 = new JLabel("이미지 등록");
		label_1.addMouseListener(new Label_1MouseListener());
		label_1.setOpaque(true);
		label_1.setBorder(new LineBorder(new Color(230, 230, 230)));
		label_1.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		label_1.setBounds(10, 12, 98, 87);
		panel_1.add(label_1);
		
		textField = new PlaceHolder("제목을 입력하세요.");
		textField.setBorder(new LineBorder(new Color(230, 230, 230)));
		textField.setBounds(132, 12, 251, 36);
		panel_1.add(textField);
		textField.setColumns(10);
		
		panel_2 = new JPanel() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g.create();
				g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
				g2.setFont(new Font("맑은 고딕",1,35));
				var fm = g2.getFontMetrics();
				String str = "★★★★★";
				mw = fm.stringWidth(str);
				g2.setColor(Color.gray);
				g2.drawString(str, 0, 24);
				g2.setColor(Color.orange);
				g2.clip(new Rectangle2D.Double(0, 0, (score/5)*mw, 1000));
				g2.drawString(str, 0, 24);
				g2.dispose();
			}
		};
		panel_2.addMouseMotionListener(new Panel_2MouseMotionListener());
		panel_2.setBounds(132, 60, 189, 30);
		panel_1.add(panel_2);
		
		label_2 = new JLabel("5.0");
		label_2.setFont(new Font("맑은 고딕", Font.BOLD, 13));
		label_2.setBounds(324, 60, 52, 30);
		panel_1.add(label_2);
		
		textArea = new PlaceHolder2("내용을 입력하세요.");
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setBorder(new LineBorder(new Color(230, 230, 230)));
		textArea.setBounds(10, 121, 394, 103);
		panel_1.add(textArea);
		
		new DropTarget(label_1, new DropTargetAdapter() {
			@Override
			public void drop(DropTargetDropEvent dtde) {
				dtde.acceptDrop(DnDConstants.ACTION_COPY);
				var tf= dtde.getTransferable();
				try {
					var file = ((List<File>) tf.getTransferData(DataFlavor.javaFileListFlavor)).get(0);
					if(file.getAbsolutePath().endsWith(".jpg")||file.getAbsolutePath().endsWith(".png")||file.getAbsolutePath().endsWith(".gif")) {
						imgFile = file;
						label_1.setIcon(new ImageIcon(ImageIO.read(imgFile).getScaledInstance(label_1.getWidth(), label_1.getHeight(), 4)));
						label_1.setText("");
					}
				} catch (UnsupportedFlavorException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		});
	}
	File imgFile;
	private class Panel_2MouseMotionListener extends MouseMotionAdapter {
		@Override
		public void mouseDragged(MouseEvent e) {
			score = Math.max(0, Math.min(5, ((double)e.getX()/mw)*5));
			label_2.setText(String.format("%.1f", score));
			panel_2.repaint();
		}
	}
	private class Label_1MouseListener extends MouseAdapter {
		@Override
		public void mouseClicked(MouseEvent e) {
			if(e.getClickCount()==2) {
				JFileChooser jfc = new JFileChooser();
				jfc.setMultiSelectionEnabled(false);
				jfc.setAcceptAllFileFilterUsed(false);
				jfc.setFileFilter(new FileNameExtensionFilter("jpg, png, gif Image", "jpg", "png", "gif"));
				if(jfc.showOpenDialog(null)==jfc.APPROVE_OPTION) {
					try {
						imgFile = jfc.getSelectedFile();
						label_1.setIcon(new ImageIcon(ImageIO.read(imgFile).getScaledInstance(label_1.getWidth(), label_1.getHeight(), 4)));
						label_1.setText("");
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				}
			}
		}
	}
	private class ButtonActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			if(imgFile==null) {
				msgErr("이미지를 등록해주세요.");
				return;
			}
			var txt = textField.getText();
			var txt2 = textArea.getText();
			if(txt.isBlank()||txt2.isBlank()) {
				msgErr("제목 또는 내용을 확인해주세요.");
				return;
			}
			try {
				DB.insert("review", 0,User.uno,txt,txt2,score);
				var rno = DB.select("select max(rno) from review", Integer.class);
				var path = imgFile.getAbsolutePath();
				String extendtion = path.substring(path.lastIndexOf('.')+1);
				Files.copy(imgFile.toPath(), Path.of("./datafiles/review/"+rno+"."+extendtion));
				msgInfo("리뷰등록이 완료되었습니다.");
				dispose();
				previous();
			} catch (SQLException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}
}
class PlaceHolder extends JTextField {
	JLabel jl;
	public PlaceHolder(String txt) {
		jl = new JLabel(txt);
		jl.setFont(new Font("맑은 고딕",1,13));
		jl.setEnabled(false);
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(getText().length()==0);
		var txt = getText();
		if(txt.length()>55) {
			txt = txt.substring(0, 55);
			setText(txt);
		}
	}
}
class PlaceHolder2 extends JTextArea{
	JLabel jl;
	public PlaceHolder2(String txt) {
		jl = new JLabel(txt);
		jl.setFont(new Font("맑은 고딕",1,13));
		jl.setEnabled(false);
		jl.setVerticalAlignment(SwingConstants.TOP);
		jl.setBorder(new EmptyBorder(10, 10, 0, 0));
		setLayout(new BorderLayout());
		add(jl);
	}
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		jl.setVisible(getText().length()==0);
		var txt = getText();
		if(txt.length()>250) {
			txt = txt.substring(0, 250);
			setText(txt);
		}
	}
}
