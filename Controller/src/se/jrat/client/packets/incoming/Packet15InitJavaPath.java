package se.jrat.client.packets.incoming;

import java.io.DataInputStream;

import se.jrat.client.Slave;


public class Packet15InitJavaPath extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {

		String path = slave.readLine();
		slave.setJavaPath(path);

	}
}
