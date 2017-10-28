package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

public class Packet103CompleteServerUpload implements OutgoingPacket {
	
	private String remote;
	
	public Packet103CompleteServerUpload(String remote) {
		this.remote = remote;
	}
	
	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(remote);
	}

	@Override
	public short getPacketId() {
		return 103;
	}

}
