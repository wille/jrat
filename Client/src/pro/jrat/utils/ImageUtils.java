package pro.jrat.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public class ImageUtils {

	public static BufferedImage decodeImage(byte[] data) throws IOException {
		return ImageIO.read(new ByteArrayInputStream(data));
	}

	public static ImageIcon generate(Color c) {
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		g.setColor(c);
		g.fillRect(1, 1, 14, 14);
		g.dispose();
		return new ImageIcon(image);
	}

}
