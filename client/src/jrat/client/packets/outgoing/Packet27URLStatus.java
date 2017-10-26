package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet27URLStatus extends AbstractOutgoingPacket {

	private String url;
	private String status;

	public Packet27URLStatus(String url, String status) {
		this.url = url;
		this.status = status;
	}

	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(url);
        con.writeLine(status);
	}

	@Override
	public short getPacketId() {
		return 27;
	}

}
