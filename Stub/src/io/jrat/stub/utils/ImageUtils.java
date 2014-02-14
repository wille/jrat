package io.jrat.stub.utils;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Iterator;
import java.util.zip.GZIPOutputStream;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtils {

	public static BufferedImage resize(BufferedImage image, int w, int h) {
		if (w == 0) {
			h = 150;
		}
		
		if (h == 0) {
			h = 100;
		}
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

	public static byte[] encode(BufferedImage image) throws Exception {
		return encode(image, 0.25F);
	}
	
	public static byte[] encode(BufferedImage image, float quality) throws Exception {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		GZIPOutputStream bss = new GZIPOutputStream(baos);
		ImageOutputStream ios = ImageIO.createImageOutputStream(bss);
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(2);
		iwp.setCompressionQuality(quality);
		writer.setOutput(ios);
		writer.write(null, new IIOImage(image, null, null), iwp);
		writer.dispose();
		bss.close();
	
		return baos.toByteArray();
	}

}
