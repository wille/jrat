package jrat.module.system.packets;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


public class Packet55InstalledProgram implements OutgoingPacket {

	private String program;

	public Packet55InstalledProgram(String program) {
		this.program = program;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(program);
	}

	@Override
	public short getPacketId() {
		return 55;
	}

}
