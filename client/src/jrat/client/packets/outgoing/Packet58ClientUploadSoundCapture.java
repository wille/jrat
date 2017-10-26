package jrat.client.packets.outgoing;

import jrat.client.Connection;


public class Packet58ClientUploadSoundCapture extends AbstractOutgoingPacket {

	private byte[] data;
	private int read;
	private int quality;
	
	public Packet58ClientUploadSoundCapture(byte[] data, int read, int quality) {
		this.data = data;
		this.read = read;
		this.quality = quality;
	}

	@Override
	public void write(Connection con) throws Exception {
		con.writeInt(quality);
		con.writeInt(read);
		con.write(data);
	}

	@Override
	public short getPacketId() {
		return 58;
	}

}
