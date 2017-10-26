package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet38HostFile extends AbstractOutgoingPacket {

	private String content;

	public Packet38HostFile(String content) {
		this.content = content;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(content);
	}

	@Override
	public short getPacketId() {
		return 38;
	}

}
