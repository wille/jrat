package org.jrat.project.api;

import java.io.DataOutputStream;

public abstract class PacketBuilder {
	
	private byte header;
	
	public PacketBuilder(byte header) {
		this.header = header;
	}
	
	/**
	 * Abstract write method, called when ready to write
	 * @param dos
	 * @throws Exception
	 */	
	public abstract void write(DataOutputStream dos) throws Exception;
	
	/**
	 * 
	 * @return The header of this packet
	 */
	public byte getHeader() {
		return header;
	}
	
}
