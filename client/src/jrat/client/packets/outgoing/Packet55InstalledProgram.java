package jrat.client.packets.outgoing;

import jrat.client.Connection;


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
