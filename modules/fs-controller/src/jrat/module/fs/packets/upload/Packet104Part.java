package jrat.module.fs.packets.upload;

import jrat.controller.Slave;
import jrat.controller.packets.outgoing.OutgoingPacket;

public class Packet104Part implements OutgoingPacket {
	
	private String remoteFile;
	private byte[] part;
	private int to;
	
	public Packet104Part(String remoteFile, byte[] part, int to) {
		this.remoteFile = remoteFile;
		this.part = part;
		this.to = to;
	}
	
	@Override
	public void write(Slave slave) throws Exception {
		slave.writeLine(remoteFile);

		slave.writeInt(to);
		slave.write(part, 0, to);
	}

	@Override
	public short getPacketId() {
		return 104;
	}

	
}
