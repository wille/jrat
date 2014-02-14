package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


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
