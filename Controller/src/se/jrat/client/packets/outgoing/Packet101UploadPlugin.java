package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.common.io.FileIO;

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
