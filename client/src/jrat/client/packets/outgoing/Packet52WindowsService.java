package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet52WindowsService extends AbstractOutgoingPacket {

	private String name;

	public Packet52WindowsService(String name) {
		this.name = name;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(name);
	}

	@Override
	public short getPacketId() {
		return 52;
	}

}
