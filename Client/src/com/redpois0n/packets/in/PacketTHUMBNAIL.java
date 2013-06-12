package com.redpois0n.packets.in;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;

import javax.swing.ImageIcon;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.Frame;
import com.redpois0n.ui.frames.FrameInfo;
import com.redpois0n.utils.Util;


public class PacketTHUMBNAIL extends AbstractIncomingPacket {

	public BufferedImage bufferedImage;
	public byte[] buffer;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		bufferedImage = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
		buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

		slave.getDataInputStream().readFully(buffer);

		slave.setThumbnail(new ImageIcon(bufferedImage));
		FrameInfo frame1 = FrameInfo.instances.get(slave);
		if (frame1 != null) {
			frame1.panel.image = slave.getThumbnail().getImage();
			frame1.panel.repaint();
		}
		
		int row = Util.getRow(slave);
		
		if (row != -1 && Frame.thumbnails) {
			Frame.mainModel.setValueAt(slave.getThumbnail(), row, 0);
		}
	}

}
