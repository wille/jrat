package jrat.module.fs.packets.upload;

import jrat.client.Connection;
import jrat.client.packets.outgoing.OutgoingPacket;


public class Packet29UploadPart implements OutgoingPacket {

	private String file;
	private byte[] part;
	private int to;
	
	public Packet29UploadPart(String file, byte[] part, int to) {
		this.file = file;
		this.part = part;
		this.to = to;
	}
	
	@Override
	public void write(Connection con) throws Exception {
        con.writeLine(file);

        con.writeInt(to);
        con.write(part, 0, to);
	}

	@Override
	public short getPacketId() {
		return (byte) 29;
	}

}
