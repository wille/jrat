package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.Slave;

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
	public byte getPacketId() {
		return 101;
	}

}
