package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.Slave;

public class Packet103CompleteServerUpload extends AbstractOutgoingPacket {
	
	private String remote;
	
	public Packet103CompleteServerUpload(String remote) {
		this.remote = remote;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remote);
	}

	@Override
	public byte getPacketId() {
		return 103;
	}

}
