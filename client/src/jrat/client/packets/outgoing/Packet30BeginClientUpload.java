package jrat.client.packets.outgoing;

import jrat.client.Connection;

import java.io.File;

public class Packet30BeginClientUpload implements OutgoingPacket {
	
	private File file;
	
	public Packet30BeginClientUpload(File file) {
		this.file = file;
	}

	@Override
	public void write(Connection dos) throws Exception {
		dos.writeLine(file.getAbsolutePath());
		dos.writeLong(file.length());
	}

	@Override
	public short getPacketId() {
		return (byte) 30;
	}

}
