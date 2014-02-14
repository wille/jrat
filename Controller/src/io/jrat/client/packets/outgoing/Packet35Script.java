package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet35Script extends AbstractOutgoingPacket {

	private String type;
	private String content;

	public Packet35Script(String type, String content) {
		this.type = type;
		this.content = content;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(type);
		slave.writeLine(content);
	}

	@Override
	public byte getPacketId() {
		return 35;
	}

}
