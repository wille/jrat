package com.redpois0n;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.MemoryCacheImageOutputStream;

public class ImageUtils {

	public static byte[] encodeImage(BufferedImage image, float quality) throws IOException {
		ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
		ImageWriteParam writeParam = writer.getDefaultWriteParam();

		writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		writeParam.setCompressionQuality(quality);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		writer.setOutput(new MemoryCacheImageOutputStream(output));
		writer.write(null, new IIOImage(image, null, null), writeParam);

		return output.toByteArray();
	}

}
