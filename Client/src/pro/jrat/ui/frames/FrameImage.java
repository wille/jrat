package pro.jrat.ui.frames;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JPanel;

@SuppressWarnings("serial")
public class FrameImage extends BaseDialog {

	private final Image image;

	public FrameImage(Image img) {
		this.image = img;

		setBounds(100, 100, img.getWidth(null) + 20, img.getHeight(null) + 20);

		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		getContentPane().add(panel, BorderLayout.CENTER);
	}

}
