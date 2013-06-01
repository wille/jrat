package com.redpois0n.packets;

import java.awt.Graphics;
import java.awt.image.BufferedImage;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteThumbView;
import com.redpois0n.util.ImageUtils;

public class PacketIMGLIST extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		FrameRemoteThumbView frame = FrameRemoteThumbView.instances.get(slave);

		String path = slave.readLine();
		int imageSize = slave.getDataInputStream().readInt();

		int w = 150;
		int h = 100;

		BufferedImage image = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);

		Graphics imageGraphics = image.getGraphics();

		byte[] buffer = new byte[imageSize];
		slave.getDataInputStream().readFully(buffer);

		if (frame != null) {
			BufferedImage img = ImageUtils.decodeImage(buffer);
			imageGraphics.drawImage(img, 0, 0, w, h, null);
			frame.addImage(path, image);
			frame.setProgress(frame.getProgress() + 1);
		}

		/*
		 * if (frame != null) { frame.getBar().setVisible(false);
		 * frame.getCount().setVisible(false); }
		 */
	}

}
