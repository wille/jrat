package se.jrat.client.ui.components;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
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

	public class ImagePanel extends JPanel {

		private BufferedImage image;
		
		public ImagePanel() {
			super();
			super.setFocusable(true);
			super.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseClicked(MouseEvent arg0) {
					requestFocusInWindow();
				}
			});
		}

		@Override
		public void addNotify() {
			super.addNotify();
			super.requestFocus();
		}

		public void update(BufferedImage image) {
			this.image = image;
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

		public BufferedImage getImage() {
			return image;
		}

	}

	public void update(BufferedImage image) {
		pane.update(image);
	}
	
	public BufferedImage getImage() {
		return pane.image;
	}

	public ImagePanel getPanel() {
		return pane;
	}
}
