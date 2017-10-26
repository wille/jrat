package jrat.controller.packets.outgoing;

import jrat.controller.Slave;

public class Packet104ServerUploadPart extends AbstractOutgoingPacket {
	
	private String remoteFile;
	private byte[] part;
	private int to;
	
	public Packet104ServerUploadPart(String remoteFile, byte[] part, int to) {
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
