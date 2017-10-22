package jrat.api.net;

import java.io.IOException;

public interface Reader {

	/**
	 * Reads byte from stream
	 * 
	 * @return byte
	 * @throws IOException
	 */
    byte read() throws IOException;

	/**
	 * Reads short from stream, 2 bytes
	 * 
	 * @return short
	 * @throws IOException
	 */
    short readShort() throws IOException;

	/**
	 * Reads string, (With args)
	 * 
	 * @return String
	 * @throws IOException
	 */
    String readLine() throws IOException;

	/**
	 * Reads bytes into array, until it is full
	 * 
	 * @param buffer
	 * @param i
	 *            off
	 * @param i1
	 *            len
	 * @throws IOException
	 */
    void readFully(byte[] buffer, int i, int i1) throws IOException;

	/**
	 * Reads int from stream, 4 bytes
	 * 
	 * @return int
	 * @throws IOException
	 */
    int readInt() throws IOException;

	/**
	 * Reads long from stream, 8 bytes
	 * 
	 * @return long
	 * @throws IOException
	 */
    long readLong() throws IOException;

	/**
	 * Reads boolean from stream, b != 0 = true
	 * 
	 * @return boolean
	 * @throws IOException
	 */
    boolean readBoolean() throws IOException;

	/**
	 * Reads char from stream, 2 bytes
	 * 
	 * @return char
	 * @throws IOException
	 */
    char readChar() throws IOException;
}
