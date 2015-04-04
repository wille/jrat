package se.jrat.controller.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import se.jrat.common.utils.ImageUtils;
import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FramePreviewImage;

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
