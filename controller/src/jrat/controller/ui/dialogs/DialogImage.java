package jrat.controller.ui.dialogs;

import javax.swing.*;
import java.awt.*;

@SuppressWarnings("serial")
public class DialogImage extends JDialog {

	private final Image image;

	public DialogImage(Image img) {
		setModal(true);
		setIconImage(Toolkit.getDefaultToolkit().getImage(DialogImage.class.getResource("/images-stack.png")));
		setTitle("Preview");
		this.image = img;

		setBounds(100, 100, img.getWidth(null) + 20, img.getHeight(null) + 20);

		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				super.paintComponent(g);

				g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
			}
		};
		getContentPane().add(panel, BorderLayout.CENTER);

		setLocationRelativeTo(null);
	}

}
