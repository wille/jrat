package jrat.api.net;

import java.io.IOException;

public interface Writer {

	/**
	 * Writes byte to stream
	 * 
	 * @param b
	 * @throws IOException
	 */
    void write(byte b) throws IOException;

	/**
	 * Writes byte array to stream
	 * 
	 * @param b
	 * @throws IOException
	 */
    void write(byte[] b) throws IOException;

	/**
	 * Writes string to stream (-c arg no encryption)
	 * 
	 * @param str
	 * @throws IOException
	 */
    void writeLine(String str) throws IOException;

	/**
	 * Writes short to stream, 2 bytes
	 * 
	 * @param s
	 * @throws IOException
	 */
    void writeShort(short s) throws IOException;

	/**
	 * Writes int to stream, 4 bytes
	 * 
	 * @param i
	 * @throws IOException
	 */
    void writeInt(int i) throws IOException;

	/**
	 * Writes long to stream, 8 bytes
	 * 
	 * @param l
	 * @throws IOException
	 */
    void writeLong(long l) throws IOException;

	/**
	 * Writes boolean, true = 1, false = 0
	 * 
	 * @param b
	 * @throws IOException
	 */
    void writeBoolean(boolean b) throws IOException;

	/**
	 * Writes char to stream, 2 bytes
	 * 
	 * @param c
	 * @throws IOException
	 */
    void writeChar(char c) throws IOException;
}
