package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class Packet26InitJavaPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}
}
