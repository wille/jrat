package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;
import jrat.common.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class Packet43PreviewImage implements OutgoingPacket {

    private String path;
	private BufferedImage image;

	public Packet43PreviewImage(String path, BufferedImage image) {
	    this.path = path;
		this.image = image;
	}

	@Override
	public void write(Connection dos) throws Exception {
	    dos.writeLine(path);

		byte[] buffer = ImageUtils.encode(image);
		
		dos.writeInt(buffer.length);
		dos.write(buffer);
	}

	@Override
	public short getPacketId() {
		return 43;
	}

}
