package org.jrat.project.api;

public class Packet {
	
	private final byte header;
	
	public Packet(byte header) {
		this.header = header;
	}
	
	/**
	 * 
	 * @return The header
	 */
	
	public byte getHeader() {
		return header;
	}
	
	/**
	 * 
	 * @return Packet-getHeader()
	 */
	@Override
	public String toString() {
		return "Packet-" + this.getHeader();
	}

}
