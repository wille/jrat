package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;
import java.io.File;

import se.jrat.client.Slave;
import se.jrat.common.io.FileIO;
import se.jrat.common.io.StringWriter;

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
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(remoteFile);

		dos.writeInt(to);
		dos.write(part, 0, to);		
	}

	@Override
	public byte getPacketId() {
		return 104;
	}

	
}
