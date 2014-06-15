package su.jrat.client.packets.outgoing;

import java.io.DataOutputStream;

import su.jrat.client.Slave;


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