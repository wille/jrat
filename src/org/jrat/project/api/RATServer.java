package org.jrat.project.api;

import java.io.DataInputStream;
import java.io.DataOutputStream;

public final class RATServer {

	private final String ip;
	private final DataInputStream dis;
	private final DataOutputStream dos;
	private final Connection con;
	private final IWriter writer;
	private final IReader reader;
	private final PacketWriter pw;
	
	public RATServer(String ip, Connection con, DataInputStream dis, DataOutputStream dos, IWriter writer, IReader reader, PacketWriter pw) {
		this.ip = ip;
		this.dis = dis;
		this.dos = dos;
		this.con = con;
		this.writer = writer;
		this.reader = reader;
		this.pw = pw;
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
	 * @return The DataInputStream used to read data from the server
	 */	
	
	public DataInputStream getDataInputStream() {
		return dis;
	}
	
	/**
	 * 
	 * @return The DataOutputStream used to write data to the server
	 */

	public DataOutputStream getDataOutputStream() {
		return dos;
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
	 * 
	 * @return The packet writer
	 */
	
	public PacketWriter getPacketWriter() {
		return pw;
	}
	
	/**
	 * Only equals if IP is
	 */
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof RATServer) {
			return ((RATServer)obj).ip.equals(this.ip);
		}
		
		return false;
	}

}
