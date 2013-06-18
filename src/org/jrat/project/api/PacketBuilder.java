package org.jrat.project.api;

import java.io.DataOutputStream;

public abstract class PacketBuilder {
		
	public abstract void write(DataOutputStream dos) throws Exception;
	
	public final void writeString(DataOutputStream dos, String s) throws Exception {
		dos.writeShort(s.length());
		dos.writeChars(s);
	}
	
}
