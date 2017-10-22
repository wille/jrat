package jrat.client.packets.outgoing;

import jrat.common.io.StringWriter;
import java.io.DataOutputStream;


public class Packet9InitJavaVersion extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String version = System.getProperty("java.runtime.version");
		
		sw.writeLine(version);
	}

	@Override
	public short getPacketId() {
		return (byte) 9;
	}

}