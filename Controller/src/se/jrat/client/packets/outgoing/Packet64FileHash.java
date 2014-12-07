package se.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.client.Slave;


public class Packet64FileHash extends AbstractOutgoingPacket {

	private String file;

	public Packet64FileHash(String file) {
		this.file = file;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
	}

	@Override
	public byte getPacketId() {
		return 64;
	}

}