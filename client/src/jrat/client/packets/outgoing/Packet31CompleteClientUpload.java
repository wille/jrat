package jrat.client.packets.outgoing;

import jrat.client.Connection;

public class Packet31CompleteClientUpload implements OutgoingPacket {
	
	private String file;
	
	public Packet31CompleteClientUpload(String file) {
		this.file = file;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeLine(file);
	}

	@Override
	public short getPacketId() {
		return (byte) 31;
	}

}
