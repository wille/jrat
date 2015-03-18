package se.jrat.client.packets.incoming;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FramePreviewImage;
import se.jrat.common.utils.ImageUtils;

public class Packet43PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FramePreviewImage frame = FramePreviewImage.instances.get(slave);
		int w = slave.readInt();
		int h = slave.readInt();
		
		int imageSize = slave.readInt();

		byte[] buffer = new byte[imageSize];

		slave.getDataInputStream().readFully(buffer);

		if (frame != null) {

			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			Graphics imageGraphics = image.getGraphics();

			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);

			frame.getPanel().image = image;
			frame.getPanel().repaint();

			frame.setButtonEnabled(true);
		}
	}

}
