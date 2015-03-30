package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;

public class Packet102BeginServerUpload extends AbstractOutgoingPacket {
	
	private File local;
	private String remote;
	
	public Packet102BeginServerUpload(File local, String remote) {
		this.local = local;
		this.remote = remote;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remote);
		
		dos.writeLong(local.length());
	}

	@Override
	public byte getPacketId() {
		return 102;
	}

}
