package jrat.module.shell.packets;

import jrat.controller.Slave;
import jrat.controller.packets.incoming.IncomingPacket;
import jrat.module.shell.ui.FrameRemoteShell;


public class Packet21RemoteShell implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String command = slave.readLine();
		
		FrameRemoteShell frame = (FrameRemoteShell) slave.getPanel(FrameRemoteShell.class);
		
		if (frame != null) {
			frame.getTerminal().append(command + "\n");
		}
	}
}
