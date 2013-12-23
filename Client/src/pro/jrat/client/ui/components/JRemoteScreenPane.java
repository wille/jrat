package pro.jrat.client.ui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

@SuppressWarnings("serial")
public class JRemoteScreenPane extends JScrollPane {
	
	private ImagePanel pane = new ImagePanel();
	
	public JRemoteScreenPane() {
		super.setViewportView(pane);
		super.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
		super.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
	}

	class ImagePanel extends JPanel {
		
		private BufferedImage image;
		
		public void update() {
			super.repaint();
		
			if (image != null) {
				super.setPreferredSize(new Dimension(image.getWidth(), image.getHeight()));
				super.revalidate();
			}
		}
		
		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			
			if (image != null) {
				g.drawImage(image, 0, 0, super.getWidth(), super.getHeight(), null);
			}
		}
		
	}

	public void update(BufferedImage image) {
		pane.image = image;
		pane.update();
	}
}
