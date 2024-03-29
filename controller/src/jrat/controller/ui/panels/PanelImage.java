package jrat.controller.ui.panels;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

@SuppressWarnings("serial")
public class PanelImage extends JScrollPane {

	public Image image = null;

	public PanelImage() {

	}

	public BufferedImage getBufferedImage() {
		BufferedImage img = new BufferedImage(image.getWidth(null), image.getHeight(null), BufferedImage.TYPE_INT_RGB);
		img.createGraphics().drawImage(image, 0, 0, image.getWidth(null), image.getHeight(null), null);
		return img;
	}

	@Override
	public void paintComponent(Graphics g) {
		if (image != null) {
			g.drawImage(image, 0, 0, this.getWidth(), this.getHeight(), null);
		}
		g.dispose();
		super.paintComponent(g);
	}
}
