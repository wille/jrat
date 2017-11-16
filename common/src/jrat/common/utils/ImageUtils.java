package jrat.common.utils;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.swing.ImageIcon;

public class ImageUtils {

	public static ImageIcon generateColorBoxSquare(Color c) {
		BufferedImage image = new BufferedImage(16, 16, BufferedImage.TYPE_INT_RGB);
		Graphics g = image.createGraphics();
		g.setColor(c);
		g.fillRect(1, 1, 14, 14);
		g.dispose();
		return new ImageIcon(image);
	}

	public static BufferedImage resize(BufferedImage image, int w, int h) {
		if (w == 0) {
			h = 150;
		}

		if (h == 0) {
			h = 100;
		}
		BufferedImage img = new BufferedImage(w, h, BufferedImage.TYPE_INT_RGB);
		Graphics2D gr = img.createGraphics();
		gr.drawImage(image, 0, 0, w, h, null);
		gr.dispose();
		return img;
	}

	public static BufferedImage resize(BufferedImage image, double percent) {
		int scaledWidth = (int) (image.getWidth() * percent);
		int scaledHeight = (int) (image.getHeight() * percent);

		return resize(image, scaledWidth, scaledHeight);
	}

	public static byte[] encode(BufferedImage image) throws Exception {
		return encode(image, 0.25F, "jpeg");
	}

	public static byte[] encode(BufferedImage image, float quality, String format) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream bss = new GZIPOutputStream(baos);
		ImageOutputStream ios = ImageIO.createImageOutputStream(bss);
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName(format);
		ImageWriter writer = iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		if (iwp.canWriteCompressed()) {
            iwp.setCompressionMode(2);
            iwp.setCompressionQuality(quality);
        }
		writer.setOutput(ios);
		writer.write(null, new IIOImage(image, null, null), iwp);
		writer.dispose();
		bss.close();

		return baos.toByteArray();
	}

	public static BufferedImage decodeImage(byte[] data) throws IOException {
		return ImageIO.read(new GZIPInputStream(new ByteArrayInputStream(data)));
	}

}
