package pro.jrat.api;

import java.io.IOException;

public abstract interface Writer {

	/**
	 * Writes byte to stream
	 * 
	 * @param b
	 * @throws IOException
	 */
	public abstract void write(byte b) throws IOException;

	/**
	 * Writes byte array to stream
	 * 
	 * @param b
	 * @throws IOException
	 */
	public abstract void write(byte[] b) throws IOException;

	/**
	 * Writes string to stream (-c arg no encryption)
	 * 
	 * @param str
	 * @throws IOException
	 */
	public abstract void writeLine(String str) throws IOException;

	/**
	 * Writes short to stream, 2 bytes
	 * 
	 * @param s
	 * @throws IOException
	 */
	public abstract void writeShort(short s) throws IOException;

	/**
	 * Writes int to stream, 4 bytes
	 * 
	 * @param i
	 * @throws IOException
	 */
	public abstract void writeInt(int i) throws IOException;

	/**
	 * Writes long to stream, 8 bytes
	 * 
	 * @param l
	 * @throws IOException
	 */
	public abstract void writeLong(long l) throws IOException;

	/**
	 * Writes boolean, true = 1, false = 0
	 * 
	 * @param b
	 * @throws IOException
	 */
	public abstract void writeBoolean(boolean b) throws IOException;

	/**
	 * Writes char to stream, 2 bytes
	 * 
	 * @param c
	 * @throws IOException
	 */
	public abstract void writeChar(char c) throws IOException;
}
