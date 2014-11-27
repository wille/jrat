package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet44PlaySoundFromURL extends AbstractOutgoingPacket {

	private String url;
	private int times;

	public Packet44PlaySoundFromURL(String url, int times) {
		this.url = url;
		this.times = times;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(url);
		dos.writeInt(times);
	}

	@Override
	public byte getPacketId() {
		return 44;
	}

}
