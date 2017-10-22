package jrat.controller.packets.incoming;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import jrat.controller.ui.frames.FramePreviewImage;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;

public class Packet43PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FramePreviewImage frame = FramePreviewImage.INSTANCES.get(slave);
		
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
