package org.jrat.project.api;

public class Packet {
	
	private final String header;
	
	public Packet(String header) {
		this.header = header;
	}
	
	/**
	 * 
	 * @return The header
	 */
	
	public String getHeader() {
		return header;
	}
	
	/**
	 * 
	 * @return getHeader()
	 */
	@Override
	public String toString() {
		return this.getHeader();
	}

}
