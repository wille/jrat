package org.jrat.project.api;

import java.io.IOException;

public abstract interface IReader {

	/**
	 * Reads byte from stream
	 * @return byte
	 * @throws IOException
	 */
	public abstract byte read() throws IOException;
	
	/**
	 * Reads short from stream, 2 bytes
	 * @return short
	 * @throws IOException
	 */
	public abstract short readShort() throws IOException;
	
	/**
	 * Reads string, (With args)
	 * @return String
	 * @throws IOException
	 */
	public abstract String readString() throws IOException;
	
	/**
	 * Reads bytes into array, until it is full
	 * @param buffer
	 * @param i off
	 * @param i1 len
	 * @throws IOException
	 */
	public abstract void readFully(byte[] buffer, int i, int i1) throws IOException;
	
	/**
	 * Reads int from stream, 4 bytes
	 * @return int
	 * @throws IOException
	 */
	public abstract int readInt() throws IOException;
	
	
	/**
	 * Reads long from stream, 8 bytes
	 * @return long
	 * @throws IOException
	 */
	public abstract long readLong() throws IOException;
	
	/**
	 * Reads boolean from stream, b != 0 = true
	 * @return boolean
	 * @throws IOException
	 */
	public abstract boolean readBoolean() throws IOException;
	
	/**
	 * Reads char from stream, 2 bytes
	 * @return char
	 * @throws IOException
	 */
	public abstract char readChar() throws IOException;
}
