import java.awt.EventQueue;
import java.awt.Image;
import java.util.List;

import javax.swing.JFrame;

public class F_댓글 extends BF {


	List<Image> imgs;
	int pno;
	public F_댓글(int pno, List<Image> imgs) {
		this.pno = pno;
		this.imgs = imgs;
		setBounds(100, 100, 450, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

}
