package se.jrat.client.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FramePreviewImage;
import se.jrat.common.utils.ImageUtils;

public class Packet43PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FramePreviewImage frame = FramePreviewImage.instances.get(slave);
		
		int imageSize = slave.readInt();

		byte[] buffer = new byte[imageSize];

		slave.getDataInputStream().readFully(buffer);

		if (frame != null) {

			BufferedImage image = ImageUtils.decodeImage(buffer);

			frame.getPanel().image = image;
			frame.getPanel().repaint();

			frame.setButtonEnabled(true);
		}
	}

}
