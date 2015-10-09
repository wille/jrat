package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;

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
	public short getPacketId() {
		return 105;
	}

}
