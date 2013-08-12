package pro.jrat.stub.utils;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ImageUtils {

	public static byte[] encodeImage(BufferedImage image) throws IOException {
		/*ImageWriter writer = ImageIO.getImageWritersByFormatName("jpeg").next();
		ImageWriteParam writeParam = writer.getDefaultWriteParam();

		writeParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
		writeParam.setCompressionQuality(quality);

		ByteArrayOutputStream output = new ByteArrayOutputStream();
		MemoryCacheImageOutputStream mcios = new MemoryCacheImageOutputStream(output);
		writer.setOutput(mcios);
		writer.write(null, new IIOImage(image, null, null), writeParam);
		mcios.close();
		return output.toByteArray();*/
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(image, "jpg", baos);
		baos.flush();
		byte[] bytes = baos.toByteArray();
		baos.close();
		
		return bytes;
	}

}
