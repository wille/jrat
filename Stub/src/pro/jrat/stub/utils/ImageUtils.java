package pro.jrat.stub.utils;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;

public class ImageUtils {

	public static byte[] encodeImage(BufferedImage image) throws IOException {
		ByteArrayOutputStream bss = new ByteArrayOutputStream();
		ImageOutputStream baos = ImageIO.createImageOutputStream(bss);
		Iterator<ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpeg");
		ImageWriter writer = iter.next();
		ImageWriteParam iwp = writer.getDefaultWriteParam();
		iwp.setCompressionMode(2);
		iwp.setCompressionQuality(0.1f);
		writer.setOutput(baos);
		writer.write(null, new IIOImage(image, null, null), iwp);
		writer.dispose();

		return bss.toByteArray();
	}

}
