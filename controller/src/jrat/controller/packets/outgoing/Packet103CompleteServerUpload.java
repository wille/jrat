package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

import java.io.DataOutputStream;

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
	public short getPacketId() {
		return 103;
	}

}
