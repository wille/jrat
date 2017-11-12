package jrat.module.fs.packets.upload;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;

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
