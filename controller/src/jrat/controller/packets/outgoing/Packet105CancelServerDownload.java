package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

public class Packet105CancelServerDownload implements OutgoingPacket {
	
	private String remote;
	
	public Packet105CancelServerDownload(String remote) {
		this.remote = remote;
	}
	
	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(remote);
	}

	@Override
	public short getPacketId() {
		return 105;
	}

}
